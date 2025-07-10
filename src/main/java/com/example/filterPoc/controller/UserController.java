package com.example.filterPoc.controller;

import com.example.filterPoc.model.User;
import com.example.filterPoc.request.UserRequest;
import com.example.filterPoc.service.UserService;
import com.example.filterPoc.serviceImpl.UserServiceImpl;
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
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/getById/{id}")
    public User getById(@PathVariable String id){
        return userService.getById(id);
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable String id,@RequestBody UserRequest request){
        userService.updateUser(id,request);
    }
}
