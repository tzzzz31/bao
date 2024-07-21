package com.bao.bookforum.dto.user;

import com.bao.bookforum.dto.base.BasePageDto;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserListPageDto extends BasePageDto {
    //用户名
    private String userName;
}
