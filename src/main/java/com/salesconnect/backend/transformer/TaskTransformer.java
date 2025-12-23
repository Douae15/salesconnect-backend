package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.ActivityDTO;
import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.TaskDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Activity;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.Task;
import com.salesconnect.backend.entity.User;

public class TaskTransformer extends Transformer<Task, TaskDTO> {
    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null)
            return null;
        else {
            Task task = new Task();
            task.setTaskId(taskDTO.getTaskId());
            task.setType(taskDTO.getType());
            task.setSubject(taskDTO.getSubject());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());
            task.setDueDate(taskDTO.getDueDate());
            if (taskDTO.getUserId() != null) {
                User user = new User();
                user.setUserId(taskDTO.getUserId());
                task.setUser(user);
            } else {
                task.setUser(null);
            }
            return task;
        }
    }

    @Override
    public TaskDTO toDTO(Task task) {
        if (task == null)
            return null;
        else {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setTaskId(task.getTaskId());
            taskDTO.setType(task.getType());
            taskDTO.setSubject(task.getSubject());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setDueDate(task.getDueDate());
            taskDTO.setUserId(task.getUser().getUserId());
            return taskDTO;
        }
    }
}
