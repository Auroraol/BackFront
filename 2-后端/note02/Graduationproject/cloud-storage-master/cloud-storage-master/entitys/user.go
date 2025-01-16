package entitys

type (
	//用户表
	User struct {
		Id        int
		UserName  string `field:"账号"`                       //用户名
		Password  string `field:"密码"`                       //密码
		Salt      string `json:"-"`                         //密码加密盐
		Name      string `field:"名称"  `                     //姓名
		Mobile    string `field:"手机号"  valid:"Mobile"`      //手机号码
		Email     string `field:"邮箱"  valid:"Email"`        //邮箱
		Memo      string `field:"备注"  valid:"MaxSize(20);"` //备注
		Enable    int    //用户状态：1=启用,2=禁用
		IsSuper   int    //超级用户标识：1=超级用户,其他=非超级用户
		CreatedAt Time   //创建时间
		UpdatedAt Time   //更新时间
		DeletedAt *Time  `json:"-"`
	}

	//用户文件 中间表
	UserFile struct {
		Id       int
		UserId   int    `field:"用户id"`
		FileSha1 string `field:"文件Sha1"`
		FileSize int64  `field:"文件大小"`
		FileName string `field:"文件名称"`
		Status   int    `field:"状态"` //1=正常，2=禁用

		CreatedAt Time  //创建时间
		UpdatedAt Time  //更新时间
		DeletedAt *Time `json:"-"`
	}

	//获取用户文件列表
	UserFilesReq struct {
		Pager
		UserId int //用户id
	}
	UserFilesAck struct {
		Total int
		Data  []UserFile
	}
)

const (
	//用户文件状态
	UserFileStatusEnable  = 1
	UserFileStatusDisable = 2
)
