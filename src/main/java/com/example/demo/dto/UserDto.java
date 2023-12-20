package com.example.demo.dto;

import org.springframework.lang.NonNull;

public record UserDto(@NonNull String id, @NonNull String name, String email){}
