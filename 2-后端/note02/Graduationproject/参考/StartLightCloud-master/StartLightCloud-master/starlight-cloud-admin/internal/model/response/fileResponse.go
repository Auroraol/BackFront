/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/23 10:03
 */

package response

import "time"

type FileResponse struct {
	ParentId  string    `gorm:"parent_id" json:"parent_id"`   //父结点id(NanoID)
	NodeId    string    `gorm:"node_id" json:"node_id"`       //结点唯一ID(NanoID)
	UpdatedAt time.Time `gorm:"updated_at" json:"updated_at"` //修改时间
	FileType  string    `gorm:"file_type" json:"file_type"`   //文件类型
	FileName  string    `gorm:"file_name" json:"file_name"`   //文件名
	Size      int64     `gorm:"size" json:"file_size"`        //文件大小
}
