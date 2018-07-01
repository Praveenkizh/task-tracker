package com.tracker.tracker;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="parent_task")
public class ParentTask implements Serializable {

    @Id
    @Column(name="parent_id")
    private Long parentId;
    @Column(name="parent_task")
    private String parentTask;
    @OneToMany(mappedBy="task")
    private List<Task> task;
    
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentTask() {
		return parentTask;
	}
	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}
	public List<Task> getTask() {
		return task;
	}
	public void setTask(List<Task> task) {
		this.task = task;
	}
	
    
	
}
