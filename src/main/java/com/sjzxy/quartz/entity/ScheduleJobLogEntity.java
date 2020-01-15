package com.sjzxy.quartz.entity;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;


/**
 * 定时执行日志
 * 
 * @author asiainfo
 *  
 * @date 2016年12月1日 下午10:26:18
 */
public class ScheduleJobLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	private Long logId;
	
	/**
	 * 任务id
	 */
	private Long jobId;
	
	/**
	 * spring bean名称
	 */
	private String beanName;
	
	/**
	 * 方法名
	 */
	private String methodName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 任务状态    0：成功    1：失败
	 */
	private Integer status;
	
	/**
	 * 失败信息
	 */
	private String error;

	/**
	 * 任务执行主机ip
	 */
	private String hostIp;

	/**
	 * 任务执行主机名
	 */
	private String hostName;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public ScheduleJobLogEntity() {
	}
	public ScheduleJobLogEntity(ScheduleJobEntity scheduleJob) {
		this.jobId = scheduleJob.getJobId();
		this.beanName = scheduleJob.getBeanName();
		this.methodName = scheduleJob.getMethodName();
		this.params = scheduleJob.getParams();
		this.error = "";
		this.createTime = new Date();

		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip=addr.getHostAddress().toString(); //获取本机ip
		String hostName=addr.getHostName().toString(); //获取本机计算机名称
		this.hostIp = ip;
		this.hostName = hostName;
	}

	public ScheduleJobLogEntity(Long logId, Long jobId, String beanName, String methodName, String params, Integer status, String error, String hostIp, String hostName, Integer times, Date createTime) {
		this.logId = logId;
		this.jobId = jobId;
		this.beanName = beanName;
		this.methodName = methodName;
		this.params = params;
		this.status = status;
		this.error = error;
		this.hostIp = hostIp;
		this.hostName = hostName;
		this.times = times;
		this.createTime = createTime;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}
