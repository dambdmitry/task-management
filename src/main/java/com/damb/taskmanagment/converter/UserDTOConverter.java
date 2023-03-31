package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.domain.User;
import com.damb.taskmanagment.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    public User convertUserFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        //next configure user object state
        //..
        return user;
    }
}
