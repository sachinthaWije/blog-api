package dev.sachi.blogsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String id;

    @NotBlank(message = "Content is required")
    private String content;
    private String userId;
    private String postId;
    private Date createdAt;
    private Date updatedAt;
}
