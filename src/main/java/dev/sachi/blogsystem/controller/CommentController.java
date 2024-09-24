package dev.sachi.blogsystem.controller;

import dev.sachi.blogsystem.dto.CommentDTO;
import dev.sachi.blogsystem.dto.UserDTO;
import dev.sachi.blogsystem.service.CommentService;
import dev.sachi.blogsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentDTO> createComment(@Valid @PathVariable String postId,
                                                    @RequestBody CommentDTO commentDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        CommentDTO createdComment = commentService.createComment(postId, commentDTO, loggedUser.getId());
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@Valid @PathVariable String commentId,
                                                    @RequestBody CommentDTO commentDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO, loggedUser.getId());
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO loggedUser= userService.findUserByJwtToken(jwt);
        commentService.deleteComment(commentId, loggedUser.getId());
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable String postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

}

