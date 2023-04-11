package com.example.licenta.service;

import com.example.licenta.model.User;
import com.example.licenta.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Resource
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User findUser(String user, String password) {
        return userRepository.findUserByEmailAndPassword(user,password);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
