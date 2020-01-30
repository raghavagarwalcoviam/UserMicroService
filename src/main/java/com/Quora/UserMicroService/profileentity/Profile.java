package com.Quora.UserMicroService.profileentity;

import com.Quora.UserMicroService.profiledto.InterestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    List<String> followerId;
    List<String> followingId;
    List<String> moderatorId;
    List<InterestDto> category;
    String name;
    String emailAddress;
    String password;
    String channel;
    String role;
    Boolean isOrganization;
}
