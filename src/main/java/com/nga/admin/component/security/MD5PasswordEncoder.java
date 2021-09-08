package com.nga.admin.component.security;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Slf4j
public class MD5PasswordEncoder implements PasswordEncoder {
    private Digester digester;
    public MD5PasswordEncoder(){
        digester = new Digester(DigestAlgorithm.MD5);
    }
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("原密码不能为空");
        } else {
            return digester.digestHex(rawPassword.toString());
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("原密码不能为空");
        } else if (encodedPassword != null && encodedPassword.length() != 0) {
            log.debug(rawPassword.toString());
            return this.encode(rawPassword.toString()).equals(encodedPassword);
        } else {
            log.warn("加密了一个空的密码");
            return false;
        }
    }
}
