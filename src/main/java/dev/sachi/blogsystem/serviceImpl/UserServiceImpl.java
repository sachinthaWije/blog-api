package dev.sachi.blogsystem.serviceImpl;

import dev.sachi.blogsystem.config.JwtProvider;
import dev.sachi.blogsystem.dto.UserDTO;
import dev.sachi.blogsystem.exception.ResourceNotFoundException;
import dev.sachi.blogsystem.model.User;
import dev.sachi.blogsystem.repository.UserRepository;
import dev.sachi.blogsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtProvider jwtProvider;



    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDTO findUserByJwtToken(String jwt) throws Exception {
        String username = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + username);
        }

        return modelMapper.map(user, UserDTO.class);
    }

}
