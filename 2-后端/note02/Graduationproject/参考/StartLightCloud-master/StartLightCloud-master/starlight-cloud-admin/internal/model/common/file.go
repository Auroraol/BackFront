/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/18 20:35
 */

package common

import (
	"gorm.io/gorm"
)

type File struct {
	gorm.Model
	CreatorId   string `gorm:"creator_id" json:"creator_id"`   //创建者id
	ParentId    string `gorm:"parent_id" json:"parent_id"`     //父结点id(NanoID)
	NodeId      string `gorm:"node_id" json:"node_id"`         //结点唯一ID(NanoID)
	FileName    string `gorm:"file_name" json:"file_name"`     //文件名字
	FileType    string `gorm:"file_type" json:"file_type"`     //文件类型
	Description string `gorm:"description" json:"description"` //文件描述
	Size        int64  `gorm:"file_size" json:"file_size"`     //文件大小
	//Path        string `gorm:"path" json:"path"`               //全路径
}

// 删除模型
type DeleteFile struct {
	NodeId string `gorm:"node_id" json:"node_id"` //结点唯一ID(NanoID)
}
