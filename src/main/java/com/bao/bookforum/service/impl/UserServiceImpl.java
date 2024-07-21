package com.bao.bookforum.service.impl;

import com.bao.bookforum.entity.User;
import com.bao.bookforum.mapper.UserMapper;
import com.bao.bookforum.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-06-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
