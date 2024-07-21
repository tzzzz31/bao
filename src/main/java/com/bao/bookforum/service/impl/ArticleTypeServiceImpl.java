package com.bao.bookforum.service.impl;

import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.mapper.ArticleTypeMapper;
import com.bao.bookforum.service.IArticleTypeService;
import com.bao.bookforum.vo.ArticleTypeVo;
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
 * @since 2024-06-30
 */
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements IArticleTypeService {

    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    //帖子类型列表，包含帖子数量
    @Override
    public List<ArticleTypeVo> articleTypeList() {
        return articleTypeMapper.articleTypeList();
    }
}
