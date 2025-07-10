package com.example.filterPoc.service;

import com.example.filterPoc.model.User;
import com.example.filterPoc.request.UserRequest;

import java.util.List;

public interface UserService {
    String createUser(UserRequest request);

    List<User> getAll();

    User getById(String id);

    void updateUser(String id, UserRequest request);
}
