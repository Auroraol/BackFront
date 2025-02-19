package s

import (
	"github.com/jinzhu/gorm"
	"log"
)

type TransactionClosure = func(tx *gorm.DB) error

//嵌套事务事务代理
type Transactor struct {
	Num uint32   //事务计数器
	o   *gorm.DB //orm对象
}

func NewTransactor() *Transactor {
	this := &Transactor{
		o: Db.New(),
	}

	return this
}

//事务闭包
func (this *Transactor) Transaction(f TransactionClosure) error {
	var err error
	if this.Num == 0 {
		//开启事务
		this.o = this.o.Begin()
	}
	this.Num++

	//调用逻辑
	if err = f(this.o); err != nil {
		//回滚
		if this.Num > 1 {
			this.Num--
		} else {
			//回滚
			if terr := this.o.Rollback().Error; terr != nil {
				log.Println("事务回滚异常:" + terr.Error())
			}
		}
		return err
	} else {
		//提交
		if this.Num > 1 {
			this.Num--
		} else {
			//提交
			if terr := this.o.Commit().Error; terr != nil {
				log.Println("事务提交异常:" + terr.Error())
			}
		}
	}
	return nil
}
