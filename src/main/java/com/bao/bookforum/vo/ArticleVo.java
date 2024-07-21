package com.bao.bookforum.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo {
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
    //用户名
    private String userName;

    //帖子类型id
    private String articleTypeId;

    //帖子类型名称
    private String articleTypeName;

    //帖子内容
    private String articleContext;
}
