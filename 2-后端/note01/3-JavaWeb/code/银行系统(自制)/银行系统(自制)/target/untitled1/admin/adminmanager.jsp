<%@ page import="java.util.Objects" %>
<%@ page import="com.bank.model.Bankcard" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!-- 引入 Bootstrap -->
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入 font-awesome -->
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>宿舍管理系统</title>
    <style>
        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
    <%--定义js脚本，向后台传递当前页号和部门名称--%>
    <script>
        function formSubmit(currentPage) {
            $('input[name="currentPage"]').val(currentPage);
            $('form:first').submit();
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-10">
            <!-- 顶部搜索部分 -->
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
<%--                    点击搜索-->发起请求,并且带参数search--%>
                    <form role="form" class="form-inline" action="/netBank/accountEnquiry?method=search" method="post">
                        <div class="form-group">
                            <label for="name">字段：</label>
<%--                            传过去key里面放value--%>
                            <select name="key" class="form-control">
                                <option value="cardid">银行卡标识</option>
                                <option value="cardnumber">银行卡卡号</option>
                                <option value="status">银行卡状态</option>
                            </select>
                        </div>
                        <div class="form-group" style="margin-left: 20px">
                            <label for="value">值：</label>
                            <input type="text" class="form-control" name="value" placeholder="字段值" maxlength="12" style="width: 130px">
                        </div>
                        <div class="form-group " style="margin-left: 20px">
                            <button type="submit" class="btn btn-info ">
										<span style="margin-right: 5px"
                                              class="glyphicon glyphicon-search" aria-hidden="true">
										</span>开始搜索
                            </button>
                        </div>
                        <div class="form-group " style="margin-left: 48px">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addUserModal">
										<span style="margin-right: 5px" class="" aria-hidden="true">
											<i class="fa fa-user-plus">添加银行卡信息</i>
											</span>
                            </button>
                        </div>
                    </form>
                </div>
            </div>

<%--            &lt;%&ndash;通过查询来显示指定数据&ndash;%&gt;--%>
<%--            <form action="${path}/SearchDepartmentPageServelt">--%>
<%--                <label>部门名称</label>--%>
<%--                &lt;%&ndash;查询条件&ndash;%&gt;--%>
<%--                <input type="text" name="departmentName" value="${param.departmentName}">--%>
<%--                &lt;%&ndash;当前页数&ndash;%&gt;--%>
<%--                <input type="hidden" name="currentPage" value="1">--%>
<%--                <input type="submit" value="查找">--%>
<%--            </form>--%>

            <!-- 列表展示-->
            <div class="table-responsive">
                <table class="table table-hover ">
                    <thead>
                    <tr>
                        <th>银行卡标识</th>
                        <th>银行卡卡号</th>
                        <th>银行卡状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- Assuming you have a list of accountEnquiry objects -->
                    <%
                        List<Bankcard> list = (List<Bankcard>) request.getAttribute("list");
                    %>
                    <% for (Bankcard accountEnquiry : list) { %>
                    <tr>
                        <td><%= accountEnquiry.getCardid() %></td>
                        <td><%= accountEnquiry.getCardnumber() %></td>
<%--                        <td><%= accountEnquiry.getStatus() %></td>--%>
                        <%
                            int status = accountEnquiry.getStatus();
                            if (status == 1) {
                        %>
                        <td>未激活</td>
                        <%
                        } else {
                        %>
                        <td>激活</td>
                        <%
                            }
                        %>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default "
                                        data-cardid="<%= accountEnquiry.getCardid() %>"
                                        data-cardnumber="<%= accountEnquiry.getCardnumber() %>"
                                        data-status="<%= accountEnquiry.getStatus()%>"
                                        data-toggle="modal"
                                        data-target="#updateUserModal">
                                    <i class="fa fa-user-o">修改</i>
                                </button>
                                <button type="button" class="btn btn-danger "
                                        data-id="<%= accountEnquiry.getCardid() %>" data-toggle="modal"
                                        onclick="" data-target="#delUserModal">
                                    <i class="fa fa-user-times">删除</i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <%--数据显示--%>
                <div class="pagination-container">
                    <label>共${pageUtil.recordCount}条数据，每页显示${pageUtil.pageSize}条数据，共${pageUtil.pageCount}页，当前是第${pageUtil.currentPage}页</label>
                </div>
                <%--用来进行页面跳转--%>
                <%--第一次查询后就已经获取到pageUtil对象，然后就可以使用pageUtil对象里的属性了--%>
                <div class="pagination-container">
                    <a href="#" onclick="formSubmit(1)" id="first">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="#" onclick="formSubmit(${pageUtil.currentPage - 1})" id="prev">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="#" onclick="formSubmit(${pageUtil.currentPage + 1})" id="next">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="#" onclick="formSubmit(${pageUtil.pageCount})" id="last">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <label>跳转到 </label>
                    <%--输入框填写页数--%>
                    <input type="text" id="pageNum">
                    <%--根据输入框中的页数，进行跳转--%>
                    <input type="button" value="Go" onclick="formSubmit($('#pageNum').val())">
                </div>


                <!-- add框示例（Modal） 添加-->
                <form method="post" action="/netBank/accountEnquiry?method=save" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="addUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">添加银行卡信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">银行卡标识</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="username"
                                                       name="username" placeholder="请输入银行卡标识">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">银行卡卡号</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="password"
                                                       name="password" value="" placeholder="请输入银行卡卡号">
                                            </div>
                                        </div>

<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">银行卡状态</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="text" required class="form-control" id="name"--%>
<%--                                                       name="name" value="" placeholder="默认未激活">--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">性别</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="radio" checked value="男" class="gender"--%>
<%--                                                       name="gender"> 男--%>
<%--                                                &nbsp;&nbsp;&nbsp;<input type="radio" value="女" class="gender"--%>
<%--                                                                         name="gender"> 女--%>
<%--                                            </div>--%>
<%--                                        </div>--%>

<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">联系电话</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="text" required class="form-control" id="telephone"--%>
<%--                                                       name="telephone" value="" placeholder="请输入联系电话">--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- update框示例（Modal）修改 -->
                <form method="post" action="/netBank/accountEnquiry?method=update" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel">银行卡信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">银行卡标识</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="text" readonly required class="form-control" id="id"--%>
<%--                                                       name="id">--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">银行卡标识</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="username"
                                                       name="username" placeholder="请输入银行卡标识">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">银行卡卡号</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="password"
                                                       name="password" value="" placeholder="请输入银行卡卡号">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="user_id" class="col-sm-3 control-label">银行卡状态</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="name"
                                                       name="name" value="" placeholder="请输入银行卡状态">
                                            </div>
                                        </div>

<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">性别</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="radio" checked value="男" class="gender"--%>
<%--                                                       name="gender"> 男--%>
<%--                                                &nbsp;&nbsp;&nbsp;<input type="radio" value="女" class="gender"--%>
<%--                                                                         name="gender"> 女--%>
<%--                                            </div>--%>
<%--                                        </div>--%>

<%--                                        <div class="form-group">--%>
<%--                                            <label for="user_id" class="col-sm-3 control-label">联系电话</label>--%>
<%--                                            <div class="col-sm-9">--%>
<%--                                                <input type="text" required class="form-control" id="telephone"--%>
<%--                                                       name="telephone" value="" placeholder="">--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- 删除模态框示例（Modal） -->
                <form method="post" action="/netBank/accountEnquiry?method=delete"
                      class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="delUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel">银行卡信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <h3 class="col-sm-18 control-label" id="deleteLabel">删除信息</h3>
                                                <input type="hidden" class="form-control" id="tab"
                                                       name="tab" placeholder="" value="dor_admin"> <input
                                                    type="hidden" class="form-control" id="id"
                                                    name="id" placeholder="">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">删除</button>
                                    <span id="tip"> </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $('#updateUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var username = button.data('username')
        var password = button.data('password')
        var name = button.data('name')
        var gender = button.data('gender')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('修改银行卡信息')
        modal.find('#id').val(id)
        modal.find('#username').val(username)
        modal.find('#password').val(password)
        modal.find('#name').val(name)
        var list = modal.find('.gender')
        for(var i=0;i<list.length;i++){
            if(gender == $(list.get(i)).val()){
                $(list.get(i)).prop('checked',true)
            }
        }
        modal.find('#telephone').val(telephone)
    })


    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var modal = $(this)   //$(this)表示当前对象,这里指的是delUserModal
        modal.find('.modal-title').text('删除 银行卡信息')
        modal.find('#deleteLabel').text('是否删除银行卡标识为  ' + id)
        modal.find('#id').val(id)
    })
</script>

</body>

</html>