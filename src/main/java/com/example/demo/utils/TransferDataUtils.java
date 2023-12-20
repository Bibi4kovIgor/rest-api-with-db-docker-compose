package com.example.demo.utils;

import com.example.demo.entities.User;
import com.example.demo.dto.UserDto;

import java.util.UUID;

public class TransferDataUtils {

    public static User userDtoToEntity(UserDto userDto) {
        return new User(UUID.fromString(userDto.id()), userDto.name(), userDto.email());
    }

    public static UserDto userEntityToDto(User user) {
        return new UserDto(user.getId().toString(), user.getName(), user.getEmail());
    }
}
