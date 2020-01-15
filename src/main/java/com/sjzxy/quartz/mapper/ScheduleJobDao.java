package com.sjzxy.quartz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sjzxy.quartz.entity.ScheduleJobEntity;



/**
 * 定时任务
 * 
 * @author asiainfo
 *  
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
public interface ScheduleJobDao<T> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
	
	List<ScheduleJobEntity> queryList(Map<String, Object> map);
	
	public ScheduleJobEntity queryEntityByParams(Map<String, Object> map);
	void save(T t);
	
	void save(Map<String, Object> map);
	
	void saveBatch(List<T> list);
	
	int update(T t);
	
	int update(Map<String, Object> map);
	
	int delete(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	T queryObject(Object id);
	
	List<T> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);

	int queryTotal();
}
