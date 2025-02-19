/**
 * @Author: FxShadow
 * @Description:用户模型
 * @Date: 2023/03/15 22:31
 */

package request

type GetFileListRequest struct {
	UserId        string `json:"user_id" form:"user_id"`             //用户id
	CurrentNodeId string `json:"currentNodeId" form:"currentNodeId"` //当前结点/
}

type SearchFileListRequest struct {
	UserId string `json:"userId" form:"userId"` //用户id
	Prompt string `json:"prompt" form:"prompt"` //关键词
	NodeId string `json:"nodeId" form:"nodeId"` //当前结点
}

type DeleteFileRequest struct {
	CreatorId   string `json:"create_id" form:"create_id"`     //创建者id
	Count       int    `json:"count" form:"count"`             //普通文件删除数量
	FolderCount int    `json:"folderCount" form:"folderCount"` //文件夹删除数量
}

type DownloadFileRequest struct {
	UserId   string `json:"create_id" form:"create_id"` //用户id
	FileName string `json:"file_name" form:"fileName"`  //文件名
	Range    string `header:"Content-Range"`            //断点下载请求头
}

type CreateFolderRequest struct {
	UserId        string `json:"userId" form:"userId"`               //创建者id
	FileName      string `json:"folderName" form:"folderName"`       //文件名
	CurrentNodeId string `json:"currentNodeId" form:"currentNodeId"` //当前结点
	Role          int    `gorm:"role" json:"role"`                   //角色(0:user,1:admin)
}
