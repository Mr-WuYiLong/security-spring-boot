package com.wyl.security.model;

import lombok.Data;

/**
 * @Description PermissionDto
 * @Author YiLong Wu
 * @Date 2020/2/6 18:32
 * @Version 1.0.0
 */
@Data
public class PermissionDto {
    private Integer id;
    private String code;
    private String description;
    private String url;
}
