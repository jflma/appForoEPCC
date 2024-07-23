package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.domain.user.Person;
import com.app.domain.user.ForoUser;
import com.app.services.implementations.user.UserServiceImp;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserServiceImp userService;


  @CrossOrigin
  @PostMapping("/create")
  public ResponseEntity<ForoUser> createUser(@RequestBody Person person) {
    try{
      ForoUser newUser = userService.registerUser(person);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    } catch (Exception e){
      throw new RuntimeException("No se pudo registrar el usuario");
    }
  }

}  