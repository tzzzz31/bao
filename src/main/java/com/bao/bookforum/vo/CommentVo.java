package com.bao.bookforum.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentVo {

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

    private String userName;

    @TableId(value = "comment_context")
    private String commentContext;
}
