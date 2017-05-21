package com.sample.app.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sample.app.exception.TMSCustomException;
import com.sample.app.service.TaskService;

@SpringBootApplication
@EnableScheduling
public class TaskGenerator {
	
	public static final Logger logger = LoggerFactory.getLogger(TaskGenerator.class);

	@Autowired
	TaskService taskService;

	@Scheduled(initialDelay = 1000, fixedRate = 30000)
	public void generateTask() {
		try {
			taskService.generateTask();
		} catch (TMSCustomException tce) {
			logger.error("Exception Occured ", tce);
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
		}
	}
}