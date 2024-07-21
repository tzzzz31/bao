<#include "/templates/import/top.ftl">
<div class="container">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!-- 导航头部 -->
            <div class="navbar-header">
                <!-- 移动设备上的导航切换按钮 -->
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
                    <span class="sr-only">切换导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- 品牌名称或logo -->
                <a class="navbar-brand" href="/">首页</a>
            </div>
            <!-- 导航项目 -->
            <div class="collapse navbar-collapse navbar-collapse-example">
                <ul class="nav navbar-nav">
                    <!-- 一般导航项目 -->
                    <li><a href="/bao123/"><i class="icon icon-list-ol"></i>基础数据</a></li>
                    <!-- 导航中的下拉菜单 -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon icon-group"></i>用户管理 <b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/bao123/user/list">用户列表</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown"><i class="icon icon-th-list"></i>文章管理 <b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/bao123/article/type/list">文章类型</a></li>
                            <li><a href="/bao123/article/tag/list">文章标签</a></li>
                            <li><a href="/bao123/article/list">文章管理</a></li>
                        </ul>
                    </li>



                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="your/nice/url"><i class="icon icon-signout"></i>退出登陆</a></li>
                </ul>

            </div><!-- END .navbar-collapse -->
        </div>
    </nav>