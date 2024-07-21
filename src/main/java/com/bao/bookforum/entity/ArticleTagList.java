package com.bao.bookforum.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleTagList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章对应标签id
     */
    @TableId(value = "article_tag_list_id")
    private String articleTagListId;

    /**
     * 帖子id
     */
    @TableId(value = "article_id")
    private String articleId;

    /**
     * 文章标签id
     */
    private String articleTagId;


}
