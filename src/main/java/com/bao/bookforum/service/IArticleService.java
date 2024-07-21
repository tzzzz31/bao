package com.bao.bookforum.service;

import com.bao.bookforum.dto.article.PublishArticleActionDto;
import com.bao.bookforum.entity.Article;
import com.bao.bookforum.exception.R;
import com.bao.bookforum.vo.ArticleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
public interface IArticleService extends IService<Article> {


    //帖子列表
    IPage<ArticleVo> articleList(IPage<ArticleVo> articlePage, String articleTitle,String userId,String articleTypeId);

    //帖子发布方法
    R publishArticleAction(PublishArticleActionDto publishArticleActionDto, HttpServletRequest request , HttpServletResponse response);

    //根据文章id，获取文章信息
    ArticleVo getArticle(String articleId);
}
