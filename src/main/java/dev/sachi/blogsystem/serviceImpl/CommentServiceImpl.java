package dev.sachi.blogsystem.serviceImpl;

import dev.sachi.blogsystem.dto.CommentDTO;
import dev.sachi.blogsystem.model.Comment;
import dev.sachi.blogsystem.model.Post;
import dev.sachi.blogsystem.model.User;
import dev.sachi.blogsystem.repository.CommentRepository;
import dev.sachi.blogsystem.repository.PostRepository;
import dev.sachi.blogsystem.repository.UserRepository;
import dev.sachi.blogsystem.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(String postId, CommentDTO commentDTO, String userId) {
        System.out.println("postId "+postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPostId(post.getId());
        comment.setUserId(user.getId());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(String commentId, CommentDTO commentDTO, String userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this comment.");
        }

        comment.setContent(commentDTO.getContent());
        comment.setUpdatedAt(new Date());
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this comment.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }
}
