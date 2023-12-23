"use strict";
var ref;
var time_count = 60;
var flag = 1;

$(function(){
    $("#b1").click(function () {//改变在p中的内容，就是显示游戏规则
        var texts = "这是一个配对游戏，一次翻开一个，如果两个翻开的颜色相同意味成功，就加10分，颜色也有可能改变有时间限制";
        if (flag === 1){
            $("#p1").text(texts);
            flag = 0;
        }else{
            $("#p1").text(" ");
            flag = 1;
        }
    })
})

$(function(){
    $("#b2").click(function () {//开始游戏,其实也是一个初始化的函数
        window.clearInterval(ref);
        time_count = 60;
        // clearTimeout(tk);//防止多次连续的点击开始游戏按钮之后，发生意外 取消settimeout方法设置的定时器，
        $("#b2").attr("value","on");//on代表是点击了开始按钮之后的。
        $("#b2").text("重新开始");//变成重新开始的按钮。
        $("#times").text("time:60");//这个是为了在重新开始游戏之后的设置，
        $("#scores").text("scores:0");//初始化分数。
        //初始化，设置成为没有背景。
        for (let i = 3; i < 13; i++) {
            $("#d"+i).css("background-color", "");
        }

        //用来进行记录时间的
        var ref = setInterval(function () {
            if (time_count >= 0) //只有当time>0 // 更新值
            {
                $("#times").text("time:" + time_count--);

            } else {
                $("#b2").attr("value", "off"); //时间结束了
                $("#b2").text("重新开始");//将这个变为重新开始的按钮
                window.clearInterval(ref);
            }
        }, 1000);
    })
})




function change_color(obj) {//在这里的obj是一个参数表示当前点击的对象
    var colors = ["red", "green", "yellow", "blue"];
    if (obj.style.backgroundColor != "")//这里就是设置防止再已经点击变色之后再次点击再次变色
    {
        return;
    }
    if ($("#b2").attr("value") === "on")
    {
        obj.style.backgroundColor = colors[Math.floor(Math.random()*4)];  // 生成随机整数 返回值是一个大于等于0，且小于N的随机数
    // sleep(500);
    }
}

// delay相当于形参
function sleep(delay) {//这个sleep函数类似于java等的sleep函数，单位ms
    var start = new Date().getTime();  // 时间戳
    while (new Date().getTime() - start < delay) {
        continue;
    }
}

function match_color2() {
    for (var i = 3; i < 13; i++) {
        //要找到一个有颜色的
        while ((document.getElementById("d"+i).style.backgroundColor == ""))//要找到一个有颜色的
        {
            i++;
        }
        if (i>12)
        {
            return;
        }
        var color =  $("#d" + i).css("background-color");
        for (var index = 3; index < 13; index++) {
            if (i != index && color == $("#d" + index).css("background-color"))
            {
                //找到了相同的两个的了，就要进行加分和重新变回颜色
                //在这里使用slice进行切片，因为输出的格式固定，所以分数一定在第四个在代码里面就是要从第三位开始。
                var scores = parseInt(document.getElementById("scores").innerHTML.slice(7)) + 10;
                $("#scores").text("scores:" + scores);
                //在找到之后，得分记录之后，就要重新将那填写两个的颜色
                sleep(100);//利用自己定义的sleep函数暂定100ms，让用户看清于反应后再消失，但是这样的话，真正的时间一定超过60s了
                $("#d" + i).css("background-color","")
                $("#d" + index).css("background-color","")
                break;
            }

        }
    }
}





