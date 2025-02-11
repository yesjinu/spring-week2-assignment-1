package com.codesoom.assignment.domain.controllers;

import com.codesoom.assignment.domain.dtos.TaskDTO;
import com.codesoom.assignment.domain.entity.Task;
import com.codesoom.assignment.domain.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = this.taskService.getAllTask();

        return tasks.stream()
                .map(TaskDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskDTO getTask(@PathVariable Long id) {
        Task task = this.taskService.getTask(id);

        return TaskDTO.from(task);
    }

    @PostMapping()
    public TaskDTO registerTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = Task.from(taskDTO);
        Task registeredTask = this.taskService.register(task);

        return TaskDTO.from(registeredTask);
    }

    @PutMapping("{id}")
    @PatchMapping("{id}")
    public TaskDTO modifyTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        Task modifiedTask = this.taskService.modifyTask(id, taskDTO.getTitle());

        return TaskDTO.from(modifiedTask);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
