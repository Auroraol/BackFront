var home = $('#home');
var snakeArr = [];
var direcation = 'l';
var speed = 0;
var timer = '';
//460/20
var rows = 23;
var cols = 25;
var die = false; //用于判断是否game over
var food = [];
var sorce = 0; //得分的显示

for( var i=0; i<4; i++ ){
    //var snakeDiv = document.createElement("div");
    //snakeDiv.style = 'background:url(./images/snake' + i + '.png) no-repeat;';
    var snakeDiv = $('<div style="background:url(../images/sna/snake' + i + '.png) no-repeat;z-index:999"></div>');
    home.append(snakeDiv);
    snakeArr[i] = {r : 10, c : 10 + i, div : snakeDiv, d : direcation};
    setPosition(snakeArr[i]);
}

function move(){
    var timer = setInterval(function(){
        for( var i=snakeArr.length -1; i>0; i-- ){
            snakeArr[i].c = snakeArr[i-1].c;
            snakeArr[i].r = snakeArr[i-1].r;
            snakeArr[i].d = snakeArr[i-1].d;
        }

        switch(direcation){
            case 'l' :
                snakeArr[0].c--;
                snakeArr[0].d = 'l';
                break;

            case 'r' :
                snakeArr[0].c++;
                snakeArr[0].d = 'r';
                break;

            case 't' :
                snakeArr[0].r--;
                snakeArr[0].d = 't';
                break;

            case 'b' :
                snakeArr[0].r++;
                snakeArr[0].d = 'b';
                break;
        }

        //snake的方向控制
        $(window).keydown(function(event){
            switch(event.keyCode){
                case 37 :
                    direcation = 'l';
                    break;

                case 38 :
                    direcation = 't';
                    break;

                case 39 :
                    direcation = 'r';
                    break;

                case 40 :
                    direcation = 'b';
                    break;
            }
        });

        //snake事故
        //1. snake撞墙
        if( snakeArr[0].c < 0 || snakeArr[0].r < 0 || snakeArr[0].c >= cols || snakeArr[0].r >= rows ){
            clearInterval(timer);
            die = true;
            alert('GAME OVER');
        }

        //2. snake撞到自己
        for( var i=1; i<snakeArr.length; i++ ){
            if( snakeArr[0].c == snakeArr[i].c && snakeArr[0].r == snakeArr[i].r ){
                clearInterval(timer);
                die = true;
                alert('GAME OVER');
            }
        }

        //snake吃水果
        if( snakeArr[0].c == food[0].c && snakeArr[0].r == food[0].r ){
            food[0].div.css({background : 'url(./images/snake2.png) no-repeat'});
            snakeArr.splice(snakeArr.length - 1, 0, food[0]);
            food.shift();
            sorce += 10;
            $('#score').html(sorce);
        }

        //snake产生水果
        if( food.length == 0 ){
            createFood();
        }


        for(var i = 0; i < snakeArr.length; i++){
            setPosition(snakeArr[i]);
        }
    },speed);

}


function createFood(){
    var r = parseInt(rows * Math.random());
    var c = parseInt(cols * Math.random());
    var casrsh = false;

    //2个水果出现的位置不能一样
    while( food.length == 0 ){
        //判断snake的位置，不能与snake相撞
        for( var i = 0; i < snakeArr.length; i++ ){
            if( r == snakeArr[i].r && c == snakeArr[i].c ){
                casrsh = true;
            }
        }
        //当位置不重叠的时候，产生水果
        if( !casrsh ){
            var i = parseInt(4 * Math.random());
            var foodDiv = $('<div style="background:url(./images/fruit'+ i +'.png);"></div>');
            home.append(foodDiv);
            food.push({r : r, c : c, div : foodDiv});
            setPosition(food[0]);
        }
    }

}


function setPosition(obj){
    obj.div.css({left : obj.c * 20, top : obj.r * 20});
    obj.div.removeClass().addClass(obj.d);
}
createFood(); //那页面一被加载出来就显示出食物！