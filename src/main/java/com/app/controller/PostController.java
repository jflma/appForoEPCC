package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.dto.CreatePostFieldsDTO;
import com.app.domain.post.Post;
import com.app.services.implementations.PostService;
import com.app.services.interfaces.IPostService;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/post")
public class PostController {

  private IPostService postService;

  public PostController (PostService postService) {
    this.postService = postService;

  }

  @PostMapping("/createPost")
  public Post createPost(@RequestBody CreatePostFieldsDTO fields) {
    return postService.createPost(fields.id(),fields.title(),fields.content());
  }

}
