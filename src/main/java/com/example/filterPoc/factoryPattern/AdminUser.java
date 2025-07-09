package com.example.filterPoc.factoryPattern;

import com.example.filterPoc.util.UserType;
import org.springframework.stereotype.Service;

@Service
public class AdminUser implements UserRoleService{
    @Override
    public String getUserInfo() {
        return "This is a admin.";
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
