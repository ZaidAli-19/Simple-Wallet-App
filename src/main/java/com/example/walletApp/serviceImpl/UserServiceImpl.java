package com.example.walletApp.serviceImpl;

import com.example.walletApp.exceptionHandling.ResourceNotFoundException;
import com.example.walletApp.exceptionHandling.TransactionNotFoundException;
import com.example.walletApp.exceptionHandling.UserNotFoundException;
import com.example.walletApp.factoryPattern.UserRoleService;
import com.example.walletApp.factoryPattern.UserServiceFactory;
import com.example.walletApp.model.User;
import com.example.walletApp.repository.UserRepository;
import com.example.walletApp.repository.WalletRepository;
import com.example.walletApp.request.UserRequest;
import com.example.walletApp.response.UserResponse;
import com.example.walletApp.service.UserService;
import com.example.walletApp.util.ResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserServiceFactory serviceFactory;
    private final WalletRepository  walletRepository;

    public UserServiceImpl(UserRepository repository, UserServiceFactory serviceFactory, WalletRepository walletRepository) {
        this.userRepository = repository;
        this.serviceFactory = serviceFactory;
        this.walletRepository = walletRepository;
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

    @Override
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("Invalid user Id."));
        if (walletRepository.findByUser_Uuid(id).isEmpty()){
            userRepository.delete(user);
            return "User deleted successfully.";
        }
        throw new ResourceNotFoundException("Cannot delete user because the user has active wallets.");
    }

}
