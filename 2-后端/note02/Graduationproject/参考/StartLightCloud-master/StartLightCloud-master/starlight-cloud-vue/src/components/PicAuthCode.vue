<template>
  <div style="margin:10px">
      <canvas 
       ref="canvas"
       :width="contentWidth" 
       :height="contentHeight"
       />
       <span :style="this.textStyle" @click="refresh()">换一张</span>
  </div>
</template>
<script>
  // 作用：防止暴力登录破解和批量注册
  export default {
      name: 'SIdentify',
      props: {
         setCode:{
              type:Function,
              default:()=>''   //更新验证码的方法,返回验证码即可
          }
      },
      data(){
          return {
              identifyCode:'',
              contentWidth: 100,
              contentHeight: 70,
              backgroundColorMin: 180,
              backgroundColorMax:240,
              fontSizeMin: 40,
              fontSizeMax: 60,
              colorMin: 50,
              colorMax: 160,
              lineColorMin:40,
              lineColorMax: 300,
              dotColorMin: 0,
              dotColorMax: 255,
              textStyle:{fontSize:'12px',color:'gray',marginLeft:'6px',cursor:'pointer',padding:'5px',userSelect:"none"}
           }
      },
      methods: {
          drawPic() {
              let canvas = this.$refs.canvas
              let ctx = canvas.getContext('2d')
              ctx.fillStyle = this.randomColor(this.backgroundColorMin, this.backgroundColorMax) 
              ctx.strokeStyle = this.randomColor(this.backgroundColorMin, this.backgroundColorMax) 
              ctx.fillRect(0, 0, this.contentWidth, this.contentHeight) 
              ctx.strokeRect(0,0,this.contentWidth, this.contentHeight) 
              for (let i = 0; i < this.identifyCode.length; i++) {
                  this.drawText(ctx, this.identifyCode[i], i)
              }
              this.drawLine(ctx)
              this.drawDot(ctx)
          },
          randomNum(min, max) {
              return Math.floor(Math.random() * (max - min) + min)
          },
          randomColor(min, max) {
              let r = this.randomNum(min, max)
              let g = this.randomNum(min, max)
              let b = this.randomNum(min, max)
              return 'rgb(' + r + ',' + g + ',' + b + ')'
          },
          drawText(ctx, txt, i) {
              ctx.fillStyle = this.randomColor(this.colorMin, this.colorMax)
              ctx.font = this.randomNum(this.fontSizeMin, this.fontSizeMax) + 'px SimHei' //字体大小
              ctx.textBaseline = 'alphabetic' 
              let x = (i + 1) * (this.contentWidth / (this.identifyCode.length + 1))
              let y = this.randomNum(this.fontSizeMax, this.contentHeight - 12)
              let deg = this.randomNum(-45, 45)
              ctx.translate(x, y)
              ctx.rotate(deg * Math.PI / 180) 
              ctx.fillText(txt, 0, 0)
              ctx.rotate(-deg * Math.PI / 180)
              ctx.translate(-x, -y)
          },
          drawLine(ctx) {
              for (let i = 0; i < 8; i++) {
                  ctx.strokeStyle = this.randomColor(this.lineColorMin, this.lineColorMax)
                  ctx.beginPath() 
                  ctx.moveTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight)) //设置起点x,y
                  ctx.lineTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight)) //绘制直线 x,y 一条当前位置到x,y点的直线
                  ctx.stroke() 
                 }
          },
          drawDot(ctx) {
              for (let i = 0; i < 100; i++) {
                  ctx.fillStyle = this.randomColor(0, 255)
                  ctx.beginPath()
                  ctx.arc(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight), 1, 0, 2 * Math.PI)
                  ctx.fill()
              }
          },
          refresh(){
              const words='AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz'
              let code=''
              for(let i=0;i<4;i++){ 
              code+=words[Math.floor( Math.random()*52)]
              }
              this.identifyCode=this.setCode()?this.setCode():code
          }
         
      },
      mounted() {
          this.refresh()
      },
      watch:{
          identifyCode(){
              this.drawPic()
          }
      }
  }

</script>
