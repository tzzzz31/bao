package com.bao.bookforum.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleTypeAddDto {

    /**
     * 帖子分类名称
     */
    @NotBlank(message = "帖子分类名称不能为空")
    private String articleTypeName;

    /**
     * 帖子分类排序
     */
    @NotNull(message = "帖子分类排序不能为空")
    private Integer articleTypeSort;

}

