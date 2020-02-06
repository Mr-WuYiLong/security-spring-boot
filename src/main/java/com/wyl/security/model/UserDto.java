package com.wyl.security.model;

import lombok.Data;

/**
 * @Description User
 * @Author YiLong Wu
 * @Date 2020/2/6 12:07
 * @Version 1.0.0
 */
@Data
public class UserDto {

    private Integer id;
    private String username;
    private String password;
    private Integer age;
}
