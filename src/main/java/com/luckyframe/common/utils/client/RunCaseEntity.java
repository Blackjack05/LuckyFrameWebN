package com.luckyframe.common.utils.client;

import java.io.Serializable;

/**
 * 注意对象必须继承Serializable
 * @author seagull
 */
public class RunCaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String projectname;
    private String taskid;
    private String testCaseExternalId;
    private String version;
    private String loadpath;
    
	public String getLoadpath() {
		return loadpath;
	}
	public void setLoadpath(String loadpath) {
		this.loadpath = loadpath;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTestCaseExternalId() {
		return testCaseExternalId;
	}
	public void setTestCaseExternalId(String testCaseExternalId) {
		this.testCaseExternalId = testCaseExternalId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

}
