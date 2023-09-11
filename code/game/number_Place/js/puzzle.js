var type = 2;   // n * n 的正方形
var numbers = [];
var result = 0;

// $(function () {
//     $("#createTable").ready(function () {
//         random();  // 设置一个随机打乱数组
//         // 原生:
//         // const myTable = document.createElement("table");
//         // myTable.id = "myTable";
//         // myTable.className = "puzzle";
//         // document.body.appendChild(myTable);
//         // jqury:
//         // //创建表格
//         var $table = $("<table id='Table' class='puzzle'></table>");
//         // 创建几行几列的表格
//         var id_index = 0;
//         for (let i = 0; i < type; i++) {
//             var $tr = $("<tr></tr>");
//             for (j = 0; j < type; j++) {
//                 var $cel = $("<td class='cell' id></td>");
//                 // 每一个元素都有自己的id 动态设置属性值
//                 $cel.attr("id", id_index);
//                 //列 创建到 行
//                 $tr.append($cel);
//                 // 绑定事件
//                 $('body').on('click',function () {
//                     // body 是嵌套在 stble 中的，
//                     clickHandler()
//                 });
//                 id_index++;
//             }
//             $table.append($tr);
//         }
//         $("body").append($table);
//
//
//         // 处理每一个格子
//         for (let i = 0; i < type ** 2; i++) {
//             // var cell = document.getElementById(i);
//
//             if (numbers[i] === type ** 2) {// 最后的数子设置一个空格子
//                 $("#" + i).attr("class", "emptyCell");
//                 //cell.className = "emptyCell";  // className 属性设置或返回元素的 class 属性。
//             } else {
//                 //innerHTML 和innerText 对应jqury操作是获取值,设置值
//                 // 把随机数字放进格子
//                 // cell.innerHTML = numbers[i];
//                 // cell.className = "cell";
//                 $("#" + i).text(numbers[i]);
//             }
//         }
//
//     })
// });

function typeValue(){
    type = $("input").val();
}


$("#createTable").ready(function () {
    random();
    // typeValue()
    const myTable = document.createElement("table");
    myTable.id = "myTable";
    myTable.className = "puzzle";
    document.body.appendChild(myTable);

    var counter = 0;
    for (let i = 0; i < type; i++) {
        const row = document.createElement("tr");
        for (j = 0; j < type; j++) {
            const cell = document.createElement("td");
            cell.id = counter;
            cell.addEventListener("click", clickHandler);
            row.appendChild(cell);
            counter++;
        }
        myTable.appendChild(row);
    }

    for (let i = 0; i < type ** 2; i++) {
        var cell = document.getElementById(i);
        if (numbers[i] === type ** 2) {
            cell.innerHTML = "";
            cell.className = "emptyCell";
        } else {
            cell.innerHTML = numbers[i];
            cell.className = "cell";
        }
    }
});

function random() {//打乱数组 [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]的数组，要保证随机选到的不是同一个数
    // 产生多个不重复的随机数
    //初始化
    for (let i = 0; i < type ** 2; i++) {
        numbers[i] = i + 1;  // 数据从1开始
    }
    var cnt = numbers.length, index;
    // 遍历 所有元素
    while (cnt--) {
        // 选择一个随机index
        index = Math.floor(Math.random() * cnt);   // 生成随机整数 [0, cur)
        // 交换值
        // numbers[cnt] ^= numbers[index];
        // numbers[index] ^= numbers[cnt];
        // numbers[cnt] ^= numbers[index];

        temp = numbers[cnt];
        numbers[cnt] = numbers[index];
        numbers[index] = temp;
    }
}

function checkAdjacentCell(id) { //检测出target是否有相邻的空格子
    var reslut = -1;
    // 得到当前格子 前后左右的的格子id // 设置成常量
    const topCellId = parseInt(id) - type,  // 前
        bottomCellId = parseInt(id) + type, // 后
        leftCellId = parseInt(id) - 1,     // 左
        rightCellId = parseInt(id) + 1;     // 右
    adjacentId = [topCellId, bottomCellId, leftCellId, rightCellId];

    //处理 前后左右的的格子id 可能是n* n以为 的情况 (如第一行最右边的一个，它的rightCellI和topCellId是不成立的)
    if (adjacentId[3] % type === 0) {  // 右
        adjacentId[3] = -1;
    } else if (adjacentId[2] % type === type - 1) {  //左
        adjacentId[2] = -1;
    }

    for (let i = 0; i < adjacentId.length; i++) {
        if (adjacentId[i] < type ** 2  && adjacentId[i] >= 0) {   // 可以处理 前 后
            if ($("#" + adjacentId[i]).attr("class") === "emptyCell"){
                reslut = adjacentId[i];
            }
        }
    }
    return reslut;
}

function clickHandler() { //实现换格子的功能
    var emptyID = checkAdjacentCell(this.id);  // 调用函数
    // $(this)通常是一个JQuery对象 ，可以调用jquery的方法和属性值
    // var target = $(this).arrt("id");
    var target = document.getElementById(this.id);
    // console.log(emptyID);

    // 如果相邻格子有空格子说明可以移动格子
    if (emptyID >= 0) {
        target.animate({ backgroundColor: "lightblue" }, 200);   // 动画变颜色

        var text = target.textContent;
        var empty = document.getElementById(emptyID);
        // $("#" = emptyID)
        target.className = "emptyCell";
        target.innerHTML = "";
        empty.className = "cell";
        empty.innerHTML = text;
    } else {
        target.animate({ backgroundColor: "red" }, 400);
    }
    checkWin();
}


function sleep(delay) {//这个sleep函数类似于java等的sleep函数，单位ms
        var start = new Date().getTime();  // 时间戳
        while (new Date().getTime() - start < delay) {
            continue;
        }
}

function checkWin(){ // 主要逻辑就是测试每个格子显示的数字是否等于它的id+1
    flag=true;
    for(let i = 0; i < type ** 2 - 1; i++){
        if ($("#" + i).text() == i + 1){  // 注意有个格子是空的
            console.log($("#" + i).text());
            continue;
        }else{
            flag = false;
        }
    }

    sleep(60);
    if(flag){
        var mesg = confirm("Congratulations！You win!! Do you want to play again?");
        if(mesg){
            location.reload()
        }
    }
}



