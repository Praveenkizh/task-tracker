package com.tracker.tracker;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/tasks")
	public List<Task> getAllTasks(){
		return taskRepository.findAll();
	}
	
	@PostMapping("/tasks")
	public Task createTask(@RequestBody Task task) {
		return taskRepository.save(task);
		
	}
	
	@GetMapping("/tasks/{id}")
	public Optional<Task> getTaskById(@PathVariable(value="id") Long taskId) {
		return taskRepository.findById(taskId);
	}
	
	@PutMapping("/tasks/{id}")
	public Task updateTask(@PathVariable(value="id") Long taskId, @RequestBody Task updatedTask ){
		Optional<Task> task = taskRepository.findById(taskId);
		if(task.isPresent()) {
			Task currentTask = task.get();
			currentTask.setTask(updatedTask.getTask());
			currentTask.setStartDate(updatedTask.getStartDate());
			currentTask.setEndDate(updatedTask.getEndDate());
			currentTask.setPriority(updatedTask.getPriority());
			currentTask.setParentTask(updatedTask.getParentTask());
			
			updatedTask = taskRepository.save(updatedTask);
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
