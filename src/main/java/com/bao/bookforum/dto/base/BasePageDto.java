package com.bao.bookforum.dto.base;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BasePageDto {
    //当前页码
    @NotNull(message = "未获取到当前页码")
    private Integer pageNumber = 1;
    //每页显示多少条数据
    private Integer pageSize = 20;
}
