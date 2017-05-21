package com.sample.app.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dao.TaskDao;
import com.sample.app.entity.TaskEO;
import com.sample.app.exception.TMSCustomException;
import com.sample.app.model.TaskDTO;
import com.sample.app.util.ConverterUtils;

@Service
public class TaskServiceImpl implements TaskService {

	public static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskDao taskDao;

	public List<TaskDTO> getAllTasks() throws TMSCustomException {
		List<TaskDTO> taskList = null;
		List<TaskEO> taskEOList = null;
		try {
			taskEOList = taskDao.getAllTasks();
			if (CollectionUtils.isNotEmpty(taskEOList)) {
				taskList = ConverterUtils.getTaskModelList(taskEOList);
			}
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
			throw tce;
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}

		return taskList;
	}

	public void updateTaskDetails(TaskDTO taskDTO) throws TMSCustomException {
		try {
			taskDao.updateTaskDetails(taskDTO);
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
			throw tce;
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}
		
	}

	public void generateTask() throws TMSCustomException {
		TaskEO taskEO;
		try {
			int taskId = taskDao.generateTaskID();
			taskEO = ConverterUtils.createTaskEODtl(taskId);
			taskDao.addTaskDetails(taskEO);
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
			throw tce;
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}
	}
}
