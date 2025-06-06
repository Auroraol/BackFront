package logic

import (
	"butane-netdisk/common/errorx"
	"butane-netdisk/common/utils"
	"butane-netdisk/service/repository/model"
	"butane-netdisk/service/user/rpc/types/user"
	"context"
	"crypto/md5"
	"encoding/json"
	"fmt"
	"mime/multipart"
	"path"

	"butane-netdisk/service/repository/api/internal/svc"
	"butane-netdisk/service/repository/api/internal/types"

	"github.com/zeromicro/go-zero/core/logx"
)

type FileUploadLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewFileUploadLogic(ctx context.Context, svcCtx *svc.ServiceContext) *FileUploadLogic {
	return &FileUploadLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

// 根据md5码查看文件是否存在，若存在则秒传成功。若不存在则插入数据库数据并拼接name和ext，上传文件到cos
func (l *FileUploadLogic) FileUpload(req *types.FileUploadRequest, file multipart.File, fileHeader *multipart.FileHeader) (resp *types.FileUploadResponse, err error) {
	// 判断是否已达用户容量上限
	userId, err := json.Number(fmt.Sprintf("%v", l.ctx.Value("userId"))).Int64()
	if err != nil {
		return nil, err
	}
	volumeInfo, err := l.svcCtx.UserRpc.FindVolumeById(l.ctx, &user.FindVolumeReq{Id: userId})
	if err != nil {
		return nil, err
	}
	if volumeInfo.NowVolume+fileHeader.Size > volumeInfo.TotalVolume {
		return nil, errorx.NewDefaultError("文件过大！")
	}
	// 增加用户当前已存储容量
	_, err = l.svcCtx.UserRpc.AddVolume(l.ctx, &user.AddVolumeReq{
		Id:   userId,
		Size: fileHeader.Size,
	})
	if err != nil {
		return nil, err
	}
	// 判断文件是否已存在，若已存在则为秒传成功
	b := make([]byte, fileHeader.Size)
	_, err = file.Read(b)
	if err != nil {
		return nil, err
	}
	md5Str := fmt.Sprintf("%x", md5.Sum(b))
	count, err := l.svcCtx.RepositoryPoolModel.CountByHash(l.ctx, md5Str)
	if count > 0 {
		repositoryInfo, err := l.svcCtx.RepositoryPoolModel.FindRepositoryPoolByHash(l.ctx, md5Str)
		if err != nil {
			return nil, err
		}
		return &types.FileUploadResponse{Id: repositoryInfo.Id}, err
	}
	newId := utils.GenerateNewId(l.svcCtx.RedisClient, "repository")
	// 上传文件到cos，并得到filepath
	filePath, baseName, err := utils.CosUpload(fileHeader, newId, b)
	if err != nil {
		return nil, err
	}
	// TODO 可以增加个异步转移任务队列

	// 插入数据
	_, err = l.svcCtx.RepositoryPoolModel.InsertWithId(l.ctx, &model.RepositoryPool{
		Id:   newId,
		Hash: md5Str,
		Ext:  path.Ext(baseName),
		Size: fileHeader.Size,
		Path: filePath,
		Name: fileHeader.Filename,
	})
	if err != nil {
		return nil, errorx.NewDefaultError("上传失败！")
	}
	return &types.FileUploadResponse{Id: newId}, err
}
