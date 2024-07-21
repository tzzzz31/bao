<#include "../import/top.ftl">

<#if articleTypeVoList?? && articleTypeVoList?size gt 0>
<div class="panel">
    <div class="panel-body">
        <button type="button" class="btn btn-success" onclick="addArticleType()">添加文章类型</button>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>排序</th>
                <th>添加时间</th>
                <th>文章类型名称</th>
                <th>文章数量</th>
                <th>操作</th>
            </tr>
            </thead>
            <#list articleTypeVoList as articleType>
                <tr>
<#--                    <#if articleType.articleTypeSort??>-->
                        <td>${(articleType.articleTypeSort)!}</td>
<#--                    </#if>-->
<#--                    <#else >-->

                    <td>
                        ${(articleType.getArticleTypeAddTime())?string("yyyy-MM-DD HH:mm:ss")}
                    </td>
                    <td>${(articleType.articleTypeName)!}</td>
                    <td>${(articleType.articleCount)!}</td>
                    <td>
                        <button onclick="UpdateArticleType('${articleType.articleTypeId}','${articleType.articleTypeName}','${articleType.articleTypeSort}')" type="button" class="btn btn-mini"><i class="icon icon-cog"></i> 修改</button>
                        <button onclick="delArticleType('${(articleType.articleTypeId)}')" type="button" class="btn btn-mini"><i class="icon icon-remove"></i> 删除</button>
                    </td>
                </tr>
            </#list>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
    <#else >
        <div style="text-align: center">
            <h3><i class="icon icon-coffee"></i></h3>
            <h3>暂无数据</h3>
        </div>
    </#if>

<div class="modal fade" id="articleTypeUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/bao123/article/update" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                    <h4 class="modal-title">修改文章类型</h4>
                </div>
                <div class="modal-body">

                        <input type="hidden" name="articleTypeId" id="articleTypeId">
                        <div class="form-group">
                            <label for="articleTypeName">类型名称:</label>
                            <input type="text" class="form-control" id="articleTypeName" name="articleTypeName" placeholder="类型名称">
                        </div>
                        <div class="form-group">
                            <label for="articleTypeSort">类型排序:</label>
                            <input type="number" class="form-control" id="articleTypeSort" name="articleTypeSort" placeholder="类型排序">
                        </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="articleUpdateAction()" class="btn btn-primary">保存</button>
                </div>
          </form>
        </div>
    </div>
</div>

<div class="modal fade" id="articleTypeAddModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/bao123/article/update" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                    <h4 class="modal-title">添加文章类型</h4>
                </div>
                <div class="modal-body">

                    <input type="hidden" name="articleTypeId" id="articleTypeId">
                    <div class="form-group">
                        <label for="articleTypeName">类型名称:</label>
                        <input type="text" class="form-control" id="articleTypeNameInput" name="articleTypeNameInput" placeholder="类型名称">
                    </div>
                    <div class="form-group">
                        <label for="articleTypeSort">类型排序:</label>
                        <input type="number" class="form-control" id="articleTypeSortInput" name="articleTypeSortInput" placeholder="类型排序">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="articleAddAction()" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

    <script type="text/javascript">

        function zuiMsg(msg){
            new $.zui.Messager(msg, {
                type: 'success' // 定义颜色主题
            }).show();
            return;
        }

        function articleAddAction(){
            let articleTypeName = $("#articleTypeNameInput").val();
            let articleTypeSort = $("#articleTypeSortInput").val();

            if((!checkNotNull(articleTypeId))){
                    zuiMsg("添加失败，请重试");
                }
                $.post("/bao123/article/type/add",{
                    articleTypeName:articleTypeName,
                    articleTypeSort:articleTypeSort,
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

        function articleUpdateAction(){
            let articleTypeId= $("#articleTypeId").val();
            let articleTypeName = $("#articleTypeName").val();
            let articleTypeSort = $("#articleTypeSort").val();

            if(!(checkNotNull(articleTypeId))){
                zuiMsg("程序出错，刷新重试")
            }

            $.post("/bao123/article/type/update",{
                        articleTypeId:articleTypeId,
                        articleTypeName:articleTypeName,
                        articleTypeSort:articleTypeSort,
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

        function UpdateArticleType(articleTypeId,articleTypeName,articleTypeSort){
            $('#articleTypeUpdateModal').modal('toggle', 'center');
            $("#articleTypeId").val(articleTypeId);
            $("#articleTypeName").val(articleTypeName);
            $("#articleTypeSort").val(articleTypeSort);
        }

        function addArticleType(articleTypeName,articleTypeSort){
            $('#articleTypeAddModal').modal('toggle', 'center');
            $("#articleTypeName").val(articleTypeName);
            $("#articleTypeSort").val(articleTypeSort);
        }

        function delArticleType(articleTypeId){
            if(confirm("是否删除")){
                if(!checkNotNull(articleTypeId)){
                    new $.zui.Messager('提示消息：程序出错，请刷新页面重试', {
                        type: 'success' // 定义颜色主题
                    }).show();
                    return;
                }
                $.post("/bao123/article/type/del",{
                        articleTypeId:articleTypeId
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