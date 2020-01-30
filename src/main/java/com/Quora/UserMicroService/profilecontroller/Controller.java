package com.Quora.UserMicroService.profilecontroller;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Profile;
import com.Quora.UserMicroService.profileservice.ProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class Controller {

    @Autowired
    ProfileService profileService;

    @PostMapping("/basicDetails")
    public ResponseEntity<String> addBasicDetails(@RequestBody BasicDetailsDto basicDetailsDto){
        Profile profile = new Profile();
        BeanUtils.copyProperties(basicDetailsDto,profile);
        profile.setFollowerId(new ArrayList<String>());
        profile.setFollowingId(new ArrayList<String>());
        profile.setCategory(new ArrayList<InterestDto>());
        profile.setModeratorId(new ArrayList<String>());
        Profile createdProfile = profileService.addProfile(profile);
        //insert to kafka
        return new ResponseEntity<String>(createdProfile.getUserId(),HttpStatus.CREATED);
    }

    @PostMapping("/extraDetails")
    public ResponseEntity<String> addExtraDetails(@RequestBody ExtraDetailsDto extraDetailsDto){
        Optional<Profile> profile = profileService.getProfile(extraDetailsDto.getUserId());
        if(profile.isPresent()) {
            BeanUtils.copyProperties(extraDetailsDto, profile.get());
            Profile createdProfile = profileService.addProfile(profile.get());
            return new ResponseEntity<String>(createdProfile.getUserId(),HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("userId") String userId){
        Optional<Profile> profile = profileService.getProfile(userId);
        ProfileDto profileDto = new ProfileDto();
        if(profile.isPresent()){
            BeanUtils.copyProperties(profile.get(),profileDto);
           return new ResponseEntity<ProfileDto>(profileDto,HttpStatus.OK);
        }
        else {
          return  new ResponseEntity<ProfileDto>(profileDto,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/followerId")
    public ResponseEntity<AskerResponseDto> getFollowerId(@RequestBody AskerDto askerDto){
        return new ResponseEntity<AskerResponseDto>(profileService.getFollower(askerDto),HttpStatus.ACCEPTED);
    }

    @GetMapping("/approveFollower")
    public ResponseEntity<AskerResponseDto> getApprovedFollower(@RequestBody AskerDto askerDto){
        return new ResponseEntity<AskerResponseDto>(profileService.getApprovedFollower(askerDto),HttpStatus.ACCEPTED);
    }

    @GetMapping("/answerFollowerId")
    public ResponseEntity<AnswerResponseDto> getAnswerFollower(@RequestBody AnswerDto answerDto){
        return new ResponseEntity<AnswerResponseDto>(profileService.getAnswerFollower(answerDto),HttpStatus.ACCEPTED);
    }

    @GetMapping("/answerApproveFollower")
    public ResponseEntity<AnswerResponseDto> getAnswerApprovedFollower(@RequestBody AnswerDto answerDto){
        return new ResponseEntity<AnswerResponseDto>(profileService.getAnswerApprovedFollower(answerDto),HttpStatus.ACCEPTED);
    }

    @PutMapping("/addFollower/{followerId}/{userId}")
    public ResponseEntity<String> addFollower(@PathVariable("followerId") String followerId , @PathVariable("userId") String userId){
        return profileService.addFollower(followerId,userId);
    }

    @PutMapping("/addFollowing/{followingId}/{userId}")
    public ResponseEntity<String> addFollowing(@PathVariable("followingId") String followingId , @PathVariable("userId") String userId){
        return profileService.addFollowing(followingId,userId);
    }

    @PutMapping("/addFollower/{points}/{userId}")
    public ResponseEntity<String> addPoints(@PathVariable("points") int points , @PathVariable("userId") String userId){
        return profileService.addPoints(points,userId);
    }

    @PutMapping("/addFollower/{moderatorId}/{userId}")
    public ResponseEntity<String> addModerator(@PathVariable("moderatorId") String moderatorId , @PathVariable("userId") String userId){
        return profileService.addModerator(moderatorId,userId);
    }

    @PutMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto){
        return profileService.addCategory(categoryDto);
    }

    @GetMapping("/category/{userId}")
    public ResponseEntity<List<InterestDto>> getCategory(@PathVariable("userId") String userId){
        return profileService.getCategory(userId);
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserDetailDto>> getFollower(@PathVariable("userId") String userId){
        return profileService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserDetailDto>> getFollowing(@PathVariable("userId") String userId){
        return profileService.getFollowing(userId);
    }

    @GetMapping("/moderator/{userId}")
    public ResponseEntity<List<UserDetailDto>> getModerator(@PathVariable("userId") String userId){
        return profileService.getModerators(userId);
    }

    @DeleteMapping("/follower/{followerId}/{userId}")
    public ResponseEntity<String> removeFollower(@PathVariable("followerId") String followerId,@PathVariable("userId") String userId){
        return profileService.removeFollower(followerId,userId);
    }

    @DeleteMapping("/following/{followingId}/{userId}")
    public ResponseEntity<String> removeFollowing(@PathVariable("followingId") String followingId,@PathVariable("userId") String userId){
        return profileService.removeFollowing(followingId,userId);
    }

    @DeleteMapping("/moderator/{moderatorId}/{userId}")
    public ResponseEntity<String> removeModerator(@PathVariable("moderatorId") String moderatorId,@PathVariable("userId") String userId){
        return profileService.removeFollower(moderatorId,userId);
    }

    @DeleteMapping("/category/{categoryId}/{userId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") String categoryId,@PathVariable("userId") String userId){
        return profileService.removeFollower(categoryId,userId);
    }



}
