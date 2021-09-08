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
@TableName("user_role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户主键
	 */
	private Long userId;
	/**
	 * 角色主键
	 */
	private Integer roleId;

}
