package com.nga.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.entity.po.Role;

import java.util.Map;
import java.util.List;

/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
public interface IRoleService extends IService<Role> {

    PageUtils queryPage(Map<String, Object> params);

    List<Role> getAll();

}

