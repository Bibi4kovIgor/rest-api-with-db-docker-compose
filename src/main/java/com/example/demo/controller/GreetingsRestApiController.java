package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GreetingsRestApiController {

    @GetMapping(path = {"/", "{name}"})
    public String Greetings(@PathVariable(name = "name", required = false) String name){
        return Objects.isNull(name) ? "Hello World!" : String.format("Hello, %s", name);
    }
}
