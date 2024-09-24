package dev.sachi.blogsystem.controller;

import dev.sachi.blogsystem.dto.PostDTO;
import dev.sachi.blogsystem.dto.UserDTO;
import dev.sachi.blogsystem.service.PostService;
import dev.sachi.blogsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @RequestHeader("Authorization") String jwt
                                              ) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        PostDTO createdPost = postService.createPost(postDTO, loggedUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<PostDTO> posts = postService.getAllPosts(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable String postId) {
        PostDTO post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/user/")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        List<PostDTO> posts = postService.findPostsByUserId(loggedUser.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @Valid
            @PathVariable String postId,
            @RequestBody PostDTO post,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        PostDTO updatedPost = postService.updatePost(postId, post, loggedUser.getId());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId,
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        postService.deletePost(postId, loggedUser.getId());
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
