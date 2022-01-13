package com.itzyq.redislikes.model.dto;

import com.itzyq.redislikes.config.LikedStatusEnum;
import lombok.Data;

/**
 * @className: UserLikeDTO
 * @description: 用户点赞
 * @author: zyq
 * @date: 2021/2/26 9:33
 * @Version: 1.0
 */
@Data
public class UserLikeDetailDTO {

    /**
     * 被点赞的用户id
     */
    private String likedUserId;

    /**
     * 点赞的用户id
     */
    private String likedPostId;

    /**
     * 点赞状态，0取消，1点赞
     */
    private Integer status;
}
