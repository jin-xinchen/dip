package com.itcheetah.securityjwt.service.impl;

import com.itcheetah.securityjwt.entity.model.UserPojo;
import com.itcheetah.securityjwt.mapper.SysUserInfoMapper;
import com.itcheetah.securityjwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: UserInfoServiceImpl
 * @Description: TODO
 * @Date: 2020/9/25
 * @Author: cheetah
 * @Version: 1.0
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private SysUserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo user = userInfoMapper.queryByUserName(username);
        return user;
    }
}
