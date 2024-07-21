package com.bao.bookforum.service.impl;

import com.bao.bookforum.entity.Admin;
import com.bao.bookforum.mapper.AdminMapper;
import com.bao.bookforum.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2024-07-01
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
