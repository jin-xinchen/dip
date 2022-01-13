package com.itzyq.redislikes.model.dto;

import lombok.Data;

/**
 * @className: LikedCountDTO
 * @description: 点赞dto
 * @author: zyq
 * @date: 2021/2/25 13:50
 * @Version: 1.0
 */
@Data
public class UserLikCountDTO {

    private String key;
    private Integer value;

    public UserLikCountDTO(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
