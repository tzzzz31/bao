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
 * @since 2024-06-30
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class ArticleType implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 帖子分类id
     */
    @TableId(value = "article_type_id")
    private String articleTypeId;

    /**
     * 帖子分类名称
     */
    private String articleTypeName;

    /**
     * 帖子分类排序
     */
    private Integer articleTypeSort;

    /**
     * 添加时间
     */
    private Date articleTypeAddTime;


}
