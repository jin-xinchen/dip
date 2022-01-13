package com.itzyq.redislikes.service;

import com.itzyq.redislikes.model.entity.UserLikeDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户点赞表 服务类
 * </p>
 *
 * @author zyq
 * @since 2021-02-25
 */
public interface UserLikeDetailService extends IService<UserLikeDetail> {

    void transLikedFromRedis2DB();

    void transLikedCountFromRedis2DB();
}
