package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PointUpgradeNotificationDto {
    private String userId;
    private List<String> followerUserId;
    private String level;
    private String userName;
}
