package com.Quora.UserMicroService.profileentity;

import com.Quora.UserMicroService.profiledto.InterestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Vector;

@Getter
@Setter
@Document("Profile")
public class Profile {
    @Id
    private String userId;
    String phoneNumber;
    String imageURL;
    int points;
    String profile;
    String level;
    List<InterestDto> category;
    String name;
    String emailAddress;
    String password;
    String channel;
    String role;
}
