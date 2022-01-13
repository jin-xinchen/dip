package com.itcheetah.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itcheetah.securityjwt.entity.SysUserInfo;
import com.itcheetah.securityjwt.entity.model.UserPojo;
import org.apache.ibatis.annotations.Param;

public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {

    //通过用户名密码获取用户信息
    SysUserInfo getUserByLogin(@Param("username") String username);

    //通过用户名获取角色
    String getRole(@Param("username") String username);

    UserPojo queryByUserName(@Param("userName") String userName);
}

