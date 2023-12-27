package com.example.demo.utils;

import com.example.demo.entities.User;
import com.example.demo.dto.UserDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferDataUtils {

    public static User userDtoToEntity(UserDto userDto) {
        return new User(UUID.fromString(userDto.id()), userDto.name(), userDto.email());
    }

    public static UserDto userEntityToDto(User user) {
        return new UserDto(user.getId().toString(), user.getName(), user.getEmail());
    }


    private interface Person{
        record MyPerson(int id, String name, String email, int age){}
        Map<Integer, MyPerson> personsData = Map.of(
                15, new MyPerson(15, "Ihor", "dfgdh4@email.com", 34),
                18, new MyPerson(18, "David", "dsfsdf3@email.com", 24));
        static void launch(){
            Map<Integer, MyPerson> map = new HashMap<>(personsData);
        }
    }

}
