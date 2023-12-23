<%--
  Created by IntelliJ IDEA.
  User: debug16
  Date: 2021/12/16
  Time: 上午 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid">

    <nav class="navbar navbar-expand-sm bg-light">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">个人首页</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="balance">账号余额查询</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="trade_list">交易记录查询</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="transfer.jsp">单笔转账</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="access.jsp">存取款业务</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0);" data-bs-toggle="modal" data-bs-target="#logoutModal">退出系统</a>
                <div class="modal fade" id="logoutModal">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <!-- 模态框头部 -->
                            <div class="modal-header">
                                <h5 class="modal-title">退出系统</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <!-- 模态框内容 -->
                            <div class="modal-body">
                                <strong>你确定退出系统吗？</strong>
                            </div>
                            <!-- 模态框底部 -->
                            <div class="modal-footer">
                                <a href="logout" class="btn btn-danger">确定</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

        </ul>
    </nav>
</div>

