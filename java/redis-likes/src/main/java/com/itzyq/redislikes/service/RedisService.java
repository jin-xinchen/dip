package com.itzyq.redislikes.service;

import com.itzyq.redislikes.model.dto.UserLikCountDTO;
import com.itzyq.redislikes.model.entity.UserLikeDetail;

import java.util.List;

/**
 * @className: RedisService
 * @description: redis服务类
 * @author: zyq
 * @date: 2021/2/25 13:46
 * @Version: 1.0
 */
public interface RedisService {

    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    void decrementLikedCount(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLikeDetail> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<UserLikCountDTO> getLikedCountFromRedis();
}
