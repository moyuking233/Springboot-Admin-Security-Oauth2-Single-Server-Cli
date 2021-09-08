package com.nga.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.entity.dto.PermissionWithRolesDto;
import com.nga.admin.entity.po.Permission;

import java.util.Map;
import java.util.List;

/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
public interface IPermissionService extends IService<Permission> {

    PageUtils queryPage(Map<String, Object> params);

    List<Permission> getAll();

    List<PermissionWithRolesDto> getAllPermissionsWithRoles();

}

