package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExtraDetailsDto {
    String userId;
    String phoneNumber;
    String imageURL;
    String profileType;
    Boolean isOrganization;
    List<String> moderatorId;
}
