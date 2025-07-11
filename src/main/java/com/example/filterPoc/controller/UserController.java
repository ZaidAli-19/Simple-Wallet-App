package com.example.filterPoc.controller;

import com.example.filterPoc.model.User;
import com.example.filterPoc.request.UserRequest;
import com.example.filterPoc.response.UserResponse;
import com.example.filterPoc.service.UserService;
import com.example.filterPoc.serviceImpl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/getInfoById/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable String id,@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.updateUser(id,request),HttpStatus.OK);
    }

    }

