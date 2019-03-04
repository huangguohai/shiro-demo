package com.imooc.vm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 权限
 */
@Data
@AllArgsConstructor
public class Permission {
    private int id;
    private String permission;

}
