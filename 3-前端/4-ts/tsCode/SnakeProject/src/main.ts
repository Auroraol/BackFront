import '../public/style/index.less';
import $ from 'jquery'; // 引入 jQuery 库


// 定义食物类 Food
class Food{
    //定义一个属性表示食物所对应的元素
    element: JQuery<HTMLElement>

    constructor(){
        //获取页面中的 food 元素并将其赋值给 element
        this.element = $('#food')
    }

    get x(){
        return this.element.position()?.left
    }

    get y(){
        return this.element.position()?.top
    }

    change(){
        // 生成一个阔机的位置
        // 食物的位置最小足0 最大是298  //304-4-10
        // 蛇移动一次就是一格，一格的大小就是10，所以就要求食物的
        // this.element.scrollLeft(290)
           // Generate a random horizontal position between 0 and 290
           const newX = Math.round(Math.random() * 29) * 10;
           const newy = Math.round(Math.random() * 29) * 10;
        
           // Set the new horizontal position using the css method
           this.element.css('left', `${newX}px`);
           this.element.css('top', `${newy}px`);
    }
}


// 测试
const food = new Food()
console.log(food.x);
console.log(food.y);
food.change()
console.log(food.x);
console.log(food.y);