package com.app.todo.service;

import com.app.todo.dto.TaskResponse;
import com.app.todo.entity.Task;
import com.app.todo.repository.Repository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class Service {

    private final Repository repository;

    public Optional<TaskResponse> updateTask(Long id, String s) {
        return repository.findById(id)
                .map(t -> {
                    t.setS(s);
                    repository.save(t);
                    return mapToTaskResponse(t);
                });

    }

    public void addTask(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Task is not defined");
        }
        Task task = new Task();
        task.setS(s);
        repository.save(task);
    }

    public List<TaskResponse> fetchAllTasks() {
        return repository.findAll().stream().map(this :: mapToTaskResponse).collect(Collectors.toList());
    }

    public Optional<TaskResponse> fetchTask(Long id) {
        return repository.findById(id).map(this::mapToTaskResponse);
    }

    public TaskResponse mapToTaskResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setS(task.getS());

        return response;
    }

    public boolean deleteATask(Long id) {
        if (repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
