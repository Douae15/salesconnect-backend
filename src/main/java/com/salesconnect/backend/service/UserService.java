package com.salesconnect.backend.service;

import com.salesconnect.backend.dto.UserDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    //UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getUsersForCompany();
    UserDTO updateUser(Long id, UserDTO userDTO);
   // boolean deleteUser(Long id);
}
