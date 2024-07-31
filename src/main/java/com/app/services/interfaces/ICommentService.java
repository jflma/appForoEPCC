package com.app.services.interfaces;

import com.app.domain.post.Comment;

public interface ICommentService {
  public Comment postComment(Long postId, Long userId, String content);
  public Comment answerComment(Long answerId, Long userId, String content);
}
