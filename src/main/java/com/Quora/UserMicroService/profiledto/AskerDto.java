package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskerDto {
    private String askerId;
    private String askerUserName;
    private String taggedProfileId;
    private String taggedProfileType;
    private String taggedProfileName;
    private String category;
    private Boolean isApproved;
}
