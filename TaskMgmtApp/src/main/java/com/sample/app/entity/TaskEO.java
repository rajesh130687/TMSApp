package com.sample.app.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
		@NamedQuery(name = "GetPendingTasks", query = "FROM TaskEO as task where task.remindDate <= current_date "
				+ " ORDER BY task.dueDate, task.priority") })
@Table(name = "TASK_MASTER")
public class TaskEO {
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "PRIORITY")
	private int priority;
	@Column(name = "DUE_DATE")
	private Date dueDate;
	@Column(name = "REMIND_DATE")
	private Date remindDate;
	@Column(name = "RESOLVED_AT")
	private Timestamp resolvedAt;
	@Column(name = "CREATED_AT")
	private Timestamp createdAt;
	@Column(name = "UPDATED_AT")
	private Timestamp updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	public Timestamp getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(Timestamp resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}