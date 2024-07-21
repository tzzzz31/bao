package com.bao.bookforum.dto.article;

import com.bao.bookforum.dto.base.BasePageDto;
import lombok.Data;

@Data
public class ArticlePageDto extends BasePageDto {
    //帖子标题
    private String articleTitle;

}
