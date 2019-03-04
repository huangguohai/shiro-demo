package com.imooc.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 只含用户名和密码的登录信息
 */
@Data
@AllArgsConstructor
public class LoginInfo {
    private String username;
    private String password;
}
