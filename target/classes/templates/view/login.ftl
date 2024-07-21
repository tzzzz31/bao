<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="//cdn.bootcss.com/zui/1.10.0/css/zui.min.css">
    <script src="//cdn.bootcss.com/zui/1.10.0/lib/jquery/jquery.js"></script>
    <script src="//cdn.bootcss.com/zui/1.10.0/js/zui.min.js"></script>
    <title>admin</title>
</head>
<body>
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">用户登录</div>
        <div class="panel-body center-form">
            <form class="form-horizontal" action="/userLogin" method="post" id="loginUserForm">
                    <div class="form-group">
                        <label for="userName" class="col-sm-2">用户名：</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="userName" maxlength="50" name="userName" placeholder="用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPassword" class="col-sm-2">密码：</label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="userPassword" maxlength="50" name="userPassword" placeholder="密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="verifyCode" class="col-sm-2">验证码:</label>
                        <div class="col-sm-10">
                            <div class="col-sm-10 color-xs-9">
                                <input type="text" class="form-control" id="verifyCode" maxlength="4" name="verifyCode" placeholder="验证码">
                            </div>
                            <div class="col-sm-2 col-xs-3">

                                <img class="img-thumbnail" src="/getCaptcha" id="check_code_img" onclick="javascript:this.src='/getCaptcha?'+new Date().getTime();" title="点击刷新验证码"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div style="text-align: center">
                            <button type="button" onclick="submitForm()" class="btn btn-default" style="text-align: center">
                                <i class="icon icon-signin"></i>登录
                            </button>
                        </div>
                    </div>
            </form>
            <div style="text-align: right">
                <button type="button" onclick="window.location.href='/register'" class="btn btn-default" style="text-align: right">
                    <i class="icon icon-signin"></i>注册
                </button>
            </div>
        </div>
    </div>
</div>

    <style>
        .center-form {
            width: 600px;
            height: 200px;
            margin: 0 auto;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            border-radius: 10px;
        }
    </style>

<script>


    function submitForm(){
        var userName=$("#userName").val();
        var userPassword=$("#userPassword").val();
        var verifyCode=$("#verifyCode").val();

        if(!checkNotNull(userName||userPassword)){
            zuiMsg("用户名或密码不能为空");
            return;
        }

        if(!checkNotNull(verifyCode)||verifyCode.length!=4){
            zuiMsg("验证码错误");
            return;
        }

        $.post("/userLogin",{
                userName:userName,
                userPassword:userPassword,
                verifyCode:verifyCode
            },
            function (data){
                if(data.code==200){
                    location.href="${referer!"/"}";
                    return
                }
                zuiMsg(data.message);
                $("#check_code_img").click();
            });
    }

    function checkNotNull(str){
        if(str==null||str===""||str.length<1||str==undefined){
            return false;
        }
        return true;
    }

    function zuiMsg(msg){
        new $.zui.Messager(msg, {
            type: 'success' // 定义颜色主题
        }).show();
        return;
    }


</script>

<#include "../import/bottom.ftl">