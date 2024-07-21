package com.bao.bookforum.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "comment_id")
    private String commentId;

    /**
     * 帖子id
     */
    private String articleId;

    /**
     * 评论时间
     */
    private Date commentTime;

    /**
     * 用户id（评论人）
     */
    private String userId;

    /**
     * 评论点赞次数
     */
    private Integer commentLikeNumber;

    @TableField(exist = false)
    private String userName;

    @TableId(value = "comment_context")
    private String commentContext;


}
