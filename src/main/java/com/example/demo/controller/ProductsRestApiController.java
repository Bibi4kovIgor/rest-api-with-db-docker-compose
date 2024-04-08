package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsRestApiController {

  @Autowired
  UserService userService;
  @GetMapping
  public String getAllUsers(){
    return userService.getAllUsers().stream()
        .map(String::valueOf)
        .collect(Collectors.joining(", "));
  }

  @PostMapping(path = "/add-user")
  public void addUser() {
    userService.saveUser(new UserDto(UUID.randomUUID().toString(), "Ihor", "some@email.com"));
  }


  @GetMapping(path = "/get-user-by-name/{name}")
  public String getUserByName(@PathVariable(name = "name") String name){
    String user = String.valueOf(userService.getUserByName(name));
    return user.isEmpty() ? "User was not found" : user;
  }
}
