<#include "../import/ViewTop.ftl">
<#--<#include "../import/nav_tab.ftl">-->
    <style>
        .label:hover{
            cursor: pointer;
        }
        .center-container {
            margin: 0 auto;
            background-color: red;
        }
    </style>
        <div class="col-xs-12" style="display: grid;place-items: center;">
            <div class="panel col-xs-9 col-sm-9">
                <div class="panel-heading">
                    <i class="icon icon-edit"></i>发布帖子
                </div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="articleTitle" class="col-xs-2 col-sm-1">标题</label>
                            <div class="col-xs-10 col-sm-4">
                                <input type="text" class="form-control" name="articleTitle" id="articleTitle" placeholder="标题">
                            </div>
                            <#if articleTypeList?? && articleTagList?size gt 0>
                                <label for="articleType" class="col-xs-2 col-sm-1">类型</label>
                                <div class="col-xs-4 col-sm-2">
                                    <select class="form-control" id="articleType">
                                        <#list articleTypeList as articleType>
                                            <option value="${(articleType.articleTypeId!)}">${(articleType.articleTypeName!)}</option>
                                        </#list>
                                    </select>
                                </div>
                            </#if>
                        </div>

                        <#if articleTagList?? && articleTagList?size gt 0>
                            <div class="form-group">
                                <label for="" class="col-xs-2 col-sm-1">标签</label>
                                <div class="col-xs-10">
                                    <#list articleTagList as articleTag>
                                        <span class="label" onclick="selectArticleTag('${(articleTag.articleTagId)}')" id="${articleTag.articleTagId!}">${articleTag.articleTagName!}</span>
                                    </#list>
                                </div>
                            </div>
                        </#if>
                    </form>
                    <textarea id="articleContext" maxlength="14999"></textarea>
                    <div class="form-group" style="margin-top: 15px;text-align: right" >
                        <button onclick="publisArticle()" type="button" class="btn btn-success"><i class="icon icon-edit-sign"></i>发布</button>
                    </div>
                </div>
            </div>
        </div>
    <script src="https://cdn.jsdelivr.net/gh/xwlrbh/HandyEditor@1.9.0/HandyEditor.min.js"></script>

    <script>

        var he = HE.getEditor('articleContext',{
            width:'100%',
            height:'200px',
            autoHeight: true,
            autoFloat: false,
            top0ffest: 0,
            uploadPhoto:true,
            uploadPhotoHandler:'/user/uploadFile',
            uploadPhotoSize:1024,
            uploadPhotoType:'gif,png,jpg,jpeg',
            uploadPhotoSizeError:'不能上传大于1024KB的图片',
            uploadPhotoTypeError:'只能上传gif,png,jpg,jpeg格式的图片',
            uploadParam:{},
            lang:'zh-jian',
            skin:'HandyEditor',
            externalSkin:'',
            // item:['bold','italic','strike','underline','fontSize','fotName','paragraph','color','backColor','|','center']
        })

        let articleTagIds = [];

        function publisArticle() {


            let articleTitle = $("#articleTitle").val();
            let articleType = $("#articleType").val();
            let articleContext =he.getText();

            if(!checkNotNull(articleTitle)){
                zuiMsg("请填写标题");
                return;
            }

            if(!checkNotNull(articleType)){
                zuiMsg("请选择类型");
                return;
            }

            if(!checkNotNull(articleContext)){
                zuiMsg("请填写文章内容");
                return;
            }

            console.log(articleTitle);
            console.log(articleContext);
            console.log(articleType);
            console.log(articleTagIds);

            let formData=new FormData();
            formData.append("articleTitle",articleTitle);
            formData.append("articleTypeId",articleType);
            formData.append("articleTagIds",articleTagIds);
            formData.append("articleContext",articleContext);

            $.ajax({
                    url:"/user/publishArticleAction",
                    type: 'POST',
                    data: formData,
                    //让jQuery不要处理发送的数据
                    processData: false,
                    //让jQuery不要设置Content-Type请求头
                    contentType: false,
                    beforeSend: function (){
                        console.log("正在进行，请稍后")
                    },
                    success: function (data){
                        if(data.code=200){
                                // zuiMsg("发布成功！");
                                alert('发布成功！');
                                window.location.href='/user/myArticleList';
                                return;
                        }
                        zuiMsg(data.message);
                    },
                    error: function (responseStr){
                        console.log("error");
                    }
                });

        }




        function selectArticleTag(articleTagId) {
           let index = articleTagIds.indexOf(articleTagId);
           //判断是否选中标签，选中的话就换成success
           if(index>-1){
               articleTagIds.splice(index,-1);
               $("#"+articleTagId).removeClass("label-success");
           }else {
               articleTagIds[articleTagIds.length]=articleTagId;
               $("#"+articleTagId).addClass("label-success");
           }
            return;
        }

    </script>
<#include "../import/ViewBottom.ftl">