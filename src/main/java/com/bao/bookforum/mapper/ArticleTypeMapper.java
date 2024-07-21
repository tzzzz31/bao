package com.bao.bookforum.mapper;

import com.bao.bookforum.entity.ArticleType;
import com.bao.bookforum.vo.ArticleTypeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2024-06-30
 */
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {
    //帖子类型列表，包含帖子数量
    List<ArticleTypeVo> articleTypeList();
}
