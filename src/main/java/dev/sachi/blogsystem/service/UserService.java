package dev.sachi.blogsystem.service;


import dev.sachi.blogsystem.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    Boolean existsByUsername(String username);
    UserDTO findUserByJwtToken(String jwt) throws Exception;

}
