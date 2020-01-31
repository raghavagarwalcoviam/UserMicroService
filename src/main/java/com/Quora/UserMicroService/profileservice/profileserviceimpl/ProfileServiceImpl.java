package com.Quora.UserMicroService.profileservice.profileserviceimpl;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Followers;
import com.Quora.UserMicroService.profileentity.Following;
import com.Quora.UserMicroService.profileentity.Moderators;
import com.Quora.UserMicroService.profileentity.Profile;
import com.Quora.UserMicroService.repository.FollowerRepository;
import com.Quora.UserMicroService.repository.FollowingRepository;
import com.Quora.UserMicroService.repository.ModeratorRepository;
import com.Quora.UserMicroService.repository.ProfileRepository;
import com.Quora.UserMicroService.profileservice.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    FollowerRepository followerRepository;
    @Autowired
    FollowingRepository followingRepository;
    @Autowired
    ModeratorRepository moderatorRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Optional<Profile> getProfile(String userId) {
        return profileRepository.findById(userId);
    }

    @Override
    public AskerResponseDto getFollower(AskerDto askerDto) {
        AskerResponseDto askerResponseDto = new AskerResponseDto();
        List<String> categoryFollowerList = new ArrayList<>();
        Iterator<Profile> profileIterator;
        Optional<Profile> profile = profileRepository.findById(askerDto.getAskerId());
        List<Profile> profileList = profileRepository.findAll();

        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            askerResponseDto.setAskerFollowerList(new ArrayList<String>());
            while(iterator.hasNext()){
                askerResponseDto.getAskerFollowerList().add(iterator.next().getFollowerId());
            }

        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestId().equals(askerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
            }
            }
            }
        askerResponseDto.setCategoryFollowerList(categoryFollowerList);

        if (null != askerDto.getTaggedProfileId()) {
            profile = profileRepository.findById(askerDto.getTaggedProfileId());
            if (profile.isPresent()) {
                String profileName = profile.get().getProfile();
                System.out.println(profileName);
                if (profileName.equals("public")) {
                    List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
                    Iterator<Followers> iterator = followers.iterator();
                    askerResponseDto.setTagFollowerList(new ArrayList<String>());
                    while(iterator.hasNext()){
                        askerResponseDto.getTagFollowerList().add(iterator.next().getFollowerId());
                    }
                } else {
                    List<Moderators> moderators = moderatorRepository.findByUserId(profile.get().getUserId());
                    Iterator<Moderators> iterator = moderators.iterator();
                    askerResponseDto.setModeratorList(new ArrayList<String>());
                    while(iterator.hasNext()){
                        askerResponseDto.getModeratorList().add(iterator.next().getModeratorId());
                    }
                }
            }
        }
        return askerResponseDto;
    }

    @Override
    public AskerResponseDto getApprovedFollower(AskerDto askerDto) {
        AskerResponseDto askerResponseDto = new AskerResponseDto();
        List<String> categoryFollowerList = new ArrayList<>();
        Iterator<Profile> profileIterator;
        Optional<Profile> profile = profileRepository.findById(askerDto.getAskerId());
        List<Profile> profileList = profileRepository.findAll();

        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            askerResponseDto.setAskerFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                askerResponseDto.getAskerFollowerList().add(iterator.next().getFollowerId());
            }
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestId().equals(askerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
        }
        askerResponseDto.setCategoryFollowerList(categoryFollowerList);

        profile = profileRepository.findById(askerDto.getTaggedProfileId());
        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            askerResponseDto.setTagFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                askerResponseDto.getTagFollowerList().add(iterator.next().getFollowerId());
            }
        }
        return askerResponseDto;
    }

    @Override
    public AnswerResponseDto getAnswerFollower(AnswerDto answerDto) {
        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
        List<String> categoryFollowerList = new ArrayList<>();
        Iterator<Profile> profileIterator;
        Optional<Profile> profile = profileRepository.findById(answerDto.getQuestionAskerId());
        List<Profile> profileList = profileRepository.findAll();
        Optional<Profile> answerProfile = profileRepository.findById(answerDto.getAnswerUserId());

        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            answerResponseDto.setAskerFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                answerResponseDto.getAskerFollowerList().add(iterator.next().getFollowerId());
            }
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestId().equals(answerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
        }
        answerResponseDto.setCategoryFollowerList(categoryFollowerList);

        if (null != answerDto.getTaggedProfileId()) {
            profile = profileRepository.findById(answerDto.getTaggedProfileId());
            if (profile.isPresent()) {
                if (profile.get().getProfile().equals("public")) {
                    List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
                    Iterator<Followers> iterator = followers.iterator();
                    answerResponseDto.setTagFollowerList(new ArrayList<>());
                    while(iterator.hasNext()){
                        answerResponseDto.getTagFollowerList().add(iterator.next().getFollowerId());
                    }
                } else {
                    List<Moderators> moderators = moderatorRepository.findByUserId(profile.get().getUserId());
                    Iterator<Moderators> iterator = moderators.iterator();
                    answerResponseDto.setModeratorList(new ArrayList<>());
                    while(iterator.hasNext()){
                        answerResponseDto.getModeratorList().add(iterator.next().getModeratorId());
                    }
                }
            }

         if(answerProfile.isPresent()){
             List<Followers> followers = followerRepository.findByUserId(answerProfile.get().getUserId());
             Iterator<Followers> iterator = followers.iterator();
             answerResponseDto.setAnswerFollowerList(new ArrayList<>());
             while(iterator.hasNext()){
                 answerResponseDto.getAnswerFollowerList().add(iterator.next().getFollowerId());
             }
         }
         }
          return answerResponseDto;

    }

    @Override
    public AnswerResponseDto getAnswerApprovedFollower(AnswerDto answerDto) {
        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
        List<String> categoryFollowerList = new ArrayList<>();
        Iterator<Profile> profileIterator;
        Optional<Profile> profile = profileRepository.findById(answerDto.getQuestionAskerId());
        List<Profile> profileList = profileRepository.findAll();
        Optional<Profile> answerProfile = profileRepository.findById(answerDto.getAnswerUserId());


        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            answerResponseDto.setAskerFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                answerResponseDto.getAskerFollowerList().add(iterator.next().getFollowerId());
            }
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestId().equals(answerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
        }
        answerResponseDto.setCategoryFollowerList(categoryFollowerList);

        profile = profileRepository.findById(answerDto.getTaggedProfileId());
        if (profile.isPresent()) {
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            answerResponseDto.setTagFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                answerResponseDto.getTagFollowerList().add(iterator.next().getFollowerId());
            }
        }


        if(answerProfile.isPresent()){
            List<Followers> followers = followerRepository.findByUserId(answerProfile.get().getUserId());
            Iterator<Followers> iterator = followers.iterator();
            answerResponseDto.setAnswerFollowerList(new ArrayList<>());
            while(iterator.hasNext()){
                answerResponseDto.getAnswerFollowerList().add(iterator.next().getFollowerId());
            }
        }
        return answerResponseDto;
    }

    @Override
    public ResponseEntity<String> addFollower(String followerId, String userId) throws JsonProcessingException {
        Optional<Profile> profile = profileRepository.findById(userId);
        Optional<Profile> followProfile = profileRepository.findById(followerId);
        if(profile.isPresent()){
            Followers followers = new Followers();
            followers.setFollowerId(followerId);
            followers.setUserId(userId);
            followerRepository.save(followers);
            sendFollowNotifications(profile,followProfile);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> addFollowing(String followingId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            Following following = new Following();
            following.setFollowingId(followingId);
            following.setUserId(userId);
            followingRepository.save(following);
            //notification
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Synchronized
    public ResponseEntity<String> addPoints(int points, String userId) throws JsonProcessingException {
        Optional<Profile> profile = profileRepository.findById(userId);
        if (profile.isPresent()) {
            profile.get().setPoints(profile.get().getPoints() + points);
            String level = profile.get().getLevel();
            if (profile.get().getPoints() <= 1000) {
                profile.get().setLevel("beginner");
                if(level.equals("beginner") == false) {
                    sendNotificationPoints(profile, "beginner");
                }
                }

            if (profile.get().getPoints() > 1000 && profile.get().getPoints()<=2500) {
                profile.get().setLevel("silver");
                if(level.equals("silver")==false) {
                    sendNotificationPoints(profile, "silver");
                }
            }

            if (profile.get().getPoints() > 2500 && profile.get().getPoints()<=6000) {
                profile.get().setLevel("gold");
                if(level.equals("gold") == false) {
                    sendNotificationPoints(profile, "gold");
                }
            }

            if (profile.get().getPoints() > 6000) {
                profile.get().setLevel("platinum");
                if(level.equals("platinum") == false) {
                    sendNotificationPoints(profile, "platinum");
                }
            }
                profileRepository.save(profile.get());
                return new ResponseEntity<String>(HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
        }

    @Override
    public ResponseEntity<String> addModerator(String moderatorId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            Moderators moderators = new Moderators();
            moderators.setModeratorId(moderatorId);
            moderators.setUserId(userId);
            moderatorRepository.save(moderators);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> addCategory(CategoryDto categoryDto) {
        Optional<Profile> profile = profileRepository.findById(categoryDto.getUserId());
        InterestDto interestDto = new InterestDto();
        if(profile.isPresent()){
            interestDto.setInterestId(categoryDto.getInterestId());
            interestDto.setInterestName(categoryDto.getInterestName());
            profile.get().getCategory().add(interestDto);
            profileRepository.save(profile.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<UserDetailDto>> getFollowers(String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        Iterator<Followers> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        if(profile.isPresent()){
            List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
            iterator = followers.iterator();
            while(iterator.hasNext()){
                Optional<Profile> followerProfile = profileRepository.findById(iterator.next().getFollowerId());
                if(followerProfile.isPresent()){
                    UserDetailDto userDetailDto = new UserDetailDto();
                    userDetailDto.setName(followerProfile.get().getName());
                    userDetailDto.setUserId(followerProfile.get().getUserId());
                    userDetailDtos.add(userDetailDto);
                }
            }
            return new ResponseEntity<List<UserDetailDto>>(userDetailDtos,HttpStatus.OK);
        }
       else {
            return new ResponseEntity<List<UserDetailDto>>(HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public ResponseEntity<List<UserDetailDto>> getFollowing(String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        Iterator<Following> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();

        if(profile.isPresent()){
            List<Following> followings= followingRepository.findByUserId(profile.get().getUserId());
            iterator = followings.iterator();
            while(iterator.hasNext()){
                Optional<Profile> followingProfile = profileRepository.findById(iterator.next().getFollowingId());
                if(followingProfile.isPresent()){
                    UserDetailDto userDetailDto = new UserDetailDto();
                    userDetailDto.setName(followingProfile.get().getName());
                    userDetailDto.setUserId(followingProfile.get().getUserId());
                    userDetailDtos.add(userDetailDto);
                }
            }
            return new ResponseEntity<List<UserDetailDto>>(userDetailDtos,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<UserDetailDto>>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<List<UserDetailDto>> getModerators(String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        Iterator<Moderators> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        if(profile.isPresent()){
            List<Moderators> moderators=moderatorRepository.findByUserId(profile.get().getUserId());
            iterator = moderators.iterator();
            while(iterator.hasNext()){
                Optional<Profile> moderatorProfile = profileRepository.findById(iterator.next().getModeratorId());
                if(moderatorProfile.isPresent()){
                    UserDetailDto userDetailDto = new UserDetailDto();
                    userDetailDto.setName(moderatorProfile.get().getName());
                    userDetailDto.setUserId(moderatorProfile.get().getUserId());
                    userDetailDtos.add(userDetailDto);
                }
            }
            return new ResponseEntity<List<UserDetailDto>>(userDetailDtos,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<UserDetailDto>>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<List<InterestDto>> getCategory(String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            return new ResponseEntity<List<InterestDto>>(profile.get().getCategory(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<List<InterestDto>>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> removeFollower(String followerId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            Optional<Followers> followers = followerRepository.findByUserIdAndFollowerId(userId,followerId);
            if(followers.isPresent()){
                followerRepository.delete(followers.get());
            }
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> removeFollowing(String followingId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            Optional<Following> following = followingRepository.findByUserIdAndFollowingId(userId,followingId);
            if(following.isPresent()){
                followingRepository.delete(following.get());
            }
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> removeModerator(String moderatorId, String userId) {
            Optional<Profile> profile = profileRepository.findById(userId);
        System.out.println(profile.get());
            if(profile.isPresent()){
                Optional<Moderators> moderators = moderatorRepository.findByUserIdAndModeratorId(userId,moderatorId);
                System.out.println(moderators.get().getModeratorId());
                if(moderators.isPresent()){
                    moderatorRepository.delete(moderators.get());
                }
                return new ResponseEntity<String>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
    }

    @Override
    public ResponseEntity<String> removeCategory(String categoryId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            List<InterestDto> interestDtos = profile.get().getCategory();
            Iterator<InterestDto> interestDtoIterator = interestDtos.iterator();
            while (interestDtoIterator.hasNext()){
                if(interestDtoIterator.next().getInterestId().equals(categoryId)){
                    interestDtoIterator.remove();
                }
            }
            profile.get().setCategory(interestDtos);
            profileRepository.save(profile.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    public void sendNotificationPoints(Optional<Profile> profile,String level) throws JsonProcessingException {
        PointUpgradeNotificationDto pointUpgradeNotificationDto = new PointUpgradeNotificationDto();
        pointUpgradeNotificationDto.setLevel(level);
        pointUpgradeNotificationDto.setUserName(profile.get().getName());
        pointUpgradeNotificationDto.setUserId(profile.get().getUserId());
        List<Followers> followers = followerRepository.findByUserId(profile.get().getUserId());
        Iterator<Followers> iterator = followers.iterator();
        pointUpgradeNotificationDto.setFollowerUserId(new ArrayList<>());
        while(iterator.hasNext()){
            pointUpgradeNotificationDto.getFollowerUserId().add(iterator.next().getFollowerId());
        }
      ObjectMapper mapper = new ObjectMapper();
       kafkaTemplate.send("levelUp",mapper.writeValueAsString(pointUpgradeNotificationDto));

    }

    public void sendFollowNotifications(Optional<Profile>profile,Optional<Profile> followProfile) throws JsonProcessingException {
        FollowDto followDto = new FollowDto();
        followDto.setFollowerName(profile.get().getName());
        followDto.setFollowerUserId(profile.get().getUserId());
        if(followProfile.isPresent()){
        followDto.setFollowedUserId(followProfile.get().getUserId());
        followDto.setFollowedIdType(followProfile.get().getProfile());
    }
    ObjectMapper mapper = new ObjectMapper();
        kafkaTemplate.send("followRequest",mapper.writeValueAsString(followDto));
    }

}
