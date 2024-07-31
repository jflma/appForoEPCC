package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.controller.dto.response.TokenResponse;
import com.app.domain.user.ForoUser;
import com.app.services.interfaces.IUserService;

@RestController
@CrossOrigin("http://localhost:3000/")
@PreAuthorize("permitAll")
@RequestMapping("/auth")
public class AuthController {

  private IUserService userService;

  public AuthController(IUserService userService){
    this.userService=userService;
  }

  @GetMapping("/hello")
  public String test () {
    return "Endpoint /auth run!";
  }

  @PostMapping("/signup")
  public ForoUser signupUser (@RequestBody SignupFieldsDTO signupFields) {
    return userService.registerUser(signupFields);
  }

  @PostMapping("/signin")
  public TokenResponse loginUser (@RequestBody LoginRequestDTO loginRequest) {
    return userService.loginUser(loginRequest);
  }

}
