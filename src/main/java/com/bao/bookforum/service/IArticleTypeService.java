package com.bao.bookforum.service;

import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.vo.ArticleTypeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2024-06-30
 */
public interface IArticleTypeService extends IService<ArticleType> {

    List<ArticleTypeVo> articleTypeList();
}
