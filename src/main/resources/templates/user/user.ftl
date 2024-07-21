<#include "../import/ViewTop.ftl">

<div class="panel col-xs-6" style="margin-left: 380px">
    <div class="panel-body">
        <h3><i class="icon icon-desktop"></i> 用户名：${userName!}</h3>
        <h3><i class="icon icon-history"></i> 注册时间：${userRegisterTime?string('yyyy-MM-dd HH:mm:ss')}</h3>
    </div>
</div>

<button onclick="userUpdate('${userId}','${userName}',)" type="button" class="btn btn-mini"><i class="icon icon-cog"></i> 修改</button>

<div class="modal fade" id="userUpdateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/bao123/user/update" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                    <h4 class="modal-title">修改用户</h4>
                </div>
                <div class="modal-body">

                    <input type="hidden" name="userId" id="userId">
                    <div class="form-group">
                        <label for="userNameUpdate">用户名:</label>
                        <input type="text" class="form-control" id="userNameUpdate" placeholder="用户名" disabled="disabled">
                    </div>
                    <div class="form-group">
                        <label for="userPassword">密码:</label>
                        <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="userUpdateAction()" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function userUpdate(userId,userName){
        $('#userUpdateModal').modal('toggle', 'center');
        $("#userNameUpdate").val(userName);
    }

    function userUpdateAction(){
        let userId='${(userId)!}'
        let userName = $("#userNameUpdate").val();
        let userPassword = $("#userPassword").val();



        if(!(checkNotNull(userId))){
            zuiMsg("程序出错，刷新重试")
        }

        $.post("/user/update",{
                userId:userId,
                username:userName,
                userPassword:userPassword,
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
</script>
<#include "../import/ViewBottom.ftl">