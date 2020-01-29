package com.Quora.UserMicroService.profiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerResponseDto {
    List<String> askerFollowerList;
    List<String> categoryFollowerList;
    List<String> tagFollowerList;
    List<String> moderatorList;
    List<String> answerFollowerList;
}
