package com.bao.bookforum.dto.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserDto {
    @TableId(value = "user_id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 注册时间
     */
    private Date userRegisterTime;

    //是否冻结 0正常 1冻结
    private Integer userFrozen;

}
