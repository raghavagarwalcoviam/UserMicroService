package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class FollowDto {
    String followerUserId;
    String followedUserId;
    String followerName;
    String followedName;
    String followedIdType;
    List<String> moderatorId;
    Boolean isApproved;
}
