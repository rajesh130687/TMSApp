package com.sample.app.service;

import java.util.List;

import com.sample.app.exception.TMSCustomException;
import com.sample.app.model.TaskDTO;

public interface TaskService {

	public List<TaskDTO> getAllTasks() throws TMSCustomException;

	public void updateTaskDetails(TaskDTO taskDTO) throws TMSCustomException;

	public void generateTask() throws TMSCustomException;
}