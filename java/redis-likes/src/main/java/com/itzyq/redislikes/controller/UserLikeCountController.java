package com.itzyq.redislikes.controller;


import com.itzyq.redislikes.model.dto.UserLikeDetailDTO;
import com.itzyq.redislikes.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyq
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/userInfo")
public class UserLikeCountController {

    @Autowired
    private RedisService redisService;

    @PostMapping("likes")
    public void likes(@RequestBody UserLikeDetailDTO userLikeDetailDTO){
        redisService.saveLiked2Redis(userLikeDetailDTO.getLikedUserId(), userLikeDetailDTO.getLikedPostId());
        redisService.incrementLikedCount(userLikeDetailDTO.getLikedUserId());
    }

}
