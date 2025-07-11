package com.example.filterPoc.request;

import com.example.filterPoc.util.UserType;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;
    private String password;
    private UserType userType;
}
