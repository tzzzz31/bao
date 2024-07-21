<#include "../import/ViewTop.ftl">
    <div class="panel  col-sm-9" style="margin-left: 195px">
        <div>
            <i class="icon icon-book">我的帖子</i>
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
                        <button onclick="delArticle('${(articleVo.articleId)}')" type="button" class="btn btn-mini pull-right"><i class="icon icon-remove"></i> 删除</button>
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
                        </div>
                    </div>
                </div>
                <#else >
                    <div style="text-align: center">
                        <h3><i class="icon icon-coffee"></i></h3>
                        <h3>暂无数据</h3>
                    </div>
            </#if>

        </div>
    </div>










<script type="text/javascript">

    function  getNewData(pageNumber){
        if(!checkNotNull(pageNumber)){
            pageNumber=1;
        }
        window.location.href="/user/myArticleList?pageNumber="+pageNumber;
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
<#include "../import/bottom.ftl">