package com.example.filterPoc.serviceImpl;

import com.example.filterPoc.exceptionHandling.TransactionNotFoundException;
import com.example.filterPoc.exceptionHandling.UserNotFoundException;
import com.example.filterPoc.factoryPattern.UserRoleService;
import com.example.filterPoc.factoryPattern.UserServiceFactory;
import com.example.filterPoc.model.User;
import com.example.filterPoc.repository.UserRepository;
import com.example.filterPoc.request.UserRequest;
import com.example.filterPoc.response.UserResponse;
import com.example.filterPoc.service.UserService;
import com.example.filterPoc.util.ResponseMapper;
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
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());
        userRepository.save(user);

        UserRoleService serviceByType = serviceFactory.getServiceByType(request.getUserType());
        if (serviceByType != null) {
            return serviceByType.getUserInfo();
        } else {
            throw new TransactionNotFoundException("UserType is not valid!");
        }
    }

    public List<UserResponse> getAll() {
        List<User> user = userRepository.findAll();
        return user.stream().map(ResponseMapper::toUserResponse).toList();
    }

    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Please provide a valid user id."));
        return ResponseMapper.toUserResponse(user);
    }

    public String updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Invalid user Id! Please provide a valid user Id."));
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
        return "User updated successfully";
    }

}
