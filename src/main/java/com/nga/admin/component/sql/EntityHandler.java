package com.nga.admin.component.sql;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class EntityHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject json = (JSONObject) JSON.toJSON(authentication);
        JSONObject userAuthentication = (JSONObject) JSON.toJSON(json.get("userAuthentication"));
        Integer userId = -1;
        if (userAuthentication != null){
            JSONObject user = userAuthentication.getJSONObject("principal");
            userId = user.getInteger("userId");
        }
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtCreateMan", userId, metaObject);
        this.setFieldValByName("gmtModifiedMan", userId, metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        this.setFieldValByName("isDeleted", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject json = (JSONObject) JSON.toJSON(authentication);
        JSONObject userAuthentication = (JSONObject) JSON.toJSON(json.get("userAuthentication"));
        Integer userId = -1;
        if (userAuthentication != null){
            JSONObject user = userAuthentication.getJSONObject("principal");
            userId = user.getInteger("userId");
        }
        this.setFieldValByName("gmtModifiedMan", userId, metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}