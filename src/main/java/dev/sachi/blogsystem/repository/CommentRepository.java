package dev.sachi.blogsystem.repository;

import dev.sachi.blogsystem.dto.CommentDTO;
import dev.sachi.blogsystem.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<CommentDTO> findByPostId(String postId);
}
