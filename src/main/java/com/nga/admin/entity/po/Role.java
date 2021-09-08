package com.nga.admin.entity.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Data
@TableName("role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(type = IdType.AUTO)
	private Integer roleId;
	/**
	 * 角色名（Sercurity框架配合）
	 */
	private String roleName;
	/**
	 * 角色描述
	 */
	private String roleDesc;
	/**
	 * 角色昵称英文名
	 */
	private String roleNickNameEn;
	/**
	 * 角色昵称中文名
	 */
	private String roleNickNameZh;
	/**
	 * 最后修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date gmtModified;
	/**
	 * 最后修改用户所对应的id
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer gmtModifiedMan;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date gmtCreate;
	/**
	 * 创建用户所对应的id
	 */
	@TableField(fill = FieldFill.INSERT)
	private Integer gmtCreateMan;
	/**
	 * 逻辑删除位
	 */
	@TableField(fill = FieldFill.INSERT)
	private Boolean isDeleted;

}
