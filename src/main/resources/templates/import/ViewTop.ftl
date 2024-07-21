<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="//cdn.bootcss.com/zui/1.10.0/css/zui.min.css">
    <script src="//cdn.bootcss.com/zui/1.10.0/lib/jquery/jquery.js"></script>
    <script src="//cdn.bootcss.com/zui/1.10.0/js/zui.min.js"></script>
    <title>bookForum</title>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid " >
            <!-- 导航头部 -->
            <div class="navbar-header">

            </div>
            <!-- 导航项目 -->
            <div class="collapse navbar-collapse navbar-collapse-example">
                <ul class="nav navbar-nav" >
                    <li><a href="/">首页</a></li>
                </ul>

                <!-- 右侧的导航项目 -->
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown">个人中心<b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/user/">个人详情</a></li>
                            <li><a href="/user/myArticleList">我的文章</a></li>
                        </ul>
                    </li>
                    <li><a href="/user/publishArticle">发布帖子</a></li>

                    <#if (Session.user??)>
                        <li><a href="/logout">退出登录</a></li>
                    <#else >
                        <li><a href="/login">登录</a></li>
                    </#if>

                </ul>
            </div><!-- END .navbar-collapse -->
        </div>
    </nav>
</div>
<script>


    function zuiMsg(msg){
        new $.zui.Messager(msg, {
            type: 'success' // 定义颜色主题
        }).show();
        return;
    }

    function checkNotNull(str){
        if(str==null||str===""||str.length<1||str==undefined){
            return false;
        }
        return true;
    }

</script>