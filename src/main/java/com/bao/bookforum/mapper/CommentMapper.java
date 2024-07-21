package com.bao.bookforum.mapper;

import com.bao.bookforum.entity.Comment;
import com.bao.bookforum.vo.CommentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Component
public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentVo> getArticleCommentList(@Param("articleId") String articleId);
}
