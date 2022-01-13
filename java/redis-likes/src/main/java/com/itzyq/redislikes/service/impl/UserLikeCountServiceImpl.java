package com.itzyq.redislikes.service.impl;

import com.itzyq.redislikes.model.entity.UserLikeCount;
import com.itzyq.redislikes.mapper.UserLikeCountMapper;
import com.itzyq.redislikes.service.UserLikeCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyq
 * @since 2021-02-26
 */
@Service
public class UserLikeCountServiceImpl extends ServiceImpl<UserLikeCountMapper, UserLikeCount> implements UserLikeCountService {

}
