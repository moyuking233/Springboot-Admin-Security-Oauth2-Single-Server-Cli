package com.nga.admin.dao;

import com.nga.admin.entity.po.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {
    @Select("select * from role where is_deleted = 0")
    List<Role> getAll();
}
