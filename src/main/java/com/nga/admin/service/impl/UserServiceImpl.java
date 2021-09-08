package com.nga.admin.service.impl;

import com.nga.admin.entity.po.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.common.utils.Query;

import com.nga.admin.dao.UserDao;
import com.nga.admin.entity.po.User;
import com.nga.admin.service.IUserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<User> page = this.page(
                new Query<User>().getPage(params),
                new QueryWrapper<User>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (user == null)throw new UsernameNotFoundException("用户名不存在");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<Role> roles = userDao.getRolesByUserId(user.getUserId());
        for (Role role : roles) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
//            List<Permission> permissions = userDao.getPermissionsByRoleId(role.getBasicPosId());
//            for (Permission permission : permissions) {
//                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getBasicPermissionUrl());
//                grantedAuthorities.add(authority);
//            }
        }
        user.setGrantedAuthorities(grantedAuthorities);
        return user;
    }
}