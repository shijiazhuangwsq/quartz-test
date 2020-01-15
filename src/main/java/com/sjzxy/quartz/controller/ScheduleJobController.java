package com.sjzxy.quartz.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzxy.quartz.entity.ScheduleJobEntity;
import com.sjzxy.quartz.service.ScheduleJobService;
import com.sjzxy.quartz.util.Constant;
import com.sjzxy.quartz.util.Query;
import com.sjzxy.quartz.util.R;
import com.sjzxy.quartz.util.ValidatorUtils;

import net.sf.json.JSONObject;



/**
 * 定时任务
 *
 * @author asiainfo
 *
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")

public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	public R list(@RequestBody Map<String, Object> params, HttpServletRequest request){
		//查询列表数据
		Query query = new Query(params);
		//Page<Object> page = PageHelper.startPage(request);
		PageHelper.startPage(Integer.parseInt(params.get("pageNum").toString()), 
				Integer.parseInt(params.get("pageSize").toString()));
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
		PageInfo<ScheduleJobEntity> pageUtil = new PageInfo<ScheduleJobEntity>(jobList);
//		PageHelper.startPage(staticSurfaceVo.getPageNum(), staticSurfaceVo.getPageSize());
//		List<StaticSurfaceVo> list=staticSurfaceService.queryStaticSurface();
//		PageInfo<StaticSurfaceVo> page = PageInfo.of(list);
		return R.ok().put("data", pageUtil.getList()).put("total", pageUtil.getTotal());
	}

	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);

		return R.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 */
	//@SysLog("保存定时任务")
	@RequestMapping("/save")
	public R save(@RequestBody Map<String,Object> map){
		
		// 获取添加定时任务的内容
		ScheduleJobEntity scheduleJob =  getAddEntityContent(map);
		//判断是否为重复任务
		if(scheduleJobService.queryEntityByParams(map) == null) {
			// 添加定时任务
			
			  ValidatorUtils.validateEntity(scheduleJob);
			  scheduleJobService.save(scheduleJob);
		}else {
			
			// 更新定时任务
			
			Long jobId = scheduleJobService.queryEntityByParams(map).getJobId();
			scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
			scheduleJob.setJobId(jobId);
		
			scheduleJobService.update(scheduleJob);
		}
		
		return R.ok();
	}

	/**
	 * 
	 * @Description: 根据前端传来的定时任务生成正确实体类
	 * @author wsq
	 * @date 2019-12-20 04:33:50
	 */
	@SuppressWarnings("all")
	public ScheduleJobEntity getAddEntityContent( Map<String,Object> map) {
		  ScheduleJobEntity scheduleJob = new ScheduleJobEntity(); // 确定bean 
		  String beanName = "***Service"; 
		  // 确定方法 
		  String method = "***Method";
		  
		  //处理日期 
		  String implementTime = map.get("implementTime").toString(); 
		  String day = implementTime.substring(0, 3); 
		  String now = implementTime.substring(3,implementTime.length()); 
		  List<Object> list = Arrays.asList(now.split(":")); 
		  Collections.reverse(list); 
		  String changeimplementTime = list.stream().map(String ::valueOf).collect(Collectors.joining(" ")); 
		  // "30 10 1 20 * ?"每月20号1点10分30秒触发任务 
		  String cronExpression = changeimplementTime + " " + day+"* ?"; 
		  
		  //String cronExpression = "0/6 * * * * ?"; 
		  Map <String,Object> newMap = new HashMap<String, Object>();
		  newMap.putAll(map);
		  newMap.remove("implementTime");
		  //map转化为String 
		  JSONObject jsonObject=JSONObject.fromObject(newMap);
		  String params = jsonObject.toString();
		  scheduleJob.setParams(params);
		  scheduleJob.setBeanName(beanName);
		  scheduleJob.setMethodName(method);
		  scheduleJob.setCronExpression(cronExpression);
		  map.put("params", params);
		  return scheduleJob;
	}
	/**
	 * 修改定时任务
	 */
	//@SysLog("修改定时任务")
	@RequestMapping("/update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob){
		
		ValidatorUtils.validateEntity(scheduleJob);

		scheduleJobService.update(scheduleJob);

		return R.ok();
	}

	/**
	 * 删除定时任务
	 */
	//@SysLog("删除定时任务")
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);

		return R.ok();
	}

	/**
	 * 立即执行任务
	 */
	//@SysLog("立即执行任务")
	@RequestMapping("/run")
	//@RequiresPermissions("sys:schedule:run")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);

		return R.ok();
	}

	/**
	 * 暂停定时任务
	 */
	//@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	//@RequiresPermissions("sys:schedule:pause")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);

		return R.ok();
	}

	
	/**
	 * 恢复定时任务
	 */
	//@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	//@RequiresPermissions("sys:schedule:resume")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);

		return R.ok();
	}

}
