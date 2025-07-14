package com.example.walletApp.controller;

import com.example.walletApp.request.UserRequest;
import com.example.walletApp.response.UserResponse;
import com.example.walletApp.service.UserService;
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
    public List<UserResponse> getAll(){
        return userService.getAll();
    }

    @GetMapping("/getInfoById/{id}")
    public UserResponse getById(@PathVariable String id){
        return userService.getById(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable String id,@RequestBody UserRequest request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        return userService.deleteUser(id);
    }
    }

