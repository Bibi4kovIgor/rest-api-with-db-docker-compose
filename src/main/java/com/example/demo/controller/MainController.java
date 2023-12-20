package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    UserService userService;


    @GetMapping(path = {"/", "{name}"})
    public String Greetings(@PathVariable(name = "name", required = false) String name){
        return Objects.isNull(name) ? "Hello World!" : String.format("Hello, %s", name);
    }

    @PostMapping(path = "/add-user")
    public void addUser() {
        userService.saveUser(new UserDto(UUID.randomUUID().toString(), "Ihor", "some@email.com"));
    }

    @GetMapping(path = "/get-all-users")
    public String getAllUsers(){
        return userService.getAllUsers().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    @GetMapping(path = "/get-user-by-name/{name}")
    public String getUserByName(@PathVariable(name = "name") String name){
        String user = String.valueOf(userService.getUserByName(name));
        return user.isEmpty() ? "User was not found" : user;
    }

}
