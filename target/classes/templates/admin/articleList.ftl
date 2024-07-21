<#include "../import/top.ftl">

<#if articleVoIPage?? && articleVoIPage.list?size gt 0>
    <form class="form-horizontal" action="/bao123/article/list" method="get">
        <div class="form-group">
            <label for="exampleInputAccount4" class="col-sm-1">帖子标题</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="articleTitle" name="articleTitle" placeholder="帖子标题">
            </div>
            <div class="col-sm-1">
                <button type="submit"><i class="icon icon-search"></i>搜索</button>
            </div>
        </div>
    </form>
<div class="panel">
    <div class="panel-body">
        <h4>当前共有：${(articleVoIPage.total)!0}篇帖子</h4>
        <hr/>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>发布时间</th>
                <th>帖子类型</th>
                <th>发布者</th>
                <th>帖子标题</th>
                <th>浏览量</th>
                <th>点赞数</th>
                <th>收藏数</th>
                <th>操作</th>
            </tr>
            </thead>
            <#list articleVoIPage.list as articleVo>
                <tr>
                    <td>${(articleVo.articleAddTime)?string("yyyy-MM-DD HH:mm:ss")}</td>
                    <td>${(articleVo.articleTypeName)!}</td>
                    <td>${(articleVo.userName)!}</td>
                    <td>${(articleVo.articleTitle)!}</td>
                    <td>${(articleVo.articleLookNumber)!}</td>
                    <td>${(articleVo.articleLikeNumber)!}</td>
                    <td>${(articleVo.articleCollectionNumber)!}</td>
                    <td>
                        <div>
                            <button onclick="delArticle('${(articleVo.articleId)}')" type="button" class="btn btn-mini"><i class="icon icon-remove"></i> 删除</button>
                            <a target="_blank" href="/article?articleId=${(articleVo.articleId)!}" class="btn btn-mini"><i class="icon icon-eye-open"></i>查看</a>
                        </div>

                    </td>

                </tr>
            </#list>
            <tbody>
            </tbody>
        </table>
    </div>
</div>


    <div class="panel">
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
        </div>
    </div>


    <#else >
        <div style="text-align: center">
            <h3><i class="icon icon-coffee"></i></h3>
            <h3>暂无数据</h3>
        </div>
    </#if>




    <script type="text/javascript">

        function  getNewData(pageNumber){
            if(!checkNotNull(pageNumber)){
                pageNumber=1;
            }
            window.location.href="/bao123/article/list?pageNumber="+pageNumber+"<#if (articleName?? && articleName?length>0)>&userName=${articleName}</#if>"
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

        function zuiMsg(msg){
            new $.zui.Messager(msg, {
                type: 'success' // 定义颜色主题
            }).show();
            return;
        }

        function delArticle(articleId){
            if(confirm("是否删除")){
                if(!checkNotNull(articleId)){
                    new $.zui.Messager('提示消息：程序出错，请刷新页面重试', {
                        type: 'success' // 定义颜色主题
                    }).show();
                    return;
                }
                $.post("/bao123/article/del",{
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

        function checkNotNull(str){
            if(str==null||str===""||str.length<1||str==undefined){
                return false;
            }
            return true;
        }


    </script>

<#include "../import/bottom.ftl">