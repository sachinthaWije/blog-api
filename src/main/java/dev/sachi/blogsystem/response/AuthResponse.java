package dev.sachi.blogsystem.response;

import dev.sachi.blogsystem.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE userRole;
    private String userName;
}
