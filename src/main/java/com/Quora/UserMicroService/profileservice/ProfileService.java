package com.Quora.UserMicroService.profileservice;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Profile addProfile(Profile privateProfile);
    Optional<Profile> getProfile(String userId);
    AskerResponseDto getFollower(AskerDto askerDto);
    AskerResponseDto getApprovedFollower(AskerDto askerDto);
    AnswerResponseDto getAnswerFollower(AnswerDto askerDto);
    AnswerResponseDto getAnswerApprovedFollower(AnswerDto askerDto);
    ResponseEntity<String> addFollower(String followerId,String userId) throws JsonProcessingException;
    ResponseEntity<String> addFollowing(String followingId,String userId);
    ResponseEntity<String> addPoints(int points,String userId) throws JsonProcessingException;
    ResponseEntity<String> addModerator(String moderatorId,String userId);
    ResponseEntity<String> addCategory(CategoryDto categoryDto);
    ResponseEntity<List<UserDetailDto>> getFollowers(String userId);
    ResponseEntity<List<UserDetailDto>> getFollowing(String userId);
    ResponseEntity<List<UserDetailDto>> getModerators(String userId);
    List<InterestDto> getCategory(String userId);
    ResponseEntity<String> removeFollower(String followerId,String userId);
    ResponseEntity<String> removeFollowing(String followingId,String userId);
    ResponseEntity<String> removeModerator(String moderatorId,String userId);
    ResponseEntity<String> removeCategory(String categoryId,String userId);
    String isFollowing(String followingId,String userId);
}
