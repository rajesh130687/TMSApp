package com.sample.app.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sample.app.constants.TaskConstants;
import com.sample.app.entity.TaskEO;
import com.sample.app.model.TaskDTO;

public class ConverterUtils {

	public static List<TaskDTO> getTaskModelList(List<TaskEO> taskEOList) {
		List<TaskDTO> taskList = new ArrayList<TaskDTO>();
		TaskEO taskEO = null;
		Iterator<TaskEO> taskIter = taskEOList.iterator();

		while (taskIter.hasNext()) {
			taskEO = taskIter.next();

			if (taskEO != null) {
				taskList.add(getTaskModelDtl(taskEO));
			}
		}

		return taskList;
	}

	private static TaskDTO getTaskModelDtl(TaskEO taskEO) {
		TaskDTO taskDTO = new TaskDTO();

		taskDTO.setId(taskEO.getId());
		taskDTO.setPriority(taskEO.getPriority());

		if (StringUtils.isNotEmpty(taskEO.getTitle())) {
			taskDTO.setTitle(taskEO.getTitle());
		}

		if (StringUtils.isNotEmpty(taskEO.getDescription())) {
			taskDTO.setDescription(taskEO.getDescription());
		}

		if (StringUtils.isNotEmpty(taskEO.getStatus())) {
			taskDTO.setStatus(taskEO.getStatus());
		}

		if (taskEO.getCreatedAt() != null) {
			taskDTO.setCreatedAt(getTimeStampString(taskEO.getCreatedAt()));
		}

		if (taskEO.getUpdatedAt() != null) {
			taskDTO.setUpdatedAt(getTimeStampString(taskEO.getUpdatedAt()));
		}

		if (taskEO.getResolvedAt() != null) {
			taskDTO.setResolvedAt(getTimeStampString(taskEO.getResolvedAt()));
		}

		if (taskEO.getDueDate() != null) {
			taskDTO.setDueDate(getDateString(taskEO.getDueDate()));
		}

		if (taskEO.getRemindDate() != null) {
			taskDTO.setRemindDate(getDateString(taskEO.getRemindDate()));
		}

		return taskDTO;
	}

	private static String getTimeStampString(Timestamp timestamp) {
		String timeStampStr;
		SimpleDateFormat sdf = new SimpleDateFormat(TaskConstants.DATE_FORMAT_DT_TIME);
		timeStampStr = sdf.format(timestamp);
		return timeStampStr;
	}

	private static String getDateString(Date date) {
		String dateStr;
		SimpleDateFormat sdf = new SimpleDateFormat(TaskConstants.DATE_FORMAT_DT);
		dateStr = sdf.format(date);
		return dateStr;
	}

	private static Date getDateValue(String dateStr) {
		java.util.Date date;
		Date sqlDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(TaskConstants.DATE_FORMAT_DT);
		try {
			date = sdf.parse(dateStr);

			sqlDate = new Date(date.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlDate;
	}

	public static void setTaskEODtl(TaskDTO taskDTO, TaskEO taskEO) {

		if (StringUtils.isNotEmpty(taskDTO.getTitle())) {
			taskEO.setTitle(taskDTO.getTitle());
		}

		if (StringUtils.isNotEmpty(taskDTO.getDescription())) {
			taskEO.setDescription(taskDTO.getDescription());
		}

		taskEO.setPriority(taskDTO.getPriority());

		if (StringUtils.isNotEmpty(taskDTO.getStatus())) {
			taskEO.setStatus(taskDTO.getStatus());
			if (TaskConstants.RESOLVED.equalsIgnoreCase(taskDTO.getStatus())) {
				taskEO.setResolvedAt(new Timestamp(System.currentTimeMillis()));
			}
		}

		if (StringUtils.isNotEmpty(taskDTO.getRemindDate())) {
			Date sqlDate = getDateValue(taskDTO.getRemindDate());

			if (sqlDate != null) {
				taskEO.setRemindDate(sqlDate);
			}
		}

		taskEO.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	}

	public static TaskEO createTaskEODtl(int taskId) {
		TaskEO taskEO = new TaskEO();

		taskEO.setId(taskId);
		taskEO.setTitle(TaskConstants.TASK_NO + taskId);
		taskEO.setDescription(TaskConstants.DESCRIPTION_NO + taskId);
		taskEO.setPriority((taskId % 3) + 1);
		taskEO.setStatus(TaskConstants.PENDING);
		taskEO.setDueDate(getDueDate(taskId % 10));
		taskEO.setRemindDate(getDueDate(0));
		taskEO.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		return taskEO;
	}

	private static Date getDueDate(int multFactor) {
		long timeInMs = System.currentTimeMillis();
		timeInMs += multFactor * TaskConstants.TIME_MULTIPLIER;

		return new Date(timeInMs);
	}
}
