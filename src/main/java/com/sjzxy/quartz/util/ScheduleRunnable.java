package com.sjzxy.quartz.util;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;



/**
 * 执行定时任务
 * 
 * @author asiainfo
 *  
 * @date 2016年11月30日 下午12:49:33
 */
public class ScheduleRunnable implements Callable {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;

		if(params != null){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public Object call() {
		try {
			logger.info("Thread id:{}, Thread name:{}",Thread.currentThread().getId(),Thread.currentThread().getName());
			ReflectionUtils.makeAccessible(method);
			if(params != null){
				return method.invoke(target, params);
			}else{
				return method.invoke(target);
			}
		}catch (Exception e) {
			throw new RRException("执行定时任务失败", e);
		}
	}

}
