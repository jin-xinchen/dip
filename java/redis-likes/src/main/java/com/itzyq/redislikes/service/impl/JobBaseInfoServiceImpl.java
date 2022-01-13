package com.itzyq.redislikes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itzyq.redislikes.model.dto.JobBaseInfoDTO;
import com.itzyq.redislikes.model.entity.JobBaseInfo;
import com.itzyq.redislikes.mapper.JobBaseInfoMapper;
import com.itzyq.redislikes.model.vo.JobBaseInfoVO;
import com.itzyq.redislikes.service.JobBaseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itzyq.redislikes.util.StringUtils;
import io.lettuce.core.api.async.RedisGeoAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 工作任务详情基础信息表 服务实现类
 * </p>
 *
 * @author zyq
 * @since 2021-03-24
 */
@Service
public class JobBaseInfoServiceImpl extends ServiceImpl<JobBaseInfoMapper, JobBaseInfo> implements JobBaseInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JobBaseInfoMapper jobBaseInfoMapper;

    /**
     * @author zyq
     * @description 初始话redis中的任务数据
     * @date 2021/3/24 13:56
     * @Param []
     * @return void
     **/
    @PostConstruct
    public void init(){
        redisTemplate.delete("company-task");
        List<JobBaseInfo> jobBaseInfoList = jobBaseInfoMapper.selectList(Wrappers.emptyWrapper());
        jobBaseInfoList.stream().forEach(item->{
            String jobLocation = item.getJobLocation();
            if(StrUtil.isNotEmpty(jobLocation)){
                String[] split = jobLocation.split(",");
                if(split.length==2){
                    Point point = new Point(Double.parseDouble(split[0]),Double.parseDouble(split[1]));
                    redisTemplate.opsForGeo().add("company-task",point,item.getJobId());
                }
            }
        });
    }

    @Override
    public List<JobBaseInfoVO> selectJobList(JobBaseInfoDTO jobBaseInfoDTO) {
        String jobLocation = jobBaseInfoDTO.getJobLocation();
        Double distance = jobBaseInfoDTO.getDistance();
        List<Integer> idList = new ArrayList<>();
        List<GeoResult<RedisGeoCommands.GeoLocation<Integer>>> contentList = new ArrayList<>();
        if(StringUtils.isNotNull(jobLocation) && StringUtils.isNotNull(distance)){
            String[] split = jobLocation.split(",");
            if(split.length==2){
                //Point(经度, 纬度) Distance(距离量, 距离单位)
                Circle circle = new Circle(new Point(Double.parseDouble(split[0]),Double.parseDouble(split[1])),
                        new Distance(distance, Metrics.KILOMETERS));
                RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                        .newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);

                GeoResults<RedisGeoCommands.GeoLocation<Integer>> geoResults = redisTemplate.opsForGeo().radius("company-task", circle, args);//params: key, Circle, GeoRadiusCommandArgs
                contentList = geoResults.getContent();
                if(contentList.size()>0){
                    idList = contentList.stream().map(content -> content.getContent().getName()).collect(Collectors.toList());

//                    contentList.stream().forEach(item->{
//                        RedisGeoCommands.GeoLocation<Integer> content = item.getContent();
//                        idList.add(content.getName());
//                    });
                }
            }
        }
        jobBaseInfoDTO.setIdList(idList);
        List<JobBaseInfoVO> jobList = jobBaseInfoMapper.selectJobList(jobBaseInfoDTO);
        if(contentList.size()>0){
            List<GeoResult<RedisGeoCommands.GeoLocation<Integer>>> finalContentList = contentList;
            List<JobBaseInfoVO> collect = jobList.stream().map(job ->
                    finalContentList.stream().filter(content -> content.getContent().getName() == job.getJobId())
                            .findFirst()
                            .map(content -> {
                                //科学计数法转为小数用BigDecimal,保留两位小数，四舍五入
                                job.setDistance(new BigDecimal(content.getDistance().getValue()).setScale(2, RoundingMode.HALF_UP));
                                job.setDistanceUnit(content.getDistance().getUnit());
                                return job;
                            }).orElse(null)).collect(Collectors.toList());
            return collect;
        }else{
            return jobList;
        }
    }


}
