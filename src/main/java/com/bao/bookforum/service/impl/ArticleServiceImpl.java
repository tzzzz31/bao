package com.bao.bookforum.service.impl;

import com.bao.bookforum.dto.article.PublishArticleActionDto;
import com.bao.bookforum.entity.Article;
import com.bao.bookforum.entity.ArticleTag;
import com.bao.bookforum.entity.ArticleTagList;
import com.bao.bookforum.entity.User;
import com.bao.bookforum.exception.R;
import com.bao.bookforum.exception.ResultCodeEnum;
import com.bao.bookforum.mapper.ArticleMapper;
import com.bao.bookforum.service.IArticleService;
import com.bao.bookforum.service.IArticleTagListService;
import com.bao.bookforum.vo.ArticleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.CommunicationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IArticleTagListService articleTagListService;

    //帖子列表
    @Override
    public IPage<ArticleVo> articleList(IPage<ArticleVo> articlePage, String articleTitle,String userId,String articleTypeId) {
        return articleMapper.articleList(articlePage,articleTitle,userId,articleTypeId);
    }

    //帖子发布方法
    @Override
    public R publishArticleAction(PublishArticleActionDto publishArticleActionDto, HttpServletRequest request, HttpServletResponse response) {
        //保存帖子
        Article article = new Article();
        User user =(User) request.getSession().getAttribute("user");
        System.out.println("类型id为："+publishArticleActionDto.getArticleTypeId());
        article.setArticleTypeId(publishArticleActionDto.getArticleTypeId());
        article.setUserId(user.getUserId());
        article.setArticleTitle(publishArticleActionDto.getArticleTitle());
        article.setArticleAddTime(new Date());
        article.setArticleContext(publishArticleActionDto.getArticleContext());
        article.setArticleLikeNumber(0);
        article.setArticleLookNumber(0);
        article.setArticleCollectionNumber(0);

        if (!save(article)){
            return R.setResult(ResultCodeEnum.ARTICLE_ERROR);
        }

        //保存帖子的标签
        String[] articleTagIds = publishArticleActionDto.getArticleTagIds();
        ArrayList<ArticleTagList> articleTagLists = new ArrayList<>();
        for (String articleTagId : articleTagIds) {
            ArticleTagList articleTagList = new ArticleTagList();
            System.out.println("articleId:"+article.getArticleId());
            articleTagList.setArticleId(article.getArticleId());
            articleTagList.setArticleTagId(articleTagId);
            articleTagLists.add(articleTagList);
        }
        if(!articleTagListService.saveBatch(articleTagLists,50)){
            return R.error();
        }
        return R.ok();

    }

    //根据文章id，获取文章信息
    @Override
    public ArticleVo getArticle(String articleId) {
        return articleMapper.getArticle(articleId);
    }
}
