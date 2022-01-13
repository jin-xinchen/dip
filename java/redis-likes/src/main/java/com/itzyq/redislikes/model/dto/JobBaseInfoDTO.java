package com.itzyq.redislikes.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
@Data
public class JobBaseInfoDTO implements Serializable {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务地点位置经纬度(14位小数)-逗号隔开
     */
    private String jobLocation;

    /**
     * 距离
     **/
    private Double distance;

    /**
     * 距离
     **/
    private List<Integer> idList;

}
