<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common_tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>修改定时任务</title>
</head>
<body>
<div id="accordion" class="accordion">
    <div class="accordion-group">
        <div class="accordion-heading">
            <div class="title">系统管理 >任务管理>修改任务</div>
        </div>
        <div id="addAccordion" class="accordion-body in">
            <div class="accordion-inner" style="border: 0px solid red;">
                <form:form action="${path}/taskController/updateJob.do" method="post" modelAttribute="job" cssClass="form-horizontal">
                    <form:hidden path="jobId"/>
                    <form:hidden path="createTime"/>
                    <div class="control-group">
                        <label class="control-label" for="jobName"><span class="help-inline input_msg_style">*</span>任务名称</label>
                        <div class="controls">
                            <form:input path="jobName"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="jobGroup"><span class="help-inline input_msg_style">*</span>任务分组</label>
                        <div class="controls">
                            <form:input path="jobGroup"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><span class="help-inline input_msg_style">*</span>任务表达式</label>
                        <div class="controls">
                            <form:input path="cronExpression"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><span class="help-inline input_msg_style">*</span>任务执行类</label>
                        <div class="controls">
                            <form:input path="beanClass"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><span class="help-inline input_msg_style">*</span>执行方法</label>
                        <div class="controls">
                            <form:input path="executeMethod"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="jobDesc">任务描述</label>
                        <div class="controls">
                            <form:textarea path="jobDesc" rows="3" cols="20"/>
                            <span style="color:red" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button class="lt_sys_commit" type="submit"
                                onmouseover="this.className='lt_sys_commit2'" onmouseout="this.className='lt_sys_commit'"> </button>
                        <button id="btn_back" class="lt_sys_back" type="button"
                                onmouseover="this.className='lt_sys_back2'" onmouseout="this.className='lt_sys_back'"> </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<w:path/>resources/js/pc.js"></script>
<script type="text/javascript">
    $('#job').validate({
        rules:{
            jobName:{
                required:true,
                maxlength:50
            },
            jobGroup:{
                required:true,
                maxlength:50
            },
            cronExpression: {
                required:true,
                maxlength:200
            },
            beanClass: {
                required:true,
                maxlength:300
            },
            executeMethod: {
                required:true,
                maxlength:100
            },
            remark:{
                maxlength:400,
            }
        },
        messages:{
            jobName:{
                required:"请输入任务名称",
                maxlength:"最长为50个字符",
            },
            jobGroup:{
                required:"请输入任务名称",
                maxlength:"最长为50个字符",
            },
            cronExpression:{
                required:"请输入执行表达式",
                maxlength:'最长为200个字符',
            },
            beanClass:{
                required:"请输入任务执行类路径",
                maxlength:'最长为300个字符',
            },
            executeMethod:{
                required:"请输入执行任务的方法",
                maxlength:'最长为100个字符',
            },
            remark:{
                maxlength:"长度不能超过400个字符",
            }
        },
        onfocusout: function(element) {
            $(element).valid();
        },
        submitHandler: function(form){
            var exp = $("#cronExpression").val();
            $.post('${path}/taskController/checkExp.do',{'expression':exp},function(data)
            {
                if(data.code==0){
                    form.submit();
                }else{
                    showSimpleMessage("输入的表达式不正确，请重新输入！");
                    $("#cronExpression").focus();
                }
            });

        },
        errorElement: 'span',
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        }
    });

    $('#btn_back').click(function(){
        window.location.href = '${path}/taskController/list.do';
    })
</script>
</body>
</html>