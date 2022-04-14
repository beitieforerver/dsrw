<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ include file="../common/common_tags.jsp" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>任务管理>任务列表</title>
    <script src="/js/jquery-3.1.1.js"></script>
</head>
<body>
<script>
    function operation(actionName,jobId){
        $.ajax({
            url:"/taskController/" + actionName + ".action?id="+jobId,
            type:"get",
            dataType:"json",
            async: false,
            success: function(data){
                console.log(data)
                alert(data.msg);
            },
            error: function (e){
                alert("error");
            }
        })
    }
</script>
<style>
    .title {
        margin: 15px;
        font-size: 20px;
        font-weight: 700;
    }
    .table-condensed  {
        border:1px solid black;
        line-height: 30px;
        padding: 10px;
        border-spacing: 10px 5px;
    }

</style>
<div id="accordion" class="accordion">
    <div class="accordion-group">
        <div class="accordion-heading">
            <div class="title">任务管理>任务列表</div>
        </div>
        <div id="addAccordion" class="accordion-body in">
            <div class="accordion-inner">
                <table class="table table-hover table-condensed" style="">
                    <thead>
                    <tr>
                        <th>任务名称</th>
                        <th>任务分组</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                        <th>任务表达式</th>
                        <th>执行类</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pb}" var="job" varStatus="status">
                        <tr>
                            <td>${job.jobName}</td>
                            <td>${job.jobGroup}</td>
                            <td>${job.jobStatusName}</td>
                            <td>${job.createTime}</td>
                            <td>${job.updateTime}</td>
                            <td>${job.cronExpression}</td>
                            <td>${job.beanClass}</td>
                            <td>
                                <a onclick="operation('addOrUpdate',${job.jobId})" href="javascript:void(0);">新增/更新</a>
                                <a onclick="operation('pauseJob',${job.jobId})" href="javascript:void(0);">暂停</a>
                                <a onclick="operation('deleteJob',${job.jobId})" href="javascript:void(0);">注销</a>
                                <a onclick="operation('executeJob',${job.jobId})" href="javascript:void(0);">立即执行</a>
                                <a onclick="operation('resumeJob',${job.jobId})" href="javascript:void(0);">重新运行</a>
<%--                                <lt:img menuName="任务列表" moduleName="运营管理" privilegeName="执行定时任务" clickName="立即执行"--%>
<%--                                        clickMethod="executeJob('${job.jobName}','${job.jobGroup}','${job.jobId}');"></lt:img>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="10" form-id="job" class="paginationPanel"><ltPage:page pageBean="${pb}" /></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">



</script>
</body>
</html>