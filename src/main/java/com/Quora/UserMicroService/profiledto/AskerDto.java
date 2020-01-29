package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskerDto {
    String askerId;
    String taggedProfileId;
    String taggedProfileType;
    String category;
}
