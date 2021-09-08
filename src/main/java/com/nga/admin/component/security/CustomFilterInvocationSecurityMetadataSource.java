package com.nga.admin.component.security;

import com.nga.admin.entity.dto.PermissionWithRolesDto;
import com.nga.admin.entity.po.Role;
import com.nga.admin.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


//根据用户的请求的路径 分析用户角色
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    IPermissionService permissionService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<PermissionWithRolesDto> permissions = permissionService.getAllPermissionsWithRoles();
        for (PermissionWithRolesDto permission :
                permissions) {
            if (antPathMatcher.match(permission.getPermissionUrl(),requestUrl)){
                List<Role> roles = permission.getRoles();
                String[] strings = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    strings[i] =roles.get(i).getRoleName();
                }
                return SecurityConfig.createList(strings);
            }

        }

        return SecurityConfig.createList("ROLE_LOGIN");

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
