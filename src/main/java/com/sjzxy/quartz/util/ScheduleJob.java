package com.sjzxy.quartz.util;



import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.sjzxy.quartz.entity.ScheduleJobEntity;
import com.sjzxy.quartz.entity.ScheduleJobLogEntity;
import com.sjzxy.quartz.mapper.ScheduleJobLogDao;



/**
 * 定时任务
 * 
 * @author asiainfo
 *  
 * @date 2016年11月30日 下午12:44:21
 */
@Component
public class ScheduleJob extends QuartzJobBean {
	private static Logger logger = LoggerFactory.getLogger(ScheduleJob.class);
//	private ExecutorService service = Executors.newSingleThreadExecutor();
//	private ExecutorService service = Executors.newFixedThreadPool(5);



    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {

		ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);
		//获取spring bean
//        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
        
        //数据库保存执行记录
        ScheduleJobLogEntity log = new ScheduleJobLogEntity(scheduleJob);

        //任务开始时间
        long startTime = System.currentTimeMillis();
        //执行结果返回信息
		Object result = null;
		try {
            //执行任务
			logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId()+"     ExecutorService:" + ScheduleUtils.service);

			Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
			Method method = null;
			String params = scheduleJob.getParams();
			if(params != null){
				method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName(), String.class);
			}else{
				method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName());
                log.setParams("");
            }
			logger.info("Thread id:"+Thread.currentThread().getId()+ ", Thread name:"+Thread.currentThread().getName());
			ReflectionUtils.makeAccessible(method);
			if(params != null){
				result = method.invoke(target, params);
			}else{
				result = method.invoke(target);
			}
			if (result == null) {
				log.setError("");
			} else {
				log.setError(JSON.toJSONString(result));
			}
			long times = System.currentTimeMillis() - startTime;
			logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒   " + "  执行结果：" + "\n");
			log.setTimes((int)times);
			log.setStatus(0);
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			ScheduleJobLogDao scheduleJobLogDao = SpringContextUtils.getBean("scheduleJobLogDao", ScheduleJobLogDao.class);
			scheduleJobLogDao.save(log);
		}
    }
}
