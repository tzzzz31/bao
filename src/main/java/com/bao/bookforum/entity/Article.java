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
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(value = "article_id")
    private String articleId;

    /**
     * 帖子标题
     */
    private String articleTitle;

    /**
     * 帖子添加时间
     */
    private Date articleAddTime;

    /**
     * 文章内容
     */
    private String articleContext;

    /**
     * 点赞次数
     */
    private Integer articleLikeNumber;

    /**
     * 观看次数
     */
    private Integer articleLookNumber;

    /**
     * 收藏次数
     */
    private Integer articleCollectionNumber;

    /**
     * 用户id
     */
    private String userId;

    //帖子类型id
    private String articleTypeId;


}
