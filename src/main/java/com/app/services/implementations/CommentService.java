package com.app.services.implementations;

import org.springframework.stereotype.Service;

import com.app.domain.post.Answer;
import com.app.domain.post.Comment;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.CommentRepositoryImp;
import com.app.services.interfaces.ICommentService;



@Service
public class CommentService implements ICommentService{
    
  private UserService userService;
  private PostService postService;
  private AnswerService answerService;
  private CommentRepositoryImp commentRepository;

  public CommentService (UserService userService, PostService postService, AnswerService answerService, CommentRepositoryImp commentRepository) {
    this.userService = userService;
    this.postService = postService;
    this.answerService = answerService;
    this.commentRepository = commentRepository;

  }


  @Override
  public Comment postComment(Long postId, Long userId, String content) {
      try {
          Post post = postService.getPostById(postId);
          Entry entry = post.getEntry();
          ForoUser user = userService.getUserbyId(userId);


          Comment comment = new Comment();
          comment.setUser(user);
          comment.setEntry(entry);
          comment.setContent(content);

          return commentRepository.save(comment);
      } catch (Exception e) {
          throw new CreationException("No se pudo crear el comentario al post");
      }
  }

  @Override
  public Comment answerComment(Long answerId, Long userId, String content) {
      try {
          Answer answer = answerService.getAnswerById(answerId);
          Entry entry = answer.getEntry();
          ForoUser user = userService.getUserbyId(userId);


          Comment comment = new Comment();
          comment.setEntry(entry);
          comment.setUser(user);
          comment.setContent(content);

          return commentRepository.save(comment);
      } catch (Exception e) {
          throw new CreationException("No se pudo crear el comentario al answer");
      }
  }


}
