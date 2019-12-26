package com.young.pojo;

import com.young.elasticsearch.annotation.ESField;
import com.young.elasticsearch.annotation.ESID;
import com.young.elasticsearch.annotation.ESMetaData;

import java.io.Serializable;
/**
 * 日志对象
 * @author YoungLu
 */
@ESMetaData(indexName = "powercloud_log_20191226",printLog = true)
public class LogSample implements Serializable {
	@ESField(exist = false)
	private static final long serialVersionUID = 1L;
	@ESID
	private String id;//id=AV2B5b09iUaJFUrUPZux
	private String app_name;//应用名称
	private String log_time;//日志记录时间，格式yyyy-MM-dd HH:mm:ss
	private String server_uuid;//服务器uuid
	private String group_name;//微服务组名
	private String module_name;//对应的模块名称
	private String log_outline;//日志概要
	private String log_detail;//日志详细记录
	private String log_type;////日志类别，大写: DEBUG, ERROR, INFO, WARN
	private String biz_uuid;//业务识别号
	private String host_ip;//服务器ip地址
	
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getLog_outline() {
		return log_outline;
	}
	public void setLog_outline(String log_outline) {
		this.log_outline = log_outline;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getServer_uuid() {
		return server_uuid;
	}
	public void setServer_uuid(String server_uuid) {
		this.server_uuid = server_uuid;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public String getLog_detail() {
		return log_detail;
	}
	public void setLog_detail(String log_detail) {
		this.log_detail = log_detail;
	}
	public String getLog_type() {
		return log_type;
	}
	public void setLog_type(String log_type) {
		this.log_type = log_type;
	}
	public String getBiz_uuid() {
		return biz_uuid;
	}
	public void setBiz_uuid(String biz_uuid) {
		this.biz_uuid = biz_uuid;
	}
	public String getHost_ip() {
		return host_ip;
	}
	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}

	@Override
	public String toString() {
		return "LogSample{" +
				"id='" + id + '\'' +
				", app_name='" + app_name + '\'' +
				", log_time='" + log_time + '\'' +
				", server_uuid='" + server_uuid + '\'' +
				", group_name='" + group_name + '\'' +
				", module_name='" + module_name + '\'' +
				", log_outline='" + log_outline + '\'' +
				", log_detail='" + log_detail + '\'' +
				", log_type='" + log_type + '\'' +
				", biz_uuid='" + biz_uuid + '\'' +
				", host_ip='" + host_ip + '\'' +
				'}';
	}

}
