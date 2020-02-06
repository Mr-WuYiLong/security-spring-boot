package com.wyl.security.dao;

import com.wyl.security.model.PermissionDto;
import com.wyl.security.model.UserDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description UserDao
 * @Author YiLong Wu
 * @Date 2020/2/6 12:08
 * @Version 1.0.0
 */
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDto getUserByUsername(String username) {
        
        String sql = "select * from user where username = ?";
        List<UserDto> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDto.class));
        if(list != null && list.size()==1) {
            return list.get(0);
        }

        return  null;
    }

    /**
     * 根据用户id，查询用户对应的权限
     * @param userId
     * @return
     */
    public List<String> getPermissionByUserId(Integer userId) {
        String sql = "SELECT * from permission WHERE id in (\n" +
                "select permission_id from role_permission WHERE role_id in (\n" +
                "SELECT role_id from user_role WHERE user_id = ?\n" +
                "\n" +
                ")\n" +
                ")";
        List<PermissionDto> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(e -> {
            permissions.add(e.getCode());
        });
        return permissions;

    }


}
