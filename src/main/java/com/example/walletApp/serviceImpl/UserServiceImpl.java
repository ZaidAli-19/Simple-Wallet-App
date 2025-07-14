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
import java.util.UUID;

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
        user.setUuid(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());
        user.setIsDeleted(false);
        userRepository.save(user);

        UserRoleService serviceByType = serviceFactory.getServiceByType(request.getUserType());
        if (serviceByType != null) {
            return serviceByType.getUserInfo();
        } else {
            throw new ResourceNotFoundException("UserType is not valid!");
        }
    }

    public List<UserResponse> getAll() {
        List<User> user = userRepository.findAll()
                .stream()
                .filter(user1 -> !user1.getIsDeleted()).toList();
        return user.stream().map(ResponseMapper::toUserResponse).toList();
    }

    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Please provide a valid user id."));
        if(user.getIsDeleted()){
            throw new UserNotFoundException("This user is already deleted.");
        }
        return ResponseMapper.toUserResponse(user);
    }

    public String updateUser(String id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Invalid user Id! Please provide a valid user Id."));
        if (user.getIsDeleted()){
            throw new UserNotFoundException("This user is already deleted!.");
        }
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
        if (user.getIsDeleted()){
            throw new UserNotFoundException("This user has already been deleted.");
        }
        if (walletRepository.findByUser_Uuid(id)
                .stream().filter(user1-> !user1.getIsDeleted())
                .toList().isEmpty()){
            user.setIsDeleted(true);
            userRepository.save(user);
            return "User deleted successfully.";
        }
        throw new ResourceNotFoundException("Cannot delete user because the user has active wallets.");
    }

}
