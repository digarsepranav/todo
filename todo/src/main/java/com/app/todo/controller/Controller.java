package com.app.todo.controller;

import com.app.todo.dto.TaskResponse;
import com.app.todo.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class Controller {
    private final Service service;

    // create a task : will be a string and have to get it from the user
    @PostMapping("/addTask")
    public ResponseEntity<String> createTask(@@RequestBody String s) {
        service.addTask(s);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task is created! ");
    }

    // get the tasks :
    @GetMapping("/fetchTask")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(service.fetchAllTasks());
    }

    @GetMapping("/fetchTask/{id}")
    // fetch a task by id :
    public ResponseEntity<TaskResponse> getATask(@PathVariable Long id) {
        return service.fetchTask(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PutMapping("/fetchTask/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,@RequestBody String s) {
        return service.updateTask(id, s).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (!service.deleteATask(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
