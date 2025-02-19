/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 13:25
 */

package service

import (
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"golang.org/x/time/rate"
	"io"
	"net/http"
	"os"
	"strconv"
	"strings"
	"system-admin/starlight-cloud-admin/internal/dao"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/request"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type FileService struct{}

func GetFileService() (fileService FileService) {
	return fileService
}

// todo
// UploadFile 文件上传服务
func (fs *FileService) UploadFile(c *gin.Context) (message response.MessageResponse) {
	println("收到了文件上传请求")
	fileData, _ := c.FormFile("file")     //获取文件原数据
	userId := c.PostForm("user_id")       //拿到用户id
	nodeId := c.PostForm("currentNodeId") //拿到当前结点id

	//将数据绑定到新的结构体(载入文件相关信息进入数据库，不存储文件数据)
	file := common.File{}
	//todo
	//绑定父结点id
	file.ParentId = nodeId

	//todo
	//调用NanoID对结点进行id分配
	file.NodeId = util.NanoIdGenerate()

	fileNameSplitArray := strings.Split(fileData.Filename, ".")   //切割文件名
	file.CreatorId = userId                                       //绑定创建者id
	file.FileName = fileNameSplitArray[0]                         //绑定文件名字
	file.FileType = fileNameSplitArray[len(fileNameSplitArray)-1] //绑定文件类型
	file.Size = fileData.Size                                     //绑定文件大小
	file.Description = fileData.Filename                          //绑定描述（默认与文件同名）

	var fileDao dao.FileDao

	userDao := dao.UserDao{}
	//判断用户云盘存储空间是否允许上传
	//获取用户云盘存储大小
	user := common.User{UserId: userId}
	userDao.QueryInfoByUserId(&user)

	//减少用户云盘存储
	userRAM, err := strconv.ParseInt(user.RAM, 10, 64)
	if err != nil {
		fmt.Println(err)
	}
	currentRAM := userRAM - file.Size

	//如果用户云盘存储空间小于0，则禁止上传
	if currentRAM < 0 {
		message = response.MessageResponse{
			Code: util.FileUploadUnusual,
			Msg:  "文件上传失败，存储空间已满",
			Data: "null",
		}
		return
	}

	//更新用户云盘存储
	userDao.Update(user, "ram", strconv.FormatInt(currentRAM, 10))

	//根据同父结点下资源名字和类型是否相同，再加入数据库
	result := fileDao.QuerySameNameAndTypeByParentIdAndCreatorId(&file)

	//如果=0说明没找到，意为新文件
	if result == 0 {
		print("未找到相同结点的资源文件类型")

		result = fileDao.Insert(&file)
		if result > 0 {
			//将文件信息导入数据库并且存储文件于服务器

			//数据库判断该用户的角色
			level := userDao.QueryLevelByUserId(&common.User{UserId: userId})
			role := util.LevelChangeRole(level)

			//(将文件存储于对应文件夹内)

			//维护一个文件关系层和结点文件名层数数组
			var folderLayer string
			var folderLayerArray [30]string

			//维护folderLayerArray的长度
			var folderLayerArrayLen = 0

			//先将当前结点文件名加入列表再遍历剩余的结点文件名
			currentFile := fileDao.QueryData(common.File{}, "file_name", "node_id", nodeId)

			folderLayerArray[0] = currentFile.FileName
			for i := 1; nodeId != ""; i++ {

				nodeId = fileDao.QueryFolderNodeIdByFolderParentId(common.File{NodeId: nodeId})
				if nodeId == "" {
					break
				}

				//如果请求结点等于最开始的结点则break
				if nodeId == userId {
					break
				}

				//根据结点查询文件名
				fileModel := fileDao.QueryData(common.File{}, "file_name", "node_id", nodeId)

				fmt.Printf("filemodel::%+v", fileModel)

				//添加进文件夹关系层
				folderLayerArray[i] = fileModel.FileName
				folderLayerArrayLen++
			}

			//连接文件层字符
			for i := folderLayerArrayLen; i >= 0; i-- {
				if folderLayerArray[i] == "" {
					break
				}

				folderLayer += "/" + folderLayerArray[i]
			}

			//创建文件夹的路径
			saveUploadedFileLocation := "../../starlight-cloud-admin/fileRepository/" + role + "/" + userId + folderLayer + "/" + file.FileName + "." + file.FileType

			err := c.SaveUploadedFile(fileData, saveUploadedFileLocation)
			if err != nil {
				fmt.Printf("上传文件出错，错误原因：%+v", err)
				return
			}

			print("文件导入数据库成功")
			message = response.MessageResponse{
				Code: util.FileUploadOK,
				Msg:  "文件上传成功",
				Data: "null",
			}
			return

		} else {
			//导入失败
			print("文件导入数据库失败")
			message = response.MessageResponse{
				Code: util.FileUploadFailed,
				Msg:  "文件上传失败",
				Data: "null",
			}
			return
		}
	} else {
		print("更新数据库文件中")
		//更新当前结点数据
		result := fileDao.UpdateByCurrentData(file)
		if result > 0 {
			//更新成功

			//数据库判断该用户的角色
			userDao := dao.UserDao{}
			level := userDao.QueryLevelByUserId(&common.User{UserId: userId})
			role := util.LevelChangeRole(level)

			//维护一个文件关系层和结点文件名层数数组
			var folderLayer string
			var folderLayerArray [30]string

			//维护folderLayerArray的长度
			var folderLayerArrayLen = 0

			//先将当前结点文件名加入列表再遍历剩余的结点文件名
			currentFile := fileDao.QueryData(common.File{}, "file_name", "node_id", nodeId)

			folderLayerArray[0] = currentFile.FileName
			for i := 1; nodeId != ""; i++ {

				nodeId = fileDao.QueryFolderNodeIdByFolderParentId(common.File{NodeId: nodeId})
				if nodeId == "" {
					break
				}

				//如果请求结点等于最开始的结点则break
				if nodeId == userId {
					break
				}

				//根据结点查询文件名
				fileModel := fileDao.QueryData(common.File{}, "file_name", "node_id", nodeId)

				fmt.Printf("filemodel::%+v", fileModel)

				//添加进文件夹关系层
				folderLayerArray[i] = fileModel.FileName
				folderLayerArrayLen++
			}

			//连接文件层字符
			for i := folderLayerArrayLen; i >= 0; i-- {
				if folderLayerArray[i] == "" {
					break
				}

				folderLayer += "/" + folderLayerArray[i]
			}

			//创建文件夹的路径
			saveUploadedFileLocation := "../../starlight-cloud-admin/fileRepository/" + role + "/" + userId + folderLayer + "/" + file.FileName

			//将新文件信息更新数据库并且存储文件于服务器

			//saveUploadedFileLocation := "../../starlight-cloud-admin/fileRepository/" + role + "/" + userId + "/" + userId
			err := c.SaveUploadedFile(fileData, saveUploadedFileLocation+"_"+fileData.Filename)
			if err != nil {
				fmt.Printf("上传文件出错，错误原因：%+v", err)
				return
			}

			print("更新数据库文件成功")
			message = response.MessageResponse{
				Code: util.FileUpdateOK,
				Msg:  "更新文件成功",
				Data: "null",
			}
			return
		} else {
			print("更新数据库文件失败")
			message = response.MessageResponse{
				Code: util.FileUpdateFailed,
				Msg:  "更新文件失败",
				Data: "null",
			}
			return
		}
	}
}

// GetFileList 获取当前结点的资源列表
func (fs *FileService) GetFileList(c *gin.Context) (message response.MessageResponse) {
	var getFileListRequest request.GetFileListRequest
	err := c.ShouldBind(&getFileListRequest)
	if err != nil {
		fmt.Println(err)
		return
	}

	file := common.File{}
	file.CreatorId = getFileListRequest.UserId       //绑定创建者id
	file.ParentId = getFileListRequest.CurrentNodeId //绑定父结点id

	//todo
	//获取文件列表（默认为获取“我的云盘/”下的资源）
	fileDao := dao.FileDao{}
	fileArray, row := fileDao.QueryFileInformationByUserIdAndParentId(file.CreatorId, file.ParentId)

	//转换成JSON
	FileResponse, err := json.Marshal(fileArray)
	if err != nil {
		fmt.Println(err)
	}

	if row > 0 {
		message = response.MessageResponse{
			Code: util.GetFileListOK,
			Msg:  "获取文件列表成功",
			Data: string(FileResponse),
		}
		return
	} else {
		message = response.MessageResponse{
			Code: util.GetFileListFailed,
			Msg:  "获取文件列表失败",
			Data: "null",
		}
		return
	}

}

// DeleteFile 删除文件
func (fs *FileService) DeleteFile(c *gin.Context) (message response.MessageResponse) {
	fmt.Println("删除请求如下")
	var deleteFileRequest request.DeleteFileRequest
	err := c.ShouldBind(&deleteFileRequest)
	if err != nil {
		return response.MessageResponse{}
	}

	//结点列表
	var nodeIdList [100]string
	//文件名列表（带文件后缀）
	var fileNameList [100]string
	//文件夹列表
	var folderNodeIdList [10]string

	fmt.Println("fileNameList::", fileNameList)

	//循环获取具体结点id和名字加入对应的列表
	for i := 1; i <= deleteFileRequest.Count; i++ {
		nodeIdList[i] = c.Query("nodeIdList[" + strconv.Itoa(i) + "]")
		fileNameList[i] = c.Query("fileNameList[" + strconv.Itoa(i) + "]")
	}

	//循环获取具体文件夹结点id加入列表
	for i := 1; i <= deleteFileRequest.FolderCount; i++ {
		folderNodeIdList[i] = c.Query("folderNodeIdList[" + strconv.Itoa(i) + "]")
	}

	//一次性删除结点里的数据
	fileDao := dao.FileDao{}
	deleteByNodeIdListRow := fileDao.DeleteByNodeIdList(nodeIdList, deleteFileRequest.CreatorId)

	//删除与文件夹结点相关的数据并统计行数
	var folderCount int64
	for i := 1; i < len(folderNodeIdList) && folderNodeIdList[i] != ""; i++ {
		folderCount += fileDao.DeleteFolderByNodeIdList(folderNodeIdList[i])
	}
	fmt.Println("folderCount", folderCount)

	//获取权限
	var userDao dao.UserDao
	level := userDao.QueryLevelByUserId(&common.User{UserId: deleteFileRequest.CreatorId})
	role := util.LevelChangeRole(level)

	//todo
	//deleteByNodeIdListRow有问题，下面的部分不会进入
	fmt.Println("deleteByNodeIdListRow::", deleteByNodeIdListRow)

	//删除服务器里的文件
	//规则一：删除文件夹内的数据库记录，但保留服务端的文件
	//规则二：如果文件在首页，则直接清除该文件+数据库记录
	if deleteByNodeIdListRow > 0 {

		//拼接位置
		path := "../../starlight-cloud-admin/fileRepository/" + role + "/" + deleteFileRequest.CreatorId + "/"
		fmt.Println("path:::", path)
		for i := 1; i < deleteFileRequest.Count+1; i++ {
			fileName := fileNameList[i]
			fmt.Println("fileName", fileName)
			err := os.Remove(path + fileName)
			if err != nil {
				fmt.Println(err)
				return
			}
		}

		fmt.Println("文件删除成功")

		message = response.MessageResponse{
			Code: util.DeleteFileOK,
			Msg:  "删除文件成功",
			Data: "null",
		}

		return

	} else {

		message = response.MessageResponse{
			Code: util.DeleteFileFailed,
			Msg:  "删除文件失败",
			Data: "null",
		}

		return

	}
}

// DownloadFile 下载文件
func (fs *FileService) DownloadFile(c *gin.Context) {
	fmt.Println("下载请求如下")
	var downloadFileRequest request.DownloadFileRequest
	err := c.BindQuery(&downloadFileRequest)
	if err != nil {
		return
	}

	err = c.BindHeader(&downloadFileRequest)
	if err != nil {
		return
	}

	fmt.Printf("%+v", downloadFileRequest)

	//获取用户权限
	var userDao dao.UserDao
	level := userDao.QueryLevelByUserId(&common.User{UserId: downloadFileRequest.UserId})
	role := util.LevelChangeRole(level)

	//获取本地路径
	path := "../../starlight-cloud-admin/fileRepository/" + role + "/" + downloadFileRequest.UserId + "/"

	//获取该文件的文件路径

	//读取本地路径的文件
	file, _ := os.Open(path + downloadFileRequest.FileName)

	//获取文件元数据
	fileData, err := os.Stat(path + downloadFileRequest.FileName)
	if err != nil {
		fmt.Println(err)
	}

	//请求头设置
	c.Writer.WriteHeader(http.StatusOK)
	c.Writer.Header().Add("Content-Disposition", "attachment; filename="+downloadFileRequest.FileName)
	c.Writer.Header().Add("Content-Type", "application/octet-stream;charset=UTF-8")
	c.Header("Accept-Length", fmt.Sprintf("%d", fileData.Size()))

	//发送数据
	//c.File(path + fileName)

	//限速
	var limiter = rate.NewLimiter(1024*1024*5, 1024*1024*5) // 5 MB/s

	buf := make([]byte, 512)
	for {
		n, err := file.Read(buf)
		if err == io.EOF {
			break
		}
		if err != nil {
			c.AbortWithError(http.StatusInternalServerError, err)
			return
		}

		// 检查是否达到限速的最大阀值
		if err := limiter.WaitN(c.Request.Context(), n); err != nil {
			c.AbortWithError(http.StatusTooManyRequests, err)
			return
		}

		c.Writer.Write(buf[:n])
	}

	//给前端写入文件数据流
	//_, err = c.Writer.Write(content[0:len(content)])
	//if err != nil {
	//	fmt.Println(err)
	//}

	fmt.Print("okok!!!!")

	return
}

// CreateFolder 新建文件夹
func (fs *FileService) CreateFolder(c *gin.Context) (message response.MessageResponse) {
	fmt.Println("新建文件夹请求如下")
	var createFolderRequest request.CreateFolderRequest
	err := c.ShouldBindQuery(&createFolderRequest)
	if err != nil {
		fmt.Println(err)
	}

	var fileDao dao.FileDao

	//查询同父结点下是否有相同的文件夹
	row := fileDao.QuerySameNameAndTypeByParentIdAndCreatorId(&common.File{
		CreatorId: createFolderRequest.UserId,
		ParentId:  createFolderRequest.CurrentNodeId,
		FileName:  createFolderRequest.FileName,
		FileType:  "文件夹",
	})

	//维护一个文件关系层和结点文件名层数数组
	var folderLayer string
	var folderLayerArray [30]string

	//维护folderLayerArray的长度
	folderLayerArrayLen := 0

	//没找到就插入
	if row == 0 {
		file := common.File{
			CreatorId:   createFolderRequest.UserId,
			ParentId:    createFolderRequest.CurrentNodeId,
			NodeId:      util.NanoIdGenerate(),
			FileName:    createFolderRequest.FileName,
			FileType:    "文件夹",
			Description: createFolderRequest.FileName,
			Size:        0,
		}

		//插入文件夹数据
		fileDao.Insert(&file)
		//查询文件树结构
		nodeId := file.NodeId

		for i := 0; nodeId != ""; i++ {

			nodeId = fileDao.QueryFolderNodeIdByFolderParentId(common.File{NodeId: nodeId})
			if nodeId == "" {
				break
			}

			//如果请求结点等于最开始的结点则break
			if nodeId == createFolderRequest.UserId {
				break
			}

			//根据结点查询文件名
			fileModel := fileDao.QueryData(common.File{}, "file_name", "node_id", nodeId)

			//添加进文件夹关系层
			folderLayerArray[i] = fileModel.FileName
			folderLayerArrayLen++
			fmt.Println("folderLayerArrayLen++")
		}

		//连接文件层字符
		for i := folderLayerArrayLen - 1; i >= 0; i-- {
			if folderLayerArray[i] == "" {
				break
			}
			fmt.Println("i:", i)
			fmt.Println("folderLayerArray:", folderLayerArray[i])

			folderLayer += "/" + folderLayerArray[i]
		}

		//转换用户权限等级
		role := util.LevelChangeRole(createFolderRequest.Role)
		userId := createFolderRequest.UserId

		//创建文件夹的路径
		path := "../../starlight-cloud-admin/fileRepository/" + role + "/" + userId + "/" + folderLayer + "/" + file.FileName

		//在服务端创建文件夹
		err := os.MkdirAll(path, os.ModePerm)
		if err != nil {
			fmt.Println("创建目录失败", err)
			message = response.MessageResponse{
				Code: util.CreateFolderFailed,
				Msg:  "创建文件夹失败",
				Data: "null",
			}
			return
		}

		message = response.MessageResponse{
			Code: util.CreateFolderOK,
			Msg:  "创建文件夹成功",
			Data: "null",
		}

	} else {
		fmt.Println("failed,has been exist")
		message = response.MessageResponse{
			Code: util.CreateFolderFailed,
			Msg:  "创建文件夹失败",
			Data: "null",
		}
	}

	return
}

// GetSearchFileList 查询文件列表
func (fs *FileService) SearchFileList(c *gin.Context) (message response.MessageResponse) {
	fmt.Println("收到了查询请求")

	var searchFileListRequest request.SearchFileListRequest
	err := c.ShouldBind(&searchFileListRequest)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Printf("hahaha::%+v\n", searchFileListRequest)

	//todo
	//获取文件列表（默认为获取“我的云盘/”下的资源）
	fileDao := dao.FileDao{}
	fileArray, row := fileDao.QueryFileByNodeIdList(searchFileListRequest.NodeId, searchFileListRequest.Prompt)

	for _, v := range fileArray {
		fmt.Println(v.FileName + "-" + v.FileType + "-" + string(v.Size))
	}

	//转换成JSON
	FileResponse, err := json.Marshal(fileArray)
	if err != nil {
		fmt.Println(err)
	}

	if row > 0 {
		message = response.MessageResponse{
			Code: util.GetSearchFileListOK,
			Msg:  "获取查询文件列表成功",
			Data: string(FileResponse),
		}
		return
	} else {
		message = response.MessageResponse{
			Code: util.GetSearchFileListFailed,
			Msg:  "获取查询文件列表失败",
			Data: "null",
		}
		return
	}

}
