package com.nga.admin.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserVo {
    //TODO 限制密码长度 或者写正则 手机号码
    @NotEmpty(message = "用户名不能为空")
//    @Length(min = 6,max = 20,message = "密码长度必须在6-20之间")
//    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

}
