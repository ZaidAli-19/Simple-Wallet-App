package com.example.filterPoc.response;

import com.example.filterPoc.util.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserResponse {
    private String uuid;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;
    private UserType userType;
}
