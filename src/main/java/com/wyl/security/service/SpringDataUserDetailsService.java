package com.wyl.security.service;

import com.wyl.security.dao.UserDao;
import com.wyl.security.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description SpringDataUserDetailsService 自定义UserDetailsService
 * @Author YiLong Wu
 * @Date 2020/2/5 21:45
 * @Version 1.0.0
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public SpringDataUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userDao.getUserByUsername(username);
        if(user == null) {
            return null;
        }

        // 把列表变成数组
        List<String> permission = userDao.getPermissionByUserId(user.getId());
        String[] permissinoArr = new String[permission.size()];
        permission.toArray(permissinoArr);
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(permissinoArr).build();
        return userDetails;
    }
}
