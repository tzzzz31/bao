package com.bao.bookforum.exception;


public enum ResultCodeEnum {

    SUCCESS(true,200,"成功"),

    UPDATE_SUCCESS(true,200,"用户信息修改成功！"),

    DELETE_SUCCESS(true,200,"删除成功！"),

    UNKNOWN_REASON(false,500,"未知错误"),

    TEST_NUMBER(false,501,"计算错误"),

    ERROR_NULL(false,502,"输入信息不完整"),

    ERROR_CIRCLE(false,503,"验证码不正确"),

    ADMING_ERROR(false,504,"用户或密码错误"),

    ERROR_USER(false,505,"用户名和密码不能一致"),

    HAVE_USER(false,506,"该用户名已经被注册"),

    ERROR_USERNAME(false,507,"用户名不存在"),

    ERRO_PASSWORD(false,508,"用户名或密码错误"),

    USER_BAN(false,509,"该用户已被冻结，请联系管理员"),

    ARTICLE_ERROR(false,510,"帖子发布失败"),

    TIME_OUT(false,511,"登录过期，请重新登录"),

    TIME_ERROR(false,512,"您评论太快了，请休息一下");


    private final Boolean success;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCodeEnum{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
