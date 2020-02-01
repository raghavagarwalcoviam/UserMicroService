package com.Quora.UserMicroService.profilecontroller;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Profile;
import com.Quora.UserMicroService.profileservice.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/profile")
public class Controller {

    @Autowired
    ProfileService profileService;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    //Testing done
    @PostMapping("/basicDetails")
    public ResponseEntity<String> addBasicDetails(@RequestBody BasicDetailsDto basicDetailsDto){
        Profile profile = new Profile();
        BeanUtils.copyProperties(basicDetailsDto,profile);
        profile.setPoints(0);
        profile.setLevel("Beginner");
        profile.setCategory(new ArrayList<>());
        Profile createdProfile = profileService.addProfile(profile);
        //insert to kafka
        return new ResponseEntity<String>(createdProfile.getUserId(),HttpStatus.CREATED);
    }

    //Testing done
    @PostMapping("/extraDetails")
    public ResponseEntity<String> addExtraDetails(@RequestHeader("userId")String userId,@RequestBody ExtraDetailsDto extraDetailsDto){
        extraDetailsDto.setUserId(userId);
        Optional<Profile> profile = profileService.getProfile(extraDetailsDto.getUserId());
        SearchDto searchDto = new SearchDto();
        searchDto.setValueType("P");

        if(profile.isPresent()) {
            searchDto.setProfileId(profile.get().getUserId());
            searchDto.setProfileValue(profile.get().getName());
            searchDto.setProfileType(extraDetailsDto.getProfile());
            sendToSearch(searchDto);
            BeanUtils.copyProperties(extraDetailsDto, profile.get());
            Profile createdProfile = profileService.addProfile(profile.get());
            return new ResponseEntity<String>(createdProfile.getUserId(),HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    //Tesing done
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(@RequestHeader("userId") String userId){
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
    @PutMapping("/addFollower/{followerId}")
    public ResponseEntity<String> addFollower(@PathVariable("followerId") String followerId , @RequestHeader("userId") String userId) throws JsonProcessingException{
        return profileService.addFollower(followerId,userId);
    }
    //Testing done
    @PutMapping("/addFollowing/{followingId}")
    public ResponseEntity<String> addFollowing(@PathVariable("followingId") String followingId , @RequestHeader("userId") String userId){
        return profileService.addFollowing(followingId,userId);
    }

    //Testing done
    @PutMapping("/addPoints/{points}/{userId}")
    public ResponseEntity<String> addPoints(@PathVariable("points") int points , @PathVariable("userId") String userId) throws JsonProcessingException {
        return profileService.addPoints(points,userId);
    }

    //Testing done
    @PutMapping("/addModerator/{moderatorId}")
    public ResponseEntity<String> addModerator(@PathVariable("moderatorId") String moderatorId , @RequestHeader("userId") String userId){
        return profileService.addModerator(moderatorId,userId);
    }

    //Testing Done
    @PutMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestHeader("userId")String userId,@RequestBody CategoryDto categoryDto){
        System.out.println(userId);
        categoryDto.setUserId(userId);
        return profileService.addCategory(categoryDto);
    }

    //Testing done
    @GetMapping("/category")
    public ResponseEntity<List<InterestDto>> getCategory(@RequestHeader("userId")String userId){
        return profileService.getCategory(userId);
    }

    //Testing done
    @GetMapping("/follower")
    public ResponseEntity<List<UserDetailDto>> getFollower(@RequestHeader("userId") String userId){
        return profileService.getFollowers(userId);
    }

    //Testing done
    @GetMapping("/following")
    public ResponseEntity<List<UserDetailDto>> getFollowing(@RequestHeader("userId") String userId){
        return profileService.getFollowing(userId);
    }

    //Testing done
    @GetMapping("/moderator}")
    public ResponseEntity<List<UserDetailDto>> getModerator(@RequestHeader("userId") String userId){
        return profileService.getModerators(userId);
    }

    //Testing done
    @DeleteMapping("/follower/{followerId}")
    public ResponseEntity<String> removeFollower(@PathVariable("followerId") String followerId,@RequestHeader("userId") String userId){
        return profileService.removeFollower(followerId,userId);
    }

    //Testing done
    @DeleteMapping("/following/{followingId}")
    public ResponseEntity<String> removeFollowing(@PathVariable("followingId") String followingId,@RequestHeader("userId") String userId){
        return profileService.removeFollowing(followingId,userId);
    }

    //Testing done
    @DeleteMapping("/moderator/{moderatorId}")
    public ResponseEntity<String> removeModerator(@PathVariable("moderatorId") String moderatorId,@RequestHeader("userId") String userId){
        return profileService.removeModerator(moderatorId,userId);
    }

    //Testing Done
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") String categoryId,@RequestHeader("userId") String userId){
        return profileService.removeCategory(categoryId,userId);
    }

    public void sendToSearch(SearchDto searchDto){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            kafkaTemplate.send("addToSearch",objectMapper.writeValueAsString(searchDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    }

