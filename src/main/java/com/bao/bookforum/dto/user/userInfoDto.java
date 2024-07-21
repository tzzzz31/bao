package com.bao.bookforum.dto.user;

import lombok.Data;

@Data
public class userInfoDto {
    private String userName;
    private String userPassword;
    private String verifyCode;
}
