package com.tracker.tracker;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="task")
public class Task implements Serializable {

    @Id
    @Column(name="task_id")
    private Long taskId;
    @ManyToOne
    @JoinColumn(name="parent_id")
	private ParentTask parentTask;
	private String task;
	 @Temporal(TemporalType.DATE)
	 @Column(name="start_date")
	private Date startDate;
	 @Temporal(TemporalType.DATE)
	 @Column(name="end_date")
	private Date endDate;
	private Long priority;
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public ParentTask getParentTask() {
		return parentTask;
	}
	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	
	
	
	
	
}
