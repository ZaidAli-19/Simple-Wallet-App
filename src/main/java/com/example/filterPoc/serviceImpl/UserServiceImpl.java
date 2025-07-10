package com.example.filterPoc.serviceImpl;

import com.example.filterPoc.factoryPattern.UserRoleService;
import com.example.filterPoc.factoryPattern.UserServiceFactory;
import com.example.filterPoc.model.User;
import com.example.filterPoc.repository.UserRepository;
import com.example.filterPoc.request.UserRequest;
import com.example.filterPoc.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserServiceFactory serviceFactory;

    public UserServiceImpl(UserRepository repository, UserServiceFactory serviceFactory) {
        this.userRepository = repository;
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
        userRepository.save(user);

        UserRoleService serviceByType = serviceFactory.getServiceByType(request.getUserType());
        if (serviceByType != null) {
            return serviceByType.getUserInfo();
        } else {
            throw new RuntimeException("user is not valid");
        }
    }

    public List<User> getAll() {
        List<User> user = userRepository.findAll();
        return user;
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("error"));
    }

    public void updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("invalid id"));
        if(request.getFirstName()!=null){
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName()!=null){
            user.setLastName(request.getLastName());
        }
        if(request.getPassword()!=null){
            user.setPassword(request.getPassword());
        }
        if(request.getPhoneNumber()!=null){
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getEmail()!=null){
            user.setEmail(request.getEmail());
        }
        userRepository.save(user);
    }
}
