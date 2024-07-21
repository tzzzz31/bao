<#include "../import/top.ftl">

<div class="panel col-xs-12">
    <div class="panel-body">

        <div class="form-inline">
            <div class="form-group">
                <label for="exampleInputEmail3">标签名称</label>
                <input class="form-control" type="text" id="articleTagAdd">
            </div>
            <button type="button" class="btn btn-success" onclick="articleTagAdd()">添加</button>
        </div>



        <hr/>
        <#if articleTagList?? && articleTagList?size gt 0>
            <#list  articleTagList as articleTag>
                <div class="col-sm-2" style="padding: 2px">

                    <div class="img-thumbnail">
                        <input class="form-control" type="text" value="${(articleTag.articleTagName)!}" id="${(articleTag.articleTagId)!}">
                        <div style="text-align: left">
                            <i class="icon icon-cog" data-toggle="tooltip" data-placement="bottom" title="修改" onclick="articleTagUpdate('${(articleTag.articleTagId)!}')"></i>
                            <i class="icon icon-remove" data-toggle="tooltip" data-placement="bottom" title="删除" onclick="articleTagDel('${(articleTag.articleTagId)!}')"></i>
                        </div>
                    </div>

                </div>

            </#list>
            <#else >
                <div class="panel">
                    <div class="panel-body">
                        <div>
                            <h3><i class="icon icon-coffee"></i></h3>
                            <h3>暂无数据</h3>
                        </div>
                    </div>
                </div>
        </#if>
    </div>
</div>

    <script>
        function articleTagAdd() {
             let articleTagName = $("#articleTagAdd").val();
             if(!checkNotNull(articleTagName)){
                 zuiMsg("标签名称不能为空")
                 return;
             }
            $.post("/bao123/article/tag/addOrUpdate",{
                    articleTagName:articleTagName,
                },
                function (data){
                    if(data.code==200){
                        alert(data.message)
                        location.reload();
                        return
                    }
                    zuiMsg(data.message);
                });
        }

        function articleTagDel(articleTagId) {
            if (confirm("确定要删除吗？")){
                $.post("/bao123/article/tag/del",{
                        articleTagId:articleTagId,
                    },
                    function (data){
                        if(data.code==200){
                            alert(data.message)
                            location.reload();
                            return
                        }
                        zuiMsg(data.message);
                    });
            }
        }

        function articleTagUpdate(articleTagId) {
            if (confirm("确定要修改吗？")){
                let articleTagName =  $("#"+articleTagId).val();
                console.log("articleTagId"+articleTagId);
                console.log("articleTagName"+articleTagName);
                if (!checkNotNull(articleTagId)|| !checkNotNull(articleTagName)){
                    zuiMsg("修改参数不正确");
                    return;
                }

                $.post("/bao123/article/tag/addOrUpdate",{
                        articleTagId:articleTagId,
                        articleTagName:articleTagName,
                    },
                    function (data){
                        if(data.code==200){
                            alert(data.message)
                            location.reload();
                            return
                        }
                        zuiMsg(data.message);
                    });
            }

        }

        $('[data-toggle="tooltip"]').tooltip({
            placement: 'bottom'
        });

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