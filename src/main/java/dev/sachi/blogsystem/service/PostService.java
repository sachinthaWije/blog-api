package dev.sachi.blogsystem.service;

import dev.sachi.blogsystem.dto.PostDTO;
import dev.sachi.blogsystem.model.POST_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO post, String userId) ;
    List<PostDTO> findAllByUserId(String userId);
    List<PostDTO> getAllPosts(int page, int size);
    PostDTO getPostById(String postId);
    List<PostDTO> findPostsByUserId(String userId);
    PostDTO updatePost(String postId, PostDTO postDTO, String userId);
    void deletePost(String postId, String userId);
    Page<PostDTO> searchAndFilterPosts(String title, POST_STATUS status, Pageable pageable);
}
