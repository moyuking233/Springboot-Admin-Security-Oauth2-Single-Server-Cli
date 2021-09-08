package com.nga.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.common.utils.Query;

import com.nga.admin.dao.RoleDao;
import com.nga.admin.entity.po.Role;
import com.nga.admin.service.IRoleService;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Role> page = this.page(
                new Query<Role>().getPage(params),
                new QueryWrapper<Role>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

}