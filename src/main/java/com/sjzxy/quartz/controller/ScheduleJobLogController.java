package com.sjzxy.quartz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjzxy.quartz.entity.ScheduleJobLogEntity;
import com.sjzxy.quartz.service.ScheduleJobLogService;
import com.sjzxy.quartz.util.Query;
import com.sjzxy.quartz.util.R;



/**
 * 定时任务日志
 * 
 * @author asiainfo
 *  
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	//@RequiresPermissions("sys:schedule:log")
	public R list(@RequestBody Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
//		List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
//		int total = scheduleJobLogService.queryTotal(query);
//		
//		PageUtils pageUtil = new PageUtils(jobList, total, query.getPageSize(), query.getPageNum());
		PageHelper.startPage(Integer.parseInt(params.get("pageNum").toString()), 
				Integer.parseInt(params.get("pageSize").toString()));
		List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
		PageInfo<ScheduleJobLogEntity> pageUtil = new PageInfo<ScheduleJobLogEntity>(jobList);
		return R.ok().put("data", pageUtil.getList()).put("total", pageUtil.getTotal());
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		
		return R.ok().put("log", log);
	}
}
