package dev.sachi.blogsystem.serviceImpl;

import dev.sachi.blogsystem.dto.CommentDTO;
import dev.sachi.blogsystem.dto.PostDTO;
import dev.sachi.blogsystem.exception.ResourceNotFoundException;
import dev.sachi.blogsystem.exception.UnauthorizedException;
import dev.sachi.blogsystem.model.Comment;
import dev.sachi.blogsystem.model.POST_STATUS;
import dev.sachi.blogsystem.model.Post;
import dev.sachi.blogsystem.repository.CommentRepository;
import dev.sachi.blogsystem.repository.PostRepository;
import dev.sachi.blogsystem.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, String userId) {

        Post post = modelMapper.map(postDTO, Post.class);
        if (postRepository.existsByTitleAndUserId(post.getTitle(), userId)) {
            throw new IllegalArgumentException("Post title already exists for this user.");
        }

        post.setUserId(userId);
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());
        post.setPostStatus(POST_STATUS.PUBLISHED);

        return modelMapper.map(postRepository.save(post), PostDTO.class);
    }

    @Override
    public List<PostDTO> findAllByUserId(String userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getAllPosts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> posts = postRepository.findAllByPostStatus(POST_STATUS.PUBLISHED, pageable);

        return posts.stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);


            List<CommentDTO> comments = commentRepository.findByPostId(post.getId())
                    .stream()
                    .map(comment -> modelMapper.map(comment, CommentDTO.class))
                    .collect(Collectors.toList());

            postDTO.setComments(comments);

            return postDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(String postId) {

        return modelMapper.map(
                postRepository.findById(postId).orElse(null), PostDTO.class);
    }

    @Override
    public List<PostDTO> findPostsByUserId(String userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public PostDTO updatePost(String postId, PostDTO postDTO, String userId) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (!existingPostOptional.isPresent()) {
            // If post does not exist, throw a custom exception
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        Post existingPost = existingPostOptional.get();
        if (!existingPost.getUserId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to update this post.");
        }
        if (postRepository.existsByTitleAndUserId(postDTO.getTitle(), userId)) {
            throw new IllegalArgumentException("Post title already exists for this user.");
        }

        if (postDTO.getTitle() != null) {
            existingPost.setTitle(postDTO.getTitle());
        }
        if (postDTO.getContent() != null) {
            existingPost.setContent(postDTO.getContent());
        }
        if (postDTO.getPostStatus() != null) {
            existingPost.setPostStatus(postDTO.getPostStatus());
        }
        existingPost.setUpdatedAt(new Date());
        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostDTO.class);

    }

    @Override
    public void deletePost(String postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this post.");
        }

        postRepository.delete(post);
    }

    @Override
    public Page<PostDTO> searchAndFilterPosts(String title, POST_STATUS status,Pageable pageable) {
        Page<Post> posts;

        if (title != null && status != null) {
            posts = postRepository.findByTitleContainingIgnoreCaseAndPostStatus(title, status, pageable);
        } else if (title != null) {
            posts = postRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (status != null) {
            posts = postRepository.findByPostStatus(status, pageable);
        } else {
            posts = postRepository.findAll(pageable);
        }

        return posts.map(post -> modelMapper.map(post, PostDTO.class));

    }
}
