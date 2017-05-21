package com.sample.app.dao;

import java.util.List;

import com.sample.app.entity.TaskEO;
import com.sample.app.exception.TMSCustomException;
import com.sample.app.model.TaskDTO;

public interface TaskDao {
	public List<TaskEO> getAllTasks() throws TMSCustomException;

	public void updateTaskDetails(TaskDTO taskDTO) throws TMSCustomException;

	public int generateTaskID() throws TMSCustomException;

	public void addTaskDetails(TaskEO taskEO);
}
