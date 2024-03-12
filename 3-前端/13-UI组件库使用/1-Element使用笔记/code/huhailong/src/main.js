import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import './styles.scss'
import 'font-awesome/css/font-awesome.min.css'
import router from './router'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import common from '@/components/Common.vue'
import VueAnimateNumber from 'vue-animate-number'

Vue.use(ElementUI, { locale })
Vue.use(mavonEditor)
Vue.use(VueAnimateNumber)

Vue.config.productionTip = false
Vue.prototype.common = common;
//改变标题方法
function changeTitle(el,binding){
  const {value} = binding;
    if(el.dataset.title){
      document.title = el.dataset.title;
    }else if(value&&value.title){
      document.title = value.title;
    }
}
Vue.directive('blog-title',{
  inserted: function(el,binding){
    changeTitle(el,binding);
  },
  update: function(el,binding,vnode,oldNode){
    changeTitle(el,binding);
  }
})
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
