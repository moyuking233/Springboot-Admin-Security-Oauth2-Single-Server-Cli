package com.nga.admin.dao;

import com.nga.admin.entity.po.Role;
import com.nga.admin.entity.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-20 17:27:33
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select * from user where is_deleted = 0")
    List<User> getAll();
    @Select("select * from user where user_username = #{username}")
    User loadUserByUsername(String username);
    @Select("select r.* from role r,user u,user_role ur where u.user_id = #{username} and u.user_id = ur.user_id and r.role_id = ur.role_id")
    List<Role> getRolesByUserId(Long userId);
}
