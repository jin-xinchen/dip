package com.itzyq.redislikes.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 工作任务详情基础信息表
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
@Data
public class JobBaseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Integer jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务地点位置经纬度(14位小数)-逗号隔开
     */
    private String jobLocation;

    /**
     * 任务详细地点位置
     */
    private String jobDetailAddress;

    /**
     * 离中心点距离
     **/
    private BigDecimal distance;

    /**
     * 距离单位
     **/
    private String distanceUnit;


}
