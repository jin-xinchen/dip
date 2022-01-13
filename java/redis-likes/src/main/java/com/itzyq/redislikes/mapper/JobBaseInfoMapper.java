package com.itzyq.redislikes.mapper;

import com.itzyq.redislikes.model.dto.JobBaseInfoDTO;
import com.itzyq.redislikes.model.entity.JobBaseInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itzyq.redislikes.model.vo.JobBaseInfoVO;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 Mapper 接口
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
public interface JobBaseInfoMapper extends BaseMapper<JobBaseInfo> {

    List<JobBaseInfoVO> selectJobList(JobBaseInfoDTO jobBaseInfoDTO);
}
