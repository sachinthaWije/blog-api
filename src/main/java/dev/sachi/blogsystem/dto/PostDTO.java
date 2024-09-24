package dev.sachi.blogsystem.dto;

import dev.sachi.blogsystem.model.POST_STATUS;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;
    private String userId;
    private POST_STATUS postStatus=POST_STATUS.PUBLISHED;
    private Date createdAt;
    private Date updatedAt;
    private List<CommentDTO> comments;
}
