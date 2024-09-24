package dev.sachi.blogsystem.controller;


import dev.sachi.blogsystem.config.JwtProvider;
import dev.sachi.blogsystem.dto.UserDTO;
import dev.sachi.blogsystem.model.USER_ROLE;
import dev.sachi.blogsystem.model.User;
import dev.sachi.blogsystem.request.LoginRequest;
import dev.sachi.blogsystem.request.RegisterRequest;
import dev.sachi.blogsystem.response.AuthResponse;
import dev.sachi.blogsystem.service.CustomerUserDetailsService;
import dev.sachi.blogsystem.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signInHandler(@RequestBody LoginRequest req) throws Exception {

        String username = req.getUsername();
        String password = req.getPassword();
        Authentication auth = authenticate(username, password);

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login successful");
        authResponse.setUserRole(USER_ROLE.valueOf(role));
        authResponse.setUserName(username);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        UserDTO userDTO = getUserDTO(registerRequest);

        User user = modelMapper.map(userService.createUser(userDTO), User.class);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register successful");
        authResponse.setUserRole(user.getRole());
        authResponse.setUserName(user.getUsername());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterRequest registerRequest) throws IOException {
        System.out.println("register user");
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        UserDTO userDTO = getUserDTO(registerRequest);

        User user = modelMapper.map(userService.createUser(userDTO), User.class);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register successful");
        authResponse.setUserRole(user.getRole());
        authResponse.setUserName(user.getUsername());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private static UserDTO getUserDTO(RegisterRequest registerRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registerRequest.getUsername());
        userDTO.setPassword(registerRequest.getPassword());
        userDTO.setRole(registerRequest.getRole());
        userDTO.setName(registerRequest.getName());
        return userDTO;
    }

}
