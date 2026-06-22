package com.example.tasktracker.service;

import com.example.tasktracker.dto.UserDTOs;
import com.example.tasktracker.model.User;
import com.example.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDTOs.Response createUser(UserDTOs.Create dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());
        User saved = userRepository.saveAndFlush(user);
        return new UserDTOs.Response(saved.getId(), saved.getEmail(), saved.getName());
    }
}
