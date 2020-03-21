package com.luckyframe.common.utils.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.luckyframe.common.constant.ClientConstants;
import com.luckyframe.common.utils.StringUtils;
import com.luckyframe.project.testexecution.taskExecute.domain.TaskExecute;
import com.luckyframe.project.testexecution.taskExecute.service.ITaskExecuteService;
import com.luckyframe.project.testexecution.taskScheduling.domain.TaskScheduling;
import com.luckyframe.project.testexecution.taskScheduling.service.ITaskSchedulingService;

/**
 * 远程唤起客户端工具类
 * @author Seagull
 * @date 2019年4月13日
 */
public class ClientRun {
	
	@Autowired
	private ITaskSchedulingService taskSchedulingService;
	
	@Autowired
	private ITaskExecuteService taskExecuteService;
	
	public static ClientRun clientRun;

	@PostConstruct
	public void init() {
		clientRun = this;
	}

	public String toRunTask(String schedulingId,int autoType){
		String result="启动失败！";
		TaskScheduling taskScheduling = taskSchedulingService.selectTaskSchedulingById(Integer.valueOf(schedulingId));
		
		TaskExecute taskExecute = new TaskExecute();
		String taskName = "【"+taskScheduling.getSchedulingName()+ "】_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		taskExecute.setTaskName(taskName);
		taskExecute.setSchedulingId(taskScheduling.getSchedulingId());
		taskExecute.setProjectId(taskScheduling.getProjectId());
		taskExecute.setTaskStatus(0);
		
		taskExecuteService.insertTaskExecute(taskExecute);
		try{			
    		RunTaskEntity runTaskEntity = new RunTaskEntity();
    		runTaskEntity.setSchedulingName(taskScheduling.getSchedulingName());
    		runTaskEntity.setTaskId(taskExecute.getTaskId().toString());   		
    		if(StringUtils.isEmpty(taskScheduling.getClientDriverPath())){
    			runTaskEntity.setLoadPath("/TestDriven");
    		}else{
    			runTaskEntity.setLoadPath(taskScheduling.getClientDriverPath());
    		}

			result=HttpRequest.httpClientPost("http://"+taskScheduling.getClient().getClientIp()+":"+ClientConstants.CLIENT_MONITOR_PORT+"/runtask", JSONObject.toJSONString(runTaskEntity),3000);
    		System.out.println(result);
    		return result;
		}catch (Exception e) {
			//执行失败时,写入任务表记录为执行失败
			taskExecute.setTaskStatus(0);
			taskExecuteService.updateTaskExecute(taskExecute);
			return result;
		}
			
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
