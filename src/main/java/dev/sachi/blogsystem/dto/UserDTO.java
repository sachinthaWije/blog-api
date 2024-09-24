package dev.sachi.blogsystem.dto;

import dev.sachi.blogsystem.model.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String username;
    private String password;

    @NotBlank(message = "Role is required")
    private USER_ROLE role=USER_ROLE.ROLE_USER;
}
