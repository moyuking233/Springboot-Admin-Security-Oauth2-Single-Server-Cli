package com.nga.admin.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Data
@TableName("permission")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer permissionId;
	/**
	 * 允许放行的url
	 */
	private String permissionUrl;
	/**
	 * 菜单名
	 */
	private String permissionName;
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
