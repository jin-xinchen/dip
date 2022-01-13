package com.itzyq.redislikes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 工作任务详情基础信息表
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JobBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

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


}
