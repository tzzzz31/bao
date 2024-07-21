package com.bao.bookforum.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2024-06-30
 */
@Data
public class ArticleTypeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子分类id
     */
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

    //帖子数量
    @TableField(exist = false)
    private Integer articleCount;


}
