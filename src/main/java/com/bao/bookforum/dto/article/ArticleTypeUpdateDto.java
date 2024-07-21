package com.bao.bookforum.dto.article;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleTypeUpdateDto {

    /**
     * 帖子分类id
     */
    @TableId(value = "article_type_id")
    @NotBlank(message = "文章分类id不能为空")
    private String articleTypeId;

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

