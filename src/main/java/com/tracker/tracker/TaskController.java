package com.tracker.tracker;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TaskController {
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	@GetMapping("/tasks")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	
	@PostMapping("/tasks")
	@CrossOrigin(origins = "http://localhost:4200")
	@Transactional
	public Task createTask(@RequestBody Task task) {
		if (task.getParentTask().getParentId() != null) {
		Optional<ParentTask> parentTask = parentTaskRepository.findById(task.getParentTask().getParentId());
		if(parentTask.isPresent()) {
		
			task.setParentTask(parentTask.get());
			task.setActive(true);
			
		}else {
			task = persistParentTask(task);
		}
		}else {
			task = persistParentTask(task);
		}
		
			return taskRepository.save(task);
		
		
		
	}
	
	private Task persistParentTask(Task task) {
		ParentTask tempTask = new ParentTask();
		if(task.getParentTask().getParentId() != null) {
		Optional<Task> tempParentTask = taskRepository.findById(task.getParentTask().getParentId());
		tempTask.setParentTask(tempParentTask.get().getTask());
		}else {
			tempTask.setParentTask(task.getTask());
		}
		tempTask = parentTaskRepository.save(tempTask);
		task.setParentTask(tempTask);
		task.setActive(true);
		return task;
	}
	
	@GetMapping("/tasks/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Optional<Task> getTaskById(@PathVariable(value="id") Long taskId) {
		return taskRepository.findById(taskId);
	}
	
	@PutMapping("/tasks/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Task updateTask(@PathVariable(value="id") Long taskId, @RequestBody Task updatedTask ){
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isPresent()) {
			Task currentTask = task.get();
			currentTask.setTask(updatedTask.getTask());
			currentTask.setStartDate(updatedTask.getStartDate());
			currentTask.setEndDate(updatedTask.getEndDate());
			currentTask.setPriority(updatedTask.getPriority());
			Optional<Task> parentTask = taskRepository.findById(currentTask.getParentTask().getParentId());
			if(parentTask.isPresent()) {
				currentTask.getParentTask().setParentTask(parentTask.get().getTask());
			}else {
			currentTask.setParentTask(updatedTask.getParentTask());
			}
			
			updatedTask = taskRepository.save(currentTask);
		}
		return updatedTask;
	}
	
	@DeleteMapping("/tasks/{id}")
	public Task deleteTask(@PathVariable(value="id") Long taskId, @RequestBody Task updatedTask ){
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isPresent()) {
			taskRepository.delete(updatedTask);
		}
		return updatedTask;
	}
	
	

}
