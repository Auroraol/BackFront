import * as PIXI from 'pixi.js'
import {
  Live2DModel,
  MotionPreloadStrategy,
  InternalModel,
} from 'pixi-live2d-display';

// 挂载pixi
window.PIXI = PIXI;
export async function init() {
  // const model = await Live2DModel.from('https://cdn.jsdelivr.net/gh/guansss/pixi-live2d-display/test/assets/shizuku/shizuku.model.json', { motionPreload: MotionPreloadStrategy.NONE, })
  // 引入模型
  const model = await Live2DModel.from('../../src/assets/model2/HK416_805/normal.model3.json', { motionPreload: MotionPreloadStrategy.NONE,  })

  // 创建模型对象
  const app = new PIXI.Application({
    // 配置模型舞台
    view: document.getElementById('canvas_view'),
    // 背景是否透明
    transparent: true,
    autoDensity:true,
    autoResize: true,
    antialias: true,
    // 高度
    height: '1080',
    // 宽度
    width:'1900'
  })

  // 鼠标跟踪方法
  model.trackedPointers = [{ id: 1, type: 'pointerdown', flags: true }, { id: 2, type: 'mousemove', flags: true }]
  // 添加模型到舞台
  app.stage.addChild(model)
  // 模型的缩放
  model.scale.set(0.1)
  // 模型的位置,x,y相较于窗口左上角
  model.x = 0
  // 添加模型状态管理器
  const a = new InternalModel(model)
  model.InternalModel = a


  // 绑定模型点击事件动作
  model.on('pointerdown', (hitAreas) => {
    // hitAreas:模型的一些上下文
    const { x, y } = hitAreas.data.global 
    const point = model.hitTest(x, y)
      model.motion("TapHead");
  });

  // model.idleMotionPriority = "TapHead"


  // 绑定模型拖拽方法
  draggable(model);
  //添加模型范围遮罩
  addFrame(model);
  /**
   * 模型区域范围遮罩
   * @param {Live2DModel} model -模型对象
   */
  function addFrame(model) {
    const foreground = PIXI.Sprite.from(PIXI.Texture.WHITE);
    foreground.width = model.internalModel.width;
    foreground.height = model.internalModel.height;
    foreground.alpha = 0.2;

    model.addChild(foreground);
    foreground.visible = true
  }
  /**
   * 模型拖拽方法
   * @param {Live2DModel} model -模型对象
   */
  function draggable(model) {
    model.buttonMode = true;
    model.on("pointerdown", (e) => {
      model.dragging = true;
      model._pointerX = e.data.global.x - model.x;
      model._pointerY = e.data.global.y - model.y;
    });
    model.on("pointermove", (e) => {
      if (model.dragging) {
        model.position.x = e.data.global.x - model._pointerX;
        model.position.y = e.data.global.y - model._pointerY;
      }
    });
    model.on("pointerupoutside", () => (model.dragging = false));
    model.on("pointerup", () => (model.dragging = false));
  }

  // 查看模型触发区域
  var hitAreas = model.internalModel.hitAreas;
  // console.log(hitAreas)

  console.log(model)
}
