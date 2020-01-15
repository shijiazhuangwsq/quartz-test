package com.sjzxy.quartz.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: R
 * @description: 返回数据
 * @author: chennn
 * @createTime: 2019年2月17日 下午4:36:58
 */
public class R{
	
	public static final int STATE_SUCCESS = 0;
	public static final int STATE_ERROR = 500;
	private static final long serialVersionUID = 1L;

	private String msg;
	private int code;
	private HashMap<String, Object> data;

	public R() {
		this.code = STATE_SUCCESS;
		this.msg = "操作成功";
		this.data = new HashMap<>();
	}

	private R(String msg) {
		this();
		this.msg = msg;
	}

	private R(int code, String msg) {
		this(msg);
		this.code = code;
	}
	
	public static R ok() {
		return new R();
	}
	public static R ok(String msg) {
		return new R(msg);
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.data.putAll(map);
		return r;
	}

	public static R error() {
		return new R(STATE_ERROR, "未知异常，请稍后再试");
	}

	public static R error(String msg) {
		return new R(STATE_ERROR,msg);
	}
	/**
	 * R信息填写
	 * @param code
	 * @param msg
	 * @return
	 */
	public static R error(int code, String msg) {
		return new R(code,msg);
	}
	
	
	public R put(String key, Object value) {
		this.data.put(key, value);
		return this;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}
}
