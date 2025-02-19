package s

import (
	"gitee.com/phpdi/cloud-storage/entitys"
	"gitee.com/phpdi/cloud-storage/utils"
	"io"
	"os"
	"path"
)

//文件管理服务
type fileMgrService struct {
}

var (
	FileMgrService *fileMgrService
)

func init() {
	FileMgrService = new(fileMgrService)
}

//新增数据
func (this *fileMgrService) Create(fileMeta entitys.FileMeta) error {
	return Db.Create(&fileMeta).Error
}

//更新数据
func (this *fileMgrService) Update(fileMeta entitys.FileMeta) error {
	return Db.Model(&fileMeta).Where("Sha1=?", fileMeta.Sha1).Updates(fileMeta).Error
}

//获取所有文件数
func (this *fileMgrService) FileMetas() []entitys.FileMeta {
	return nil
}

//获取某个文件数据
func (this *fileMgrService) FileMeta(sha1 string) (item entitys.FileMeta, err error) {

	err = Db.Where("Sha1 = ?", sha1).First(&item).Error

	return
}

//验证文件是否存在
func (this *fileMgrService) FileExist(sha1 string) bool {

	var item entitys.FileMeta
	return !Db.Where("Sha1 = ?", sha1).First(&item).RecordNotFound()
}

//文件存储
func (this *fileMgrService) SaveFile(fileMeta entitys.FileMeta, fd io.Reader, user *entitys.User) (err error) {

	var newFile *os.File

	//文件已经存在
	if this.FileExist(fileMeta.Sha1) {
		return
	}

	//存储文件
	//文件路径
	if err = this.SetAddr(&fileMeta); err != nil {
		return
	}

	if newFile, err = os.Create(fileMeta.Addr); err != nil {
		return
	}
	defer newFile.Close()

	//完成文件拷贝
	if _, err = io.Copy(newFile, fd); err != nil {
		return
	}

	//保存文件数据
	fileMeta.Status = entitys.FileMetaStatusEnable
	if err = this.Create(fileMeta); err != nil {
		return
	}

	//存储文件与用户关系
	if user != nil {
		userFile := entitys.UserFile{
			UserId:   user.Id,
			FileSha1: fileMeta.Sha1,
			FileSize: fileMeta.Size,
			FileName: fileMeta.Name,
			Status:   entitys.UserFileStatusEnable,
		}

		if err = UserService.AddUserFile(userFile, NewTransactor()); err != nil {
			return
		}
	}

	return
}

//计算文件目录
func (this *fileMgrService) SetAddr(file *entitys.FileMeta) (err error) {
	if file.Addr, err = utils.FilePathWithDate(entitys.UploadFilePath, file.Sha1+path.Ext(file.Name)); err != nil {
		return
	}

	return
}
