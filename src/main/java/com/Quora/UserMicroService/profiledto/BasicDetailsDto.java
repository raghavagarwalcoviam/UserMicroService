package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasicDetailsDto {
    String userId;
    String name;
    String emailAddress;
    String password;
    String channel;
    String role;
    List<InterestDto> category;
}
