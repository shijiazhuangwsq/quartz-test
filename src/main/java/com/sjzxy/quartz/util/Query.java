package com.sjzxy.quartz.util;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author asiainfo
 *  
 * @date 2017-03-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页数
	private Integer pageNum;
	//当前记录数
	private Integer pageSize;
	
	
    public Query(Map<String, Object> params){
        //分页参数的验证 
    	//如果页面上的分页参数不符合则按默认值来
    	Integer pageNum = new Integer(1);
        Integer pageSize = new Integer(10);  
        
        Object oPageNum = params.get("pageNum");
        Object oPageSize = params.get("pageSize");
    	if(oPageNum != null) {
    		try {
				Integer sPageNum = Integer.parseInt(oPageNum.toString());
				if(sPageNum.compareTo(0) >=0) {
					pageNum = sPageNum;
				}
			} catch (NumberFormatException e) {
			}	
    	}
    	
    	if(oPageSize != null) {
    		try {
    			Integer sPageSize = Integer.parseInt(oPageSize.toString());
    			if(sPageSize.compareTo(0) > 0) {
    				pageSize = sPageSize;
    			}
			} catch (NumberFormatException e) {
				pageSize = 15;
			}	
    	}
    	this.pageNum = pageNum;
    	this.pageSize = pageSize;
    	params.put("pageNum", pageNum);
    	params.put("pageSize", pageSize);
    
    	this.putAll(params);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
       /* String sidx = params.get("sidx").toString();
        String order = params.get("order").toString();
        this.put("sidx", SQLFilter.sqlInject(sidx));
        this.put("order", SQLFilter.sqlInject(order));*/
    }


	public Integer getPageNum() {
		return pageNum;
	}


	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
