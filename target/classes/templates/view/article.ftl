<#include "../import/ViewTop.ftl">

    <style>
        .comments-list{
            margin-left: 25px;
        }
        .hoverPalm{
            cursor: pointer;
        }
    </style>

        <ol class="breadcrumb">
            <li><a href="/"><i class="icon icon-home"></i>首页</a></li>
<#--            <li><a href="/article/list?articleTypeId=${(articleType.articleTypeId)!}">${(articleType.articleTypeName)!}<i class="icon icon-home"></i></a></li>-->
            <li class="active">${(article.articleTitle)!}</li>
        </ol>
        <hr/>

    <div class="col-xs-12">
        <div class="panel">
            <div >
                <article class="article">
                    <header>
                        <h1 class="text-center">${(article.articleTitle)!}</h1>
                        <dl class="dl-inline">
                            <dd>${article.getArticleAddTime()?string("yyyy-MM-dd")}</dd>
                            <dt></dt>
                            <dd class="pull-right">
                                <span class="label label-info">
                                    <i class="icon-eye-open"></i>${(article.articleLookNumber)!}
                                </span>
                                <span class="label label-success hoverPalm" onclick="articleLike('${(article.articleId)!}')">
                                    <i class="icon-thumbs-up"></i>${(article.articleLikeNumber)!}
                                </span>
                            </dd>
                        </dl>
                    </header>
                    <section class="content">
                        <p>
                            ${(article.articleContext)!}
                        </p>
                    </section>
                    <hr/>
                    <footer>

                        <#if commentList?? && commentList?size gt 0>

                            <#list commentList as comentVo>
                                <div class="col-xs-12" id="commentListBox" >
                                    <div class="content">
                                        <div class="pull-right">${comentVo.getCommentTime()?string("yyyy-MM-DD HH:mm:ss")}</div>
                                        <div>
                                            <a href="javascript:void(0);">
                                                <strong>
                                                    <i class="icon icon-user" href="javascript:void(0);"></i>${(comentVo.getUserName())!}
                                                </strong>
                                            </a>
                                        </div>
                                        <div class="text">${(comentVo.getCommentContext())!}</div>
                                    </div>
                                </div>
                            </#list>

                        <#else >
                            <div style="text-align: center">
                                <h3><i class="icon icon-coffee"></i></h3>
                                <h3>暂无评论,快来评论吧！</h3>
                            </div>
                        </#if>

                        <div class="form-group col-xs-12" style="margin-top: 25px;padding: 2px">
                            <hr/>
                            <label for="commentContent">撰写评论</label>
                            <#if user??>
                                    <textarea id="commentContent" name="commentContent" maxlength="1500" class="form-control new-comment-text" rows="2" placeholder="撰写评论"></textarea>
                                <#else >
                                    <textarea id="commentContent" name="commentContent" disabled="disabled" maxlength="1500" class="form-control new-comment-text" rows="2" placeholder="请先登录，登录后才可以评论"></textarea>
                            </#if>
                            <button type="button" onclick="saveComment('${(article.articleId)!}')" class="btn btn-success pull-right" style="margin-top: 10px">评论</button>
                        </div>
                    </footer>
                </article>
            </div>
        </div>
    </div>

     <script>
        function saveComment(articleId) {
            let commentContent = $("#commentContent").val();
            if (!checkNotNull(commentContent) || commentContent.label < 1) {
                zuiMsg("不可以发布空评论哦")
                return;
            }
            console.log("评论内容为："+commentContent);
            $.post("/user/saveComment", {
                    articleId: articleId,
                    commentContent: commentContent
                },
                function (data) {
                    if (data.code == 200) {
                        // //插入评论到评论区
                        // let commentId = data.data.commentId;
                        // let userId = data.data.userId;
                        // let commentContext = data.data.commentContext;
                        // let commentTime = data.data.commentTime;
                        // let commentGoodNumber = data.data.commentGoodNumber;

                        location.reload();

                        return
                    }
                    zuiMsg(data.message);
                });
        }

        function articleLike(articleId) {
            $.post("/articleLike",{
                    articleId:articleId
                },
                function (data){
                    if(data.code==200){
                        zuiMsg("点赞成功！");
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



    </script>

<#include "../import/ViewBottom.ftl">