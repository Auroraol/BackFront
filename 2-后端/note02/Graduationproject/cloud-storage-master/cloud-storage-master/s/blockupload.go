package s

import (
	"errors"
	"fmt"
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/utils"
	"github.com/gomodule/redigo/redis"
	"math"
	"os"
	"os/exec"
	"strconv"
	"strings"
)

var BlockUploadService *blockUploadService

//分块上传服务
type blockUploadService struct {
}

func init() {
	BlockUploadService = new(blockUploadService)
}

//初始化分块信息
func (this *blockUploadService) InitMultipart(item *entitys.MultipartUploadInfo) (err error) {
	if entitys.MyAppConfig.ChunkSize <= 0 {
		return errors.New("分块大小计算错误")
	}

	//分块初始化信息
	item.UploadId = utils.UUid(16)
	item.ChunkSize = entitys.MyAppConfig.ChunkSize * 1024 * 1024
	item.ChunkCount = int(math.Ceil(float64(item.FileSize) / float64(item.ChunkSize)))

	//将初始化信息写入redis
	if err = RedisService.DoWithCheck("HMSET", fmt.Sprintf(entitys.MultipartUploadCachePrefix, item.UploadId),
		"ChunkCount", item.ChunkCount,
		"ChunkSize", item.ChunkSize,
		"FileHash", item.FileHash,
		"FileName", item.FileName,
		"FileSize", item.FileSize); err != nil {
		return
	}
	return
}

//通知分块上传完成
func (this *blockUploadService) CompleteUpload(uploadId string, user *entitys.User) (err error) {
	var (
		fileMeta entitys.FileMeta
		file     entitys.MultipartUploadInfo
		ok       bool
	)
	if file, err = this.GetUploadInfoFromCache(uploadId); err != nil {
		return
	}

	if file.CompleteCount != file.ChunkCount {
		return errors.New("上传未完成")
	}

	//存储文件数据
	fileMeta = entitys.FileMeta{
		Sha1:   file.FileHash,
		Name:   file.FileName,
		Size:   file.FileSize,
		Status: entitys.FileMetaStatusEnable,
	}

	//计算文件路径
	if err = FileMgrService.SetAddr(&fileMeta); err != nil {
		return
	}

	//合并分块
	if ok, err = mergeAllPartFile(entitys.MultipartUploadTmpPath+"/"+uploadId, fileMeta.Addr); err != nil {
		return err
	}

	if !ok {
		return errors.New("文件合并失败")
	}

	if err = FileMgrService.Create(fileMeta); err != nil {
		return
	}

	//存储文件与用户关系
	if user != nil {
		userFile := entitys.UserFile{
			UserId:   user.Id,
			FileSha1: fileMeta.Sha1,
			FileSize: fileMeta.Size,
			FileName: fileMeta.Name,
			Status:   entitys.FileMetaStatusEnable,
		}
		if err = UserService.AddUserFile(userFile, NewTransactor()); err != nil {
			return
		}
	}

	//删除缓存
	if err = RedisService.DoWithCheck("DEL", fmt.Sprintf(entitys.MultipartUploadCachePrefix, uploadId)); err != nil {
		return
	}

	return

}

//取消上传分块
func (this *blockUploadService) CancelUpload(uploadId string) (err error) {
	//删除缓存
	if err = RedisService.DoWithCheck("DEL", fmt.Sprintf(entitys.MultipartUploadCachePrefix, uploadId)); err != nil {
		return
	}

	//删除目录
	if err = os.RemoveAll(entitys.MultipartUploadTmpPath + "/" + uploadId); err != nil {
		return
	}

	return
}

//查看分块上传的整体状态
func (this *blockUploadService) MultipartUploadStatus(uploadId string) (file entitys.MultipartUploadInfo, err error) {
	return this.GetUploadInfoFromCache(uploadId)
}

//合并文件
func mergeAllPartFile(partFileStorePath, fileStorePath string) (ok bool, err error) {
	var cmd *exec.Cmd
	cmd = exec.Command(entitys.MyAppConfig.MergeAllShell, partFileStorePath, fileStorePath)

	if _, err = cmd.Output(); err != nil {
		return
	}

	//合并成功，删除目录
	if err = os.RemoveAll(partFileStorePath); err != nil {
		return
	}

	return true, nil
}

//从缓存中获取上传信息
func (this *blockUploadService) GetUploadInfoFromCache(uploadId string) (file entitys.MultipartUploadInfo, err error) {
	var data []interface{}
	if data, err = redis.Values(RedisService.Do("HGETALL", fmt.Sprintf(entitys.MultipartUploadCachePrefix, uploadId))); err != nil {
		return
	}
	if len(data) == 0 {
		return file, errors.New("上传信息不存在")
	}

	file.UploadId = uploadId
	for i := 0; i < len(data); i += 2 {
		k := string(data[i].([]byte))
		v := string(data[i+1].([]byte))

		switch {
		case k == "ChunkCount":
			file.ChunkCount, _ = strconv.Atoi(v)
		case strings.HasPrefix(k, entitys.MultipartUploadChunkIndex):
			file.CompleteCount++
		case k == "FileName":
			file.FileName = v
		case k == "FileHash":
			file.FileHash = v
		case k == "FileSize":
			fileSize, _ := strconv.Atoi(v)
			file.FileSize = int64(fileSize)
		case k == "ChunkSize":
			file.ChunkSize, _ = strconv.Atoi(v)
		}

	}

	return
}
