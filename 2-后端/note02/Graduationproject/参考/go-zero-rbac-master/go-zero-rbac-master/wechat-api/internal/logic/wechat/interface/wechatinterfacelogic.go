/*
 * # 此文件版权属于ASO.DESIGN
 * # 文件：wechatinterfacelogic.go  项目：aso-zero
 * # 作用：
 * # 当前修改时间：2021年07月18日 01:05:05
 * # 上次修改时间：2021年07月16日 15:37:15
 * # 作者：thunur
 * # 此文件不可非法传播、倒卖、共享，否则我们将追究相应的法律责任。
 * # 您如果已获得ASO.DESIGN授权可在原有基础上进行修改使用
 * # 如果您还没获得授权请联系我们 thunur@qq.com
 * # Copyright (c) 2021 aso.design
 */

package logic

import (
	"aso/rpc/wechat/wechatclient"
	"aso/utils/common/errorx"
	"aso/utils/gconv"
	"aso/wechat-api/internal/svc"
	"context"
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/silenceper/wechat/v2/officialaccount/message"
	"github.com/tal-tech/go-zero/core/logx"
)

type WechatInterfaceLogic struct {
	logx.Logger
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

type materialData struct {
	Url              string `json:"url,optional"`                // 地址
	Type             string `json:"type,optional"`               // 类型
	Title            string `json:"title,optional"`              // 标题
	Author           string `json:"author,optional"`             // 作者
	Cover            string `json:"cover,optional"`              // 图片封面
	Summary          string `json:"summary,optional"`            // 摘要
	Content          string `json:"content"`                     // 正文
	ContentSourceUrl string `json:"content_source_url,optional"` // 原文URL
	Sort             int64  `json:"sort,optional"`               // 排序
	Hits             int64  `json:"hits,optional"`               // 阅读次数
	MediaKey         int64  `json:"media_key,optional"`
}

// 自动回复
type autoReply struct {
	Active  string         `json:"active"`
	Media   []materialData `json:"media,optional"`
	Mediaid int64          `json:"media_id,optional"`
	Text    string         `json:"text,optional"`
	Url     string         `json:"url,optional"`
}

type attention struct {
	Active  string         `json:"active"`
	Media   []materialData `json:"media,optional"`
	MediaId int64          `json:"media_id,optional"`
	Text    string         `json:"text,optional"`
	Url     string         `json:"url,optional"`
}

type replyData struct {
	AutoReply autoReply `json:"autoReply"`
	Attention attention `json:"attention"`
}

func NewWechatInterfaceLogic(ctx context.Context, svcCtx *svc.ServiceContext) WechatInterfaceLogic {
	return WechatInterfaceLogic{
		Logger: logx.WithContext(ctx),
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

type menu struct {
	Id        int64
	pid       int64
	Type      string
	Name      string
	Url       string
	MediaId   int64
	MsgType   string
	AppId     string
	PagePath  string
	Text      string
	Pic       string
	SubButton []menu
}

// WechatInterface 微信获取消息以及返回接口
// @Description: 微信开放平台模式(需要对消息进行加密和解密)
// @receiver l
// @param w
// @param r
// @return error
func (l *WechatInterfaceLogic) WechatInterface(w http.ResponseWriter, r *http.Request) error {
	var cfg map[string]string
	info, err := l.svcCtx.Wechat.Info(l.ctx, &wechatclient.WechatInfoRequest{
		Type: 1,
	})
	if err != nil {
		logx.Error("微信配置获取失败：", err.Error())
		return err
	}
	cfg = gconv.JsonToMap(info.Value)
	// 初始化微信
	ex := l.svcCtx.WechatSdk.Register(cfg)
	// 传入request和responseWriter
	server := ex.Wechat.GetServer(r, w)
	// 跳过验证
	//server.SkipValidate(true)
	// 自动回复和关注回复数据查询
	var defaultReply *replyData
	result, err := l.svcCtx.Wechat.Info(l.ctx, &wechatclient.WechatInfoRequest{
		Type: 4,
	})
	if err != nil {
		logx.Error("微信配置获取失败：", err.Error())
		return nil
	}
	err = json.Unmarshal([]byte(result.Value), &defaultReply)
	if err != nil {
		logx.Error("转结构体失败：", err.Error())
	}
	//设置接收消息的处理方法
	server.SetMessageHandler(func(msg *message.MixMessage) *message.Reply {
		// 判断消息类型
		// 菜单点击消息恢复
		switch msg.Event {
		case message.EventClick:
			fmt.Printf("事件消息：%s", message.EventClick)
			fmt.Printf("事件消息：%s", msg.EventKey)
			data, err := l.svcCtx.Wechat.MenuInfo(l.ctx, &wechatclient.MenuInfoReq{
				Id: gconv.Int64(msg.EventKey),
			})
			if err != nil {
				logx.Error(err.Error())
				text := message.NewText(err.Error())
				return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
			}
			switch data.MsgType {
			case "picmsg":
				articleList, err := l.getMediaItem(data.MediaID)
				if err != nil {
					return nil
				}
				news := message.NewNews(articleList)
				return &message.Reply{MsgType: message.MsgTypeNews, MsgData: news}
			case "text":
				text := message.NewText(data.Text)
				return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
			}
		//	关注事件
		case message.EventSubscribe:
			switch defaultReply.Attention.Active {
			case "text":
				text := message.NewText(defaultReply.Attention.Text)
				return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
			case "picmsg":
				articleList, _ := l.getMediaItem(defaultReply.Attention.MediaId)
				news := message.NewNews(articleList)
				return &message.Reply{MsgType: message.MsgTypeNews, MsgData: news}
			default:
				text := message.NewText("欢迎关注！")
				return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
			}
		//	取消关注事件
		case message.EventUnsubscribe:
			fmt.Printf("事件消息：%s", message.EventUnsubscribe)
			break
		//	扫码事件
		case message.EventScan:
			fmt.Printf("事件消息：%s", message.EventScan)
			break
		//	上报定位
		case message.EventLocation:
			fmt.Printf("事件消息：%s", message.EventLocation)
			break
		//	点击菜单浏览网页
		case message.EventView:
			fmt.Printf("事件消息：%s", message.EventView)
			break
		//	扫码推事件的事件推送
		case message.EventScancodePush:
			fmt.Printf("事件消息：%s", message.EventScancodePush)
			break
		}

		// 默认回复
		replyMessage, _ := l.svcCtx.Wechat.ReplyMessage(l.ctx, &wechatclient.ReplyMessageReq{
			Key: msg.Content,
		})
		if replyMessage != nil {
			switch replyMessage.Type {
			// 图文回复只支持一条
			case "picmsg":
				articleList, _ := l.getMediaItem(replyMessage.MediaId)
				news := message.NewNews(articleList)
				return &message.Reply{MsgType: message.MsgTypeNews, MsgData: news}
			case "text":
				text := message.NewText(replyMessage.Text)
				return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
			}
		}
		// 默认回复
		switch defaultReply.AutoReply.Active {
		case "text":
			text := message.NewText(defaultReply.AutoReply.Text)
			return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
		case "picmsg":
			articleList, _ := l.getMediaItem(defaultReply.AutoReply.Mediaid)
			news := message.NewNews(articleList)
			return &message.Reply{MsgType: message.MsgTypeNews, MsgData: news}
		default:
			text := message.NewText("这个高级功能我还没学会！")
			return &message.Reply{MsgType: message.MsgTypeText, MsgData: text}
		}
		//voice := message.NewVoice(mediaID)
		//return &message.Reply{MsgType: message.MsgTypeVoice, MsgData: voice}

		//
		//video := message.NewVideo(mediaID, "标题", "描述")
		//return &message.Reply{MsgType: message.MsgTypeVideo, MsgData: video}

		//music := message.NewMusic("标题", "描述", "音乐链接", "HQMusicUrl", "缩略图的媒体id")
		//return &message.Reply{MsgType: message.MsgTypeMusic, MsgData: music}

		//多客服消息转发
		//transferCustomer := message.NewTransferCustomer("")
		//return &message.Reply{MsgType: message.MsgTypeTransfer, MsgData: transferCustomer}
	})

	// 处理消息接收以及回复
	err = server.Serve()
	if err != nil {
		fmt.Println("错误", err)
		return errorx.NewDefaultError(err.Error())
	}
	// 发送回复的消息
	err = server.Send()
	if err != nil {
		fmt.Println("错误", err)
		return errorx.NewDefaultError(err.Error())
	}
	return nil
}

func (l *WechatInterfaceLogic) getMediaItem(mediaId int64) ([]*message.Article, error) {
	articleList, err := l.svcCtx.Wechat.MaterialItem(l.ctx, &wechatclient.WechatMaterialItemReq{
		MediaId: mediaId,
	})
	if err != nil {
		return nil, err
	}
	// 转成sdk结构体
	article := make([]*message.Article, 0)
	for _, v := range articleList.List {
		article = append(article, &message.Article{
			Title:       v.Title,
			Description: v.Summary,
			PicURL:      v.Cover,
			URL:         v.Url,
		})
	}
	return article, nil
}
