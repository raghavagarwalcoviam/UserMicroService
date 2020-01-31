package com.Quora.UserMicroService.profilecontroller;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Profile;
import com.Quora.UserMicroService.profileservice.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    //Testing done
    @PostMapping("/basicDetails")
    public ResponseEntity<String> addBasicDetails(@RequestBody BasicDetailsDto basicDetailsDto){
        Profile profile = new Profile();
        BeanUtils.copyProperties(basicDetailsDto,profile);
        profile.setPoints(0);
        profile.setLevel("Beginner");
        Profile createdProfile = profileService.addProfile(profile);
        //insert to kafka
        return new ResponseEntity<String>(createdProfile.getUserId(),HttpStatus.CREATED);
    }

    //Testing done
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

    //Tesing done
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

    //Testing done
    @GetMapping("/followerId")
    public ResponseEntity<AskerResponseDto> getFollowerId(@RequestBody AskerDto askerDto){
        return new ResponseEntity<AskerResponseDto>(profileService.getFollower(askerDto),HttpStatus.ACCEPTED);
    }

    //Testing done
    @GetMapping("/approveFollower")
    public ResponseEntity<AskerResponseDto> getApprovedFollower(@RequestBody AskerDto askerDto){
        return new ResponseEntity<AskerResponseDto>(profileService.getApprovedFollower(askerDto),HttpStatus.ACCEPTED);
    }

    //Testing done
    @GetMapping("/answerFollowerId")
    public ResponseEntity<AnswerResponseDto> getAnswerFollower(@RequestBody AnswerDto answerDto){
        return new ResponseEntity<AnswerResponseDto>(profileService.getAnswerFollower(answerDto),HttpStatus.ACCEPTED);
    }

    //Testing done
    @GetMapping("/answerApproveFollower")
    public ResponseEntity<AnswerResponseDto> getAnswerApprovedFollower(@RequestBody AnswerDto answerDto){
        return new ResponseEntity<AnswerResponseDto>(profileService.getAnswerApprovedFollower(answerDto),HttpStatus.ACCEPTED);
    }

    //Tesing done
    @PutMapping("/addFollower/{followerId}/{userId}")
    public ResponseEntity<String> addFollower(@PathVariable("followerId") String followerId , @PathVariable("userId") String userId) throws JsonProcessingException{
        return profileService.addFollower(followerId,userId);
    }
    //Testing done
    @PutMapping("/addFollowing/{followingId}/{userId}")
    public ResponseEntity<String> addFollowing(@PathVariable("followingId") String followingId , @PathVariable("userId") String userId){
        return profileService.addFollowing(followingId,userId);
    }

    //Testing done
    @PutMapping("/addPoints/{points}/{userId}")
    public ResponseEntity<String> addPoints(@PathVariable("points") int points , @PathVariable("userId") String userId) throws JsonProcessingException {
        return profileService.addPoints(points,userId);
    }

    //Testing done
    @PutMapping("/addModerator/{moderatorId}/{userId}")
    public ResponseEntity<String> addModerator(@PathVariable("moderatorId") String moderatorId , @PathVariable("userId") String userId){
        return profileService.addModerator(moderatorId,userId);
    }

    //Testing Done
    @PutMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto){
        return profileService.addCategory(categoryDto);
    }

    //Testing done
    @GetMapping("/category/{userId}")
    public ResponseEntity<List<InterestDto>> getCategory(@PathVariable("userId") String userId){
        return profileService.getCategory(userId);
    }

    //Testing done
    @GetMapping("/follower/{userId}")
    public ResponseEntity<List<UserDetailDto>> getFollower(@PathVariable("userId") String userId){
        return profileService.getFollowers(userId);
    }

    //Testing done
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserDetailDto>> getFollowing(@PathVariable("userId") String userId){
        return profileService.getFollowing(userId);
    }

    //Testing done
    @GetMapping("/moderator/{userId}")
    public ResponseEntity<List<UserDetailDto>> getModerator(@PathVariable("userId") String userId){
        return profileService.getModerators(userId);
    }

    //Testing done
    @DeleteMapping("/follower/{followerId}/{userId}")
    public ResponseEntity<String> removeFollower(@PathVariable("followerId") String followerId,@PathVariable("userId") String userId){
        return profileService.removeFollower(followerId,userId);
    }

    //Testing done
    @DeleteMapping("/following/{followingId}/{userId}")
    public ResponseEntity<String> removeFollowing(@PathVariable("followingId") String followingId,@PathVariable("userId") String userId){
        return profileService.removeFollowing(followingId,userId);
    }

    //Testing done
    @DeleteMapping("/moderator/{moderatorId}/{userId}")
    public ResponseEntity<String> removeModerator(@PathVariable("moderatorId") String moderatorId,@PathVariable("userId") String userId){
        return profileService.removeModerator(moderatorId,userId);
    }

    //Testing Done
    @DeleteMapping("/category/{categoryId}/{userId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") String categoryId,@PathVariable("userId") String userId){
        return profileService.removeCategory(categoryId,userId);
    }



}
