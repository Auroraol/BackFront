package entitys

type (
	//文件信息
	FileMeta struct {
		Id        int
		Sha1      string `field:"文件唯一值"`
		Name      string `filed:"文件名称"`
		Size      int64  `field:"文件大小"` //bit
		Addr      string `field:"文件路径"`
		Status    int    `field:"状态"` //状态 1=可用，2=禁用
		CreatedAt Time
		UpdatedAt Time
		DeletedAt *Time `json:"-"`
		Ext1      int
		Ext2      *string
	}

	//断点续传，分块信息
	MultipartUploadInfo struct {
		FileName      string `field:"文件名称"`
		FileHash      string `field:"文件hash"`
		FileSize      int64  `field:"文件大小"`
		UploadId      string `field:"上传id"`
		ChunkSize     int    `field:"分块大小"`
		ChunkCount    int    `field:"分块数量"`
		CompleteCount int    //已完成的上传块数量
	}

	//分块上传信息
	MultipartUpload struct {
		UploadId string `field:"上传id"`
		Index    int    `field:"分块索引"`
	}
)

const (
	FileMetaStatusEnable  = 1
	FileMetaStatusDisable = 2

	//分块上传缓存前缀
	MultipartUploadTmpPath     = "./static/upload/tmp"
	MultipartUploadFilePath    = MultipartUploadTmpPath + "/%s/%d"
	MultipartUploadCachePrefix = "InitMultipart:%s"
	MultipartUploadChunkIndex  = "chunkId:"
)
