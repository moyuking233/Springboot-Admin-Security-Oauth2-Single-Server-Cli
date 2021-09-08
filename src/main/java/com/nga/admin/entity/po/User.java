package com.nga.admin.entity.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * 
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Data
@TableName("user")
public class User implements Serializable , UserDetails {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户主键
	 */
	@TableId(type = IdType.AUTO)
	private Long userId;
	/**
	 * 用户名
	 */
	private String userUsername;
	/**
	 * 密码
	 */
	private String userPassword;
	/**
	 * 证书是否过期
	 */
	private Boolean isCredentialsExpired;
	/**
	 * 登录是否过期
	 */
	private Boolean isExpired;
	/**
	 * 账户是否锁定
	 */
	private Boolean isLocked;
	/**
	 * 最后登录时间
	 */
	private Date gmtLastLogin;
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
	/* ======================== 数据库没有的字段 ========================== */

	/**
	 * 角色信息
	 */
	@TableField(exist = false)
	private Role role;
	/**
	 * 授权信息
	 */
	@TableField(exist = false)
	private Set<GrantedAuthority> grantedAuthorities;
	/* ======================== 接口 UserDetails 应用的方法 ========================== */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.userUsername;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (isExpired != null) return !isExpired;
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (isLocked != null) return !isLocked;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (isCredentialsExpired != null)return !isCredentialsExpired;
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (isDeleted != null)return !isDeleted;
		return false;
	}
}
