package com.luckyframe.project.monitor.job.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.client.HttpRequest;
import com.luckyframe.common.utils.client.RunTaskEntity;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;

/**
 * 测试任务调度客户端
 * @author Seagull
 * @date 2019年3月26日
 */
@Component("runAutomationTestTask")
public class RunAutomationTestTask
{
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
    private static final Logger log = LoggerFactory.getLogger(RunAutomationTestTask.class);
	
	public static RunAutomationTestTask runAutomationTestTask;

	@PostConstruct
	public void init() {
		runAutomationTestTask = this;
	}
	
    public void runTask(String params) throws UnsupportedEncodingException, IOException,ConnectException
    {        
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(Integer.valueOf(params));
		
		if(null!=taskScheduling){
			String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String taskName ="【"+taskScheduling.getSchedulingName()+ "】_"+time;
			TaskExecute taskExecute = new TaskExecute();
			taskExecute.setSchedulingId(taskScheduling.getSchedulingId());
			taskExecute.setProjectId(taskScheduling.getProjectId());
			taskExecute.setTaskName(taskName);
			taskExecute.setTaskStatus(0);
			taskExecuteService.insertTaskExecute(taskExecute);
			
			String url= "http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runTask";
			RunTaskEntity runTaskEntity = new RunTaskEntity();
			runTaskEntity.setTaskId(taskExecute.getTaskId().toString());
			runTaskEntity.setSchedulingName(taskScheduling.getSchedulingName());
			runTaskEntity.setLoadPath(taskScheduling.getClientDriverPath());
			try {
				HttpRequest.httpClientPost(url, JSONObject.toJSONString(runTaskEntity),3000);
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				log.error("测试任务执行，远程链接客户端出现异常...");
				taskExecute.setTaskStatus(4);
				taskExecuteService.updateTaskExecute(taskExecute);
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				log.error("测试任务执行，远程链接客户端出现异常...");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				log.error("测试任务执行，远程链接客户端出现异常...");
			}
		}
    }
    
}
