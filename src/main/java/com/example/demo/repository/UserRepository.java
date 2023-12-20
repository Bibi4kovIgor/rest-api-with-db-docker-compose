package com.example.demo.repository;

import com.example.demo.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Transactional
    @NonNull
    User save(@NonNull User user);

    @NonNull
    List<User>findAll();

    User findUserByName(String name); // SELECT * FROM User a WHERE a.name = 'name'
}
