var type = 9;

$(function() {
        var $table = $("<table id='Table' class='puzzle'></table>");
// 创建几行几列的表格
        var id_index = 0;
        for (let n = 0; n < 3; n++){

            for (let m = 0; m < 3; m++){

            }
        }
        for (let i = 0; i < type; i++) {
            var $tr = $("<tr></tr>");
            for (j = 0; j < type; j++) {
                var $cel = $("<td class='cell' id></td>");
                // 每一个元素都有自己的id 动态设置属性值
                $cel.attr("id", id_index);
                //列 创建到 行
                $tr.append($cel);
                id_index++;
            }
            $table.append($tr);
        }
        $("#GameInterface").append($table);
})