package com.bao.bookforum.service.impl;

import com.bao.bookforum.entity.Comment;
import com.bao.bookforum.mapper.CommentMapper;
import com.bao.bookforum.service.ICommentService;
import com.bao.bookforum.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    //帖子评论列表
    @Override
    public List<CommentVo> getArticleCommentList(String articleId) {
        return commentMapper.getArticleCommentList(articleId);
    }
}
