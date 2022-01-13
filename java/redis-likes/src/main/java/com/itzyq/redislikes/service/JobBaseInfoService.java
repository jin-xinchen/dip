package com.itzyq.redislikes.service;

import com.itzyq.redislikes.model.dto.JobBaseInfoDTO;
import com.itzyq.redislikes.model.entity.JobBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itzyq.redislikes.model.vo.JobBaseInfoVO;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 服务类
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
public interface JobBaseInfoService extends IService<JobBaseInfo> {

    List<JobBaseInfoVO> selectJobList(JobBaseInfoDTO jobBaseInfoDTO);
}
