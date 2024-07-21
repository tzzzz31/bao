package com.bao.bookforum.mapper;

import com.bao.bookforum.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
