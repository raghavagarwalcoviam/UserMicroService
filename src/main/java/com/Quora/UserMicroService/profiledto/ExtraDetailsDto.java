package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExtraDetailsDto {
    String userId;
    List<InterestDto> category;
    String channel;
    String profile;
    String role;
}
