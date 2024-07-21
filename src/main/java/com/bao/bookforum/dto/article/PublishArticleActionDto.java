package com.bao.bookforum.dto.article;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PublishArticleActionDto {

    //帖子标题
    @NotBlank(message = "请填写帖子标题")
    private String articleTitle;

    //类型id
    @NotBlank(message = "请选择帖子类型")
    private String articleTypeId;

    private String[] articleTagIds;

    //帖子内容
    @NotBlank(message = "请填写文章内容")
    private String articleContext;
}
