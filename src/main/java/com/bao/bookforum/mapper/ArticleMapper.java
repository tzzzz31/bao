package com.bao.bookforum.mapper;

import com.bao.bookforum.entity.Article;
import com.bao.bookforum.vo.ArticleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    //帖子列表
    IPage<ArticleVo> articleList(IPage<ArticleVo> articlePage, @Param("articleTitle") String articleTitle,@Param("userId") String userId,@Param("articleTypeId") String articleTypeId);

    ArticleVo getArticle(@Param("articleId") String articleId);
}
