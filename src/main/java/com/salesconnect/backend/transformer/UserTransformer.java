package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.*;
import com.salesconnect.backend.entity.*;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer extends Transformer<User, UserDTO>{
    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null)
            return null;
        else {
            Transformer<Activity, ActivityDTO> activityTransformer = new ActivityTransformer();
            Transformer<Task, TaskDTO> tasksTransformer = new TaskTransformer();
            User user = new User();
            user.setUserId(userDTO.getUserId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setRole(userDTO.getRole());
            user.setPassword(userDTO.getPassword());
            if (userDTO.getCompanyId() != null) {
                Company company = new Company();
                company.setCompanyId(userDTO.getCompanyId());
                user.setCompany(company);
            } else {
                user.setCompany(null);
            }
            user.setActivities(activityTransformer.toEntityList(userDTO.getActivitiesDTO()));
            user.setTasks(tasksTransformer.toEntityList(userDTO.getTasksDTO()));
            return user;
        }
    }

    @Override
    public UserDTO toDTO(User user) {
        if (user == null)
            return null;
        else {
            Transformer<Activity, ActivityDTO> activityTransformer = new ActivityTransformer();
            Transformer<Task, TaskDTO> tasksTransformer = new TaskTransformer();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setRole(user.getRole());
            userDTO.setPassword(user.getPassword());
            if (user.getCompany() != null) {
                userDTO.setCompanyId(user.getCompany().getCompanyId());
            }
            userDTO.setActivitiesDTO(activityTransformer.toDTOList(user.getActivities()));
            userDTO.setTasksDTO(tasksTransformer.toDTOList(user.getTasks()));
            return userDTO;
        }
    }
}
