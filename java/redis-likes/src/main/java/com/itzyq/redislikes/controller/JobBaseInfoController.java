package com.itzyq.redislikes.controller;


import com.itzyq.redislikes.model.domain.AjaxResult;
import com.itzyq.redislikes.model.dto.JobBaseInfoDTO;
import com.itzyq.redislikes.model.entity.JobBaseInfo;
import com.itzyq.redislikes.model.vo.JobBaseInfoVO;
import com.itzyq.redislikes.service.JobBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 工作任务详情基础信息表 前端控制器
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/jobBaseInfo")
public class JobBaseInfoController extends BaseController{

    @Autowired
    private JobBaseInfoService jobBaseInfoService;

    @PostMapping("/list")
    public AjaxResult list(@RequestBody JobBaseInfoDTO jobBaseInfoDTO) {
        startPage();
        List<JobBaseInfoVO> list = jobBaseInfoService.selectJobList(jobBaseInfoDTO);
        return AjaxResult.success(getDataTable(list));
    }
}
