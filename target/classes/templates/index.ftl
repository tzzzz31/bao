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
                    <li class="dropdown">
                        <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown">帖子类型<b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu">
                            <#if articleTypeArrayList?? && articleTypeArrayList?size gt 0>
                                <#list articleTypeArrayList as articleType>
                                    <li><a href="/?articleTypeId=${(articleType.articleTypeId)!}">${(articleType.articleTypeName)!}</a></li>
                                </#list >
                            </#if>
                        </ul>
                    </li>
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

<div class="panel col-sm-9" style="margin-left: 195px">
    <div class="panel-body">
        <form class="form-horizontal" action="/?articleTitle=" method="get">
            <div class="form-group">
                <label for="articleTitle" class="col-sm-5">请输入您要搜索的帖子：</label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="articleTitle" placeholder="帖子名称">
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-success">查询</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="panel  col-sm-9" style="margin-left: 195px">
    <div>
        <i class="icon icon-book">帖子广场</i>
    </div>
    <div class="panel-body">
        <#if articleVoIPage?? && articleVoIPage.list?size gt 0>
            <#list articleVoIPage.list as articleVo>
                <div>
                    <a class="card" href="/article?articleId=${(articleVo.articleId)!}">
                        <div class="card-heading"><strong></strong>${(articleVo.articleTitle)!}</div>
                        <div class="card-context text-muted">${(articleVo.articleAddTime)?string("yyyy-MM-dd HH:mm:ss")}</div>
                        <div class="card-actions">
                            <span class="label label-warning">${(articleVo.articleTypeName)!}</span>
                            <div class="pull-right"><i class="icon-eye-open"></i>${(articleVo.articleLookNumber)!}</div>
                        </div>
                    </a>
                </div>
            </#list>


            <div class="col-xs-12 col-sm-9" style="margin-left: 195px">
                <div class="panel" style="text-align: center">
                    <div class="panel-body">
                        <div class="col-sm-12">
                            <ul class="pager">
                                <li class="previous" onclick="getNewData(1)">
                                    <a href="javascript:void (0)"><i class="icon icon-step-backward"></i></a>
                                </li>

                                <#if articleVoIPage.pageNumber lte 1>
                                    <li class="previous disabled">
                                        <a href="javascript:void (0)"><i class="icon icon-chevron-left"></i></a>
                                    </li>
                                <#else>
                                    <li class="previous">
                                        <a href="javascript:void (0)"><i class="icon icon-chevron-left"></i></a>
                                    </li>
                                </#if>
                                <li >
                                    <a href="javascript:void (0)" class="btn">${articleVoIPage.pageNumber}页/共${articleVoIPage.totalPage}页</a>
                                </li>
                                <#if articleVoIPage.pageNumber gte articleVoIPage.totalPage>
                                    <li class="next disabled">
                                        <a href="javascript:void(0)"><i class="icon icon-chevron-right"></i></a>
                                    </li>
                                <#else>
                                    <li class="next" onclick="getNewData('${articleVoIPage.pageNumber+1}')">
                                        <a href="javascript:void(0)"><i class="icon icon-chevron-right"></i></a>
                                    </li>
                                </#if>
                                <li class="previous" onclick="getNewData('${articleVoIPage.totalPage}')">
                                    <a href="javascript:void (0)"><i class="icon icon-step-forward"></i></a>
                                </li>

                                <li class="next">
                                    <a href="javascript:void(0)">
                                        <input type="number" id="renderPageNumber" maxlength="5" oninput="value=value.replace(/[^\d]/g,'')">
                                    </a>
                                </li>
                                <li class="next">
                                    <a href="javascript:void (0)" onclick="renderPage()">
                                        跳转
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <#else >
                            <div style="text-align: center">
                                <h3><i class="icon icon-coffee"></i></h3>
                                <h3>暂无数据</h3>
                            </div>
                    </div>
                </div>
            </div>

        </#if>

    </div>
</div>










<script type="text/javascript">

    function  getNewData(pageNumber){
        if(!checkNotNull(pageNumber)){
            pageNumber=1;
        }
        window.location.href="/?pageNumber="+pageNumber;
    }

    function renderPage(){
        let renderPageNumber = $("#renderPageNumber").val();
        if (!checkNotNull(renderPageNumber)){
            alert("请输入跳转的页码！");
            return;
        }
        let totalPage = '${articleVoIPage.totalPage}';
        if(parseInt(renderPageNumber)>parseInt(totalPage)){
            renderPageNumber=totalPage;
        }
        getNewData(renderPageNumber);
    }

    function delArticle(articleId){
        if(confirm("是否删除")){
            if(!checkNotNull(articleId)){
                new $.zui.Messager('提示消息：程序出错，请刷新页面重试', {
                    type: 'success' // 定义颜色主题
                }).show();
                return;
            }
            $.post("/user/myArticleDel",{
                    articleId:articleId
                },
                function (data){
                    if(data.code==200){
                        alert(data.message)
                        location.reload();
                        return
                    }
                    console.log(data)
                    new $.zui.Messager(data.message,{
                        type: 'warning',
                        placement: 'center'
                    }).show()
                });
        }
    }
</script>

<#include "import/ViewBottom.ftl">