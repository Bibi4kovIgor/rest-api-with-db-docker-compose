package com.example.demo.service;

import com.example.demo.utils.TransferDataUtils;
import com.example.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public boolean saveUser(UserDto userDto) {
        return Objects.isNull(userRepository.save(TransferDataUtils.userDtoToEntity(userDto)));
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(TransferDataUtils::userEntityToDto)
                .toList();
    }

    public UserDto getUserByName(String name){
        return Optional.ofNullable(userRepository.findUserByName(name))
                .map(TransferDataUtils::userEntityToDto)
                .orElse(null);
    }

}
