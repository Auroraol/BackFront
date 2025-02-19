/**
 * @Author: FxShadow
 * @Description:
 * @Date: 2023/03/16 15:28
 */

package dao

import (
	"fmt"
	"system-admin/starlight-cloud-admin/internal/model/common"
	"system-admin/starlight-cloud-admin/internal/model/response"
	"system-admin/starlight-cloud-admin/internal/util"
)

type FileDao struct{}

// 插入（行数）
func (fd *FileDao) Insert(file *common.File) (row int64) {
	db := util.GetDB()
	result := db.Table("files").Create(&file)
	return result.RowsAffected
}

// 删除（单）（行数）
func (fd *FileDao) Delete(file *common.File) (row int64) {
	db := util.GetDB()
	result := db.Delete(&file, 1)
	return result.RowsAffected
}

// 根据结点Id批量删除文件（多）（行数）
func (fd *FileDao) DeleteByNodeIdList(nodeIdList [100]string, creatorId string) (row int64) {
	db := util.GetDB()
	var deleteFile common.DeleteFile
	result := db.Debug().Table("files").Where("creator_id = ?", creatorId).Delete(&deleteFile, nodeIdList)
	return result.RowsAffected
}

// 树形结构删除文件夹
func (fd *FileDao) DeleteFolderByNodeIdList(nodeId string) (row int64) {
	db := util.GetDB()
	//var file common.File
	tx := db.Begin()
	sql := "WITH RECURSIVE subtree AS (\n  SELECT node_id\n  FROM files\n  WHERE node_id = '" + nodeId + "' \n\n  UNION ALL\n\n  SELECT f.node_id\n  FROM files f\n  JOIN subtree s ON f.parent_id = s.node_id\n)\nDELETE FROM files\nWHERE node_id IN (SELECT node_id FROM subtree);"
	result := tx.Debug().Exec(sql)

	if result.Error != nil {
		// 回滚事务
		tx.Rollback()
		return -1
	}

	tx.Commit()
	return result.RowsAffected
}

// 更新（单）（行数）
func (fd *FileDao) Update(file common.File, key string, value string) (row int64) {
	db := util.GetDB()
	db.First(&file, 1)
	result := db.Table("files").Model(&file).Update(key, value)
	return result.RowsAffected
	//db.Model(&file).Updates(Product{Price: 666, Code: "1003"}) //多
}

// 更新（更改当前文件）（行数）
func (fd *FileDao) UpdateByCurrentData(file common.File) (row int64) {
	db := util.GetDB()
	db.First(&file, 1)
	result := db.Table("files").Model(&file).Omit("node_id").Where("parent_id = ? AND file_name = ? AND file_type = ?", file.ParentId, file.FileName, file.FileType).Updates(&file)
	return result.RowsAffected

}

// 查询（单）（行数）
func (fd *FileDao) Query(file common.File, key string, value string) (row int64) {
	db := util.GetDB()
	result := db.Table("files").First(&file, key+" = ?", value)
	return result.RowsAffected
}

// 查询（单）（数据）
func (fd *FileDao) QueryData(file common.File, target string, key string, value string) common.File {
	db := util.GetDB()
	db.Table("files").Select(target).First(&file, key+" = ?", value)
	return file
}

// 树形结构查询文件/文件夹
func (fd *FileDao) QueryFileByNodeIdList(nodeId string, prompt string) (filesArray []response.FileResponse, row int64) {
	db := util.GetDB()
	tx := db.Begin()

	result := db.Raw("WITH RECURSIVE subtree AS ("+
		"SELECT node_id "+
		"FROM files "+
		"WHERE node_id = ? "+
		"UNION ALL "+
		"SELECT f.node_id "+
		"FROM files f "+
		"JOIN subtree s ON f.parent_id = s.node_id "+
		") "+
		"SELECT file_name,file_type,updated_at,size FROM files WHERE file_name LIKE "+"'%%"+prompt+"%%'", nodeId).Find(&filesArray)

	fmt.Printf("%+v\n", filesArray)

	if result.Error != nil {
		// 回滚事务
		tx.Rollback()
		return filesArray, -1
	}

	tx.Commit()
	return filesArray, result.RowsAffected

}

// 查询同父结点和创建者下的同名和同类型的资源（行数）
func (fd *FileDao) QuerySameNameAndTypeByParentIdAndCreatorId(file *common.File) (row int64) {
	db := util.GetDB()
	result := db.Table("files").Where("parent_id = ? AND file_name = ? AND file_type = ? AND creator_id = ?", file.ParentId, file.FileName, file.FileType, file.CreatorId).First(&file)
	return result.RowsAffected
}

// 查询同创建者，同父结点的文件名（描述），修改时间，类型，大小（根据文件类型为升序排序）（根据UID和父结点查询）
func (fd *FileDao) QueryFileInformationByUserIdAndParentId(userId string, parentId string) (filesArray []response.FileResponse, row int64) {
	db := util.GetDB()
	result := db.Table("files").Where("creator_id = ? AND parent_id = ?", userId, parentId).Select("parent_id", "node_id", "file_name", "updated_at", "file_type", "size").Omit("creator_id").Order("file_type desc").Find(&filesArray)
	return filesArray, result.RowsAffected
}

// 查询当前结点的父结点id
func (fd *FileDao) QueryFolderNodeIdByFolderParentId(file common.File) (nodeId string) {
	db := util.GetDB()
	db.Table("files").Where("node_id = ?", file.NodeId).Select("parent_id").First(&file)
	return file.ParentId
}
