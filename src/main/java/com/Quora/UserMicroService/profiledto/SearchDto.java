package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private String profileValue;
    private String profileId;
    private String profileType;
    private String searchId;
    private String valueType ;
    private String questionValue;
    private String questionId;
    private Boolean questionStatus;
    private String askerProfileId;
    private String askerProfileName;
    private int numberOfLikes;
    private int numberOfDislikes;
    private String approvedAnswerId;
    private String approvedAnswer;
    private String approvedAnswererId;
    private String approvedAnswererProfile;
    private String categoryId;
    private String categoryName;

}
