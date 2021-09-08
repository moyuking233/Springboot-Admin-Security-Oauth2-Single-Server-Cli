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

import com.nga.admin.dao.UserRoleDao;
import com.nga.admin.entity.po.UserRole;
import com.nga.admin.service.IUserRoleService;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRole> page = this.page(
                new Query<UserRole>().getPage(params),
                new QueryWrapper<UserRole>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserRole> getAll() {
        return userRoleDao.getAll();
    }

}