package com.sample.app.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.constants.TaskConstants;
import com.sample.app.exception.TMSCustomException;
import com.sample.app.model.TaskDTO;
import com.sample.app.model.TaskInfo;
import com.sample.app.service.TaskService;

@RestController
@RequestMapping("/")
public class TaskController {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	TaskService taskService;

	@RequestMapping(value = "/getTaskList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskInfo> listAllUsers() {
		List<TaskDTO> taskList = null;
		TaskInfo taskInfo = new TaskInfo();

		try {
			taskList = taskService.getAllTasks();

			if (CollectionUtils.isEmpty(taskList)) {
				taskInfo.setStatus(TaskConstants.ERROR);
				taskInfo.setErrorDesc(TaskConstants.NO_REC_FOUND);
			} else {
				taskInfo.setStatus(TaskConstants.SUCCESS);
				taskInfo.setTaskList(taskList);
			}
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
			taskInfo.setStatus(TaskConstants.EXCEPTION);
			taskInfo.setErrorDesc(tce.getMessage());
			return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			taskInfo.setStatus(TaskConstants.EXCEPTION);
			taskInfo.setErrorDesc(e.getMessage());
			return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updTaskDtl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskInfo> updateTaskDetails(@RequestBody TaskDTO taskDTO) {
		TaskInfo taskInfo = new TaskInfo();

		try {
			taskService.updateTaskDetails(taskDTO);
			taskInfo.setStatus(TaskConstants.SUCCESS);
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
			taskInfo.setStatus(TaskConstants.EXCEPTION);
			taskInfo.setErrorDesc(tce.getMessage());
			return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			taskInfo.setStatus(TaskConstants.EXCEPTION);
			taskInfo.setErrorDesc(e.getMessage());
			return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<TaskInfo>(taskInfo, HttpStatus.OK);
	}
}