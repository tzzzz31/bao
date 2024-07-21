<#include "../import/top.ftl">
<div class="panel">
    <div class="panel-body">
        <form class="form-horizontal" action="/bao123/user/list" method="get">
            <div class="form-group">
                <label for="userName" class="col-sm-1">用户名：</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="userName" placeholder="用户名">
                </div>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-success">查询</button>
                </div>
            </div>
        </form>
    </div>
</div>

<#if userPage?? && userPage.list?size gt 0>
<h4><i class="icon icon-info-sign">提示:被冻结的用户无法登陆</i></h4>
<div class="panel">
    <div class="panel-body">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>注册时间</th>
                <th>用户名</th>
                <th>是否冻结</th>
                <th>操作</th>
            </tr>
            </thead>
            <#list userPage.list as user>
                <tr>
                    <td>${(user.userRegisterTime)?string("yyyy-MM-DD HH:mm:ss")}</td>
                    <td>${(user.userName)!}</td>
                    <td>
                        <#if (user.userFrozen)?? && (user.userFrozen)==1>
                            <span class="label label-danger">冻结</span>
                        <#else >
                            <span class="label label-success">正常</span>
                        </#if>
                    </td>
                    <td>
                        <button onclick="userUpdate('${user.userId}','${user.userName}','${user.userFrozen}')" type="button" class="btn btn-mini"><i class="icon icon-cog"></i> 修改</button>
                        <button onclick="delUser('${(user.userId)}')" type="button" class="btn btn-mini"><i class="icon icon-remove"></i> 删除</button>
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

                    <#if userPage.pageNumber lte 1>
                        <li class="previous disabled">
                            <a href="javascript:void (0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    <#else>
                        <li class="previous">
                            <a href="javascript:void (0)"><i class="icon icon-chevron-left"></i></a>
                        </li>
                    </#if>
                    <li >
                        <a href="javascript:void (0)" class="btn">${userPage.pageNumber}页/共${userPage.totalPage}页</a>
                    </li>
                    <#if userPage.pageNumber gte userPage.totalPage>
                        <li class="next disabled">
                            <a href="javascript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    <#else>
                        <li class="next" onclick="getNewData('${userPage.pageNumber+1}')">
                            <a href="javascript:void(0)"><i class="icon icon-chevron-right"></i></a>
                        </li>
                    </#if>
                    <li class="previous" onclick="getNewData('${userPage.totalPage}')">
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
                            <label for="exampleInputAccount1">用户名:</label>
                            <input type="text" class="form-control" id="userNameUpdate" placeholder="用户名" disabled="disabled">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">密码:</label>
                            <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputMoney1">是否冻结</label>
                            <div class="input-group">
                                <label class="radio-inline">
                                    <input type="radio" name="userFrozen" value="0" checked="checked"> 正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="userFrozen" value="1"> 冻结
                                </label>
                            </div>
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

    <script type="text/javascript">

        function zuiMsg(msg){
            new $.zui.Messager(msg, {
                type: 'success' // 定义颜色主题
            }).show();
            return;
        }

        function userUpdateAction(){
            let userId= $("#userId").val();
            let userName = $("#userNameUpdate").val();
            let userPassword = $("#userPassword").val();
            let userFrozen=$("input[name='userFrozen']:checked").val();



            if(!(checkNotNull(userId))){
                zuiMsg("程序出错，刷新重试")
            }
            if(!(checkNotNull(userFrozen))){
                zuiMsg("请选择是否冻结用户")
            }

            $.post("/bao123/user/update",{
                    userId:userId,
                    username:userName,
                    userPassword:userPassword,
                    userFrozen: userFrozen
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

        function userUpdate(userId,userName,userFrozen){
            $('#userUpdateModal').modal('toggle', 'center');
            $("#userId").val(userId);
            $("#userNameUpdate").val(userName);
            $(":radio[name='userFrozen'][value='"+userFrozen+"']").prop("checked",'checked');
        }

        function delUser(userId){
            if(confirm("是否删除")){
                if(!checkNotNull(userId)){
                    new $.zui.Messager('提示消息：程序出错，请刷新页面重试', {
                        type: 'success' // 定义颜色主题
                    }).show();
                    return;
                }
                $.post("/bao123/user/del",{
                        userId:userId
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

        function  getNewData(pageNumber){
            if(!checkNotNull(pageNumber)){
                pageNumber=1;
            }
            window.location.href="/bao123/user/list?pageNumber="+pageNumber+"<#if (userName?? && userName?length>0)>&userName=${userName}</#if>"
        }

        function renderPage(){
            let renderPageNumber = $("#renderPageNumber").val();
            if (!checkNotNull(renderPageNumber)){
                alert("请输入跳转的页码！");
                return;
            }
            let totalPage = '${userPage.totalPage}';
            if(parseInt(renderPageNumber)>parseInt(totalPage)){
                renderPageNumber=totalPage;
            }
            getNewData(renderPageNumber);
        }
        function checkNotNull(str){
            if(str==null||str===""||str.length<1||str==undefined){
                return false;
            }
            return true;
        }
    </script>

<#include "../import/bottom.ftl">