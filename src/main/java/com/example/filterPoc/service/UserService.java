package com.example.filterPoc.service;

import com.example.filterPoc.model.User;
import com.example.filterPoc.request.UserRequest;
import com.example.filterPoc.response.UserResponse;

import java.util.List;

public interface UserService {
    String createUser(UserRequest request);

    List<UserResponse> getAll();

    UserResponse getById(String id);

    String updateUser(String id, UserRequest request);
}
