package com.nga.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.entity.po.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.List;

/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-20 17:27:33
 */
public interface IUserService extends IService<User>, UserDetailsService {

    PageUtils queryPage(Map<String, Object> params);

    List<User> getAll();

}

