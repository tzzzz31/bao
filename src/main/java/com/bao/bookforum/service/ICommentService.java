package com.bao.bookforum.service;

import com.bao.bookforum.entity.Comment;
import com.bao.bookforum.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
public interface ICommentService extends IService<Comment> {
    List<CommentVo> getArticleCommentList(String articleId);
}
