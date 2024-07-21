package com.bao.bookforum.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

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
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子标签id
     */
    @TableId(value = "article_tag_id")
    private String articleTagId;

    /**
     * 标签名字
     */
    private String articleTagName;

    /**
     * 添加时间
     */
    private Date articleTagAddtime;


}
