package com.app.controller.user;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@PreAuthorize("denyAll()")
@RequestMapping("/api/user")
public class UserController {

  @GetMapping("/role")
  @PreAuthorize("hasRole('ADMIN')")
  public String onlyAdmins () {
    return "Hi you has role ADMIN";
  }

  @GetMapping("/helloworld")
  @PreAuthorize("permitAll()")
  public String helloWorld () {
    return "Hello world !";
  }
}  