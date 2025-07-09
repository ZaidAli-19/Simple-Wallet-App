package com.example.filterPoc.service;

import com.example.filterPoc.factoryPattern.UserRoleService;
import com.example.filterPoc.factoryPattern.UserServiceFactory;
import com.example.filterPoc.model.User;
import com.example.filterPoc.repository.UserRepository;
import com.example.filterPoc.request.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserServiceFactory serviceFactory;

    public UserService(UserRepository repository, UserServiceFactory serviceFactory) {
        this.repository = repository;
        this.serviceFactory = serviceFactory;
    }

    public String createUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());
        repository.save(user);

        UserRoleService serviceByType = serviceFactory.getServiceByType(request.getUserType());
        if (serviceByType != null) {
            return serviceByType.getUserInfo();
        } else {
            throw new RuntimeException("user is not valid");
        }
    }

    public List<User> getAll() {
        List<User> user = repository.findAll();
        return user;
    }

    public User getById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("error"));
    }
}
