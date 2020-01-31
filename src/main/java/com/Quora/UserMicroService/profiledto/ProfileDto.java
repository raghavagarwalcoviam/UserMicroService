package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
public class ProfileDto {
    String userId;
    String name;
    String emailAddress;
    String password;
    String channel;
    String role;
    String phoneNumber;
    String imageURL;
    String profile;
}
