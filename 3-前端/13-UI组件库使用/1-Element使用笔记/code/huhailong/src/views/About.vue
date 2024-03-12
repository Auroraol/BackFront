<template>
  <div class="about">
    <div v-blog-title data-title="关于"></div>
    <div class="topBackground">
      <div class="title">关于我和我的网站</div>
      <div class="subtitle"><i class="fa fa-envelope"></i>联系方式: 151412413@qq.com</div>
    </div>
    <div class="content">
      <el-row>
        <el-col :lg="6" :xs="{span:22,offset:1}" :sm="{span:22,offset:1}">
          <!-- <div class="mylink">
            <div class="header"><i class="fa fa-link"></i>相关链接</div>
            <div class="mybody">
              <div class="body-item">
                <img style="width: 40px" src="../assets/bilibili.png" />
                <div><el-link href="https://space.bilibili.com/326117321">哔哩哔哩</el-link></div>
              </div>
              <div class="body-item">
                <img style="width: 40px" src="../assets/csdn.png" />
                <div><el-link href="https://blog.csdn.net/hhl18730252820">CSDN</el-link></div>
              </div>
              <div class="body-item">
                <img style="width: 40px" src="../assets/gitee.png" />
                <div><el-link href="https://gitee.com/hlovez">Gitee</el-link></div>
              </div>
              <div class="body-item" @click="showSmallApp=true">
                <img style="width: 40px" src="../assets/smallapp.png" />
                <div><el-link >菜谱小程序</el-link></div>
              </div>
            </div>
          </div> -->
          <el-dialog width="50%" title="小胡菜谱" :visible.sync="showSmallApp">
            <div class="showappbox">
              <div class="left">
                <p>基于微信小程序云开发的菜谱小程序，最新版本抛弃了之前使用Java作为服务端的方式，采用了Nodejs的方式完成
                  所有的服务端接口，这样可以省去服务器的开销，可以更稳定的运行下去。Nodejs爬去网页内容可以查看下面的demo带嘛。
                </p>
                <p><a href="https://gitee.com/hlovez/node-reptile.git">基于Nodejs的爬虫Demo（以爬取菜谱为例）</a></p>
              </div>
              <div class="right">
                <el-image style="width:60%" src="https://img-blog.csdnimg.cn/2e32c9ad341f413f9b7cdcc8b6991637.png"></el-image>
              </div>
            </div>
          </el-dialog>
          <!-- <div class="mylink" style="margin: 2rem 0">
            <div class="header">
              <i class="fa fa-line-chart"></i>网站访问次数
            </div>
            <div class="mybody">
              <animate-number
                class="num"
                from="1"
                :to="total"
                :key="total"
                duration="500"
                easing="easeOutQuad"
              ></animate-number>
            </div>
          </div> -->
        </el-col>
        <el-col :lg="{ span: 14, offset: 5 }" :xs="{span:22,offset:2}" :sm="{span:22,offset:1}">
          <div class="mydoc">
            <div class="header"><i class="fa fa-smile-o"></i>自我介绍</div>
            <div class="mybody">
              <p>
                Hello，欢迎访问我的网站!我现在是一只程序猿，工作中主要是是负责Java服务端的一些开发工作，但前端技术我也很喜欢，所以也会自己做一些小程序和网站，这样在可以边学习边找乐趣。
              </p>
              <p>
                这个网站是我业余时间做的，主要用来记录和做一些测试，间歇性的发布一些内容（可能只是为了测试），目前还在不断（有空的时候）完善中，为了记录一些东西，所以先把博客功能放上去
                目前支持发布文章（现在只对管理员角色开放），以及评论和回复功能。这个网站是基Spring Cloud开发的。前端使用Vue+Element-UI,由于囊中羞涩，云服务器的配置很低，所以采用的
                是使用家里闲置的笔记本（两台退休的笔记本）来充当服务器，通过使用frp内网穿透的方式来映射到这个公网服务器的，因此网络可能有时候不太稳定（一般情况下还行）。
              </p>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import * as blogApi from '@/api/blog/api.js'
export default {
  name: "About",
  data() {
    return {
      total: '0',
      showSmallApp: false
    };
  },
  methods: {
    count(){
      blogApi.countView().then(res=>{
        this.total = res.data.toString()
        console.log(typeof(this.total))
      })
    }
  },
  mounted(){
    this.count()
  }
};
</script>
<style lang="scss" scoped>
.about {
  min-height: calc(100vh - 8rem);
}
.showappbox{
  display: flex;
  .left{
    text-align: left;
  }
}
.topBackground {
  // background-image: url('../assets/topbackground.jpeg');
  // background-color: #333333;
  width: 100%;
  height: 10rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  // box-shadow: 2px 3px 10px #333333;
  .title {
    color: #ffffff;
    font-weight: bold;
    font-size: 28px;
  }
  .subtitle{
    padding-top: 1rem;
    color: rgba($color: #ffffff, $alpha: 0.6);
    font-weight: bold;
  }
  .avatar {
    position: absolute;
    top: 180px;
    right: 10px;
  }
}
.content {
  margin: 1rem;
  .mylink {
    height: 18rem;
    border-radius: 4px;
    background: rgba($color: #ffffff, $alpha: 0.5);
    box-shadow: 2px 3px 10px rgb(51,72,94);
    .mybody {
      padding: 10px;
      display: flex;
      align-items: center;
      justify-content: space-around;
      .body-item {
        cursor: pointer;
      }
      .num {
        font-size: 55px;
        font-weight: bold;
      }
    }
  }
  .mydoc {
    border-radius: 4px;
    min-height: 20rem;
    box-shadow: 2px 3px 10px rgb(51,72,94);
    text-align: left;
    .mybody{
      padding: 10px;
    }
    p {
      color: white;
      text-indent: 30px;
      line-height: 24px;
    }
  }
}
.header {
  padding: 10px;
  text-align: left;
  background-color: gainsboro;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  border-bottom: 1px solid gainsboro;
}
</style>
