package com.sample.app.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sample.app.constants.TaskConstants;
import com.sample.app.entity.TaskEO;
import com.sample.app.exception.TMSCustomException;
import com.sample.app.model.TaskDTO;
import com.sample.app.util.ConverterUtils;

@Transactional
@Repository
public class TaskDaoImpl implements TaskDao {
	
	public static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<TaskEO> getAllTasks() throws TMSCustomException {
		List<TaskEO> taskList = null;
		try {
			Query taskQuery = entityManager.createNamedQuery(TaskConstants.GET_PENDING_TASKS);
	
			taskList = (List<TaskEO>) taskQuery.getResultList();
		} catch (HibernateException he) {
			logger.error("Exception Occured ", he);
			throw new TMSCustomException(he.getMessage());
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}
		return taskList;
	}

	private TaskEO getTaskById(int id) {
		return entityManager.find(TaskEO.class, id);
	}

	public void updateTaskDetails(TaskDTO taskDTO) throws TMSCustomException {
		try {
			TaskEO taskEO = getTaskById(taskDTO.getId());
			ConverterUtils.setTaskEODtl(taskDTO, taskEO);
	
			entityManager.flush();
		} catch (HibernateException he) {
			logger.error("Exception Occured ", he);
			throw new TMSCustomException(he.getMessage());
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}
	}

	public int generateTaskID() throws TMSCustomException {
		BigInteger taskId;
		try {
			Query taskQuery = entityManager.createNativeQuery(TaskConstants.GET_TASK_ID_QUERY);
			taskId = (BigInteger) taskQuery.getSingleResult();
		} catch (HibernateException he) {
			logger.error("Exception Occured ", he);
			throw new TMSCustomException(he.getMessage());
		} catch (Exception e) {
			logger.error("Exception Occured ", e);
			throw new TMSCustomException(e.getMessage());
		}
		
		return taskId.intValue();
	}

	public void addTaskDetails(TaskEO taskEO) {
		entityManager.persist(taskEO);
	}

}
