package com.nga.admin.entity.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Data
@TableName("role_permission")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * position职位id
	 */
	private Integer roleId;
	/**
	 * permission权限id
	 */
	private Integer permissionId;

}
