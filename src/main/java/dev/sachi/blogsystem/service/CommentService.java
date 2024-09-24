package dev.sachi.blogsystem.service;

import dev.sachi.blogsystem.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(String postId, CommentDTO commentDTO, String userId);
    CommentDTO updateComment(String commentId, CommentDTO commentDTO, String userId);
    void deleteComment(String commentId, String userId);
    List<CommentDTO> getCommentsByPostId(String postId);
}
