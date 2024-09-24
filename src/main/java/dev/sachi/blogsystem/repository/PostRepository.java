package dev.sachi.blogsystem.repository;

import dev.sachi.blogsystem.dto.PostDTO;
import dev.sachi.blogsystem.model.POST_STATUS;
import dev.sachi.blogsystem.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post,String> {

    List<PostDTO> findAllByUserId(String userId);
    boolean existsByTitleAndUserId(String title, String userId);
    Page<PostDTO> findAllByPostStatus(POST_STATUS status, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Post> findByPostStatus(POST_STATUS status, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCaseAndPostStatus(String title, POST_STATUS status, Pageable pageable);


}
