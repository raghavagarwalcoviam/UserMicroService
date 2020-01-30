package com.Quora.UserMicroService.profileservice.profileserviceimpl;

import com.Quora.UserMicroService.profiledto.*;
import com.Quora.UserMicroService.profileentity.Profile;
import com.Quora.UserMicroService.repository.ProfileRepository;
import com.Quora.UserMicroService.profileservice.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

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
            askerResponseDto.setAskerFollowerList(profile.get().getFollowerId());
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestName().equals(askerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
            }
            }

//            if (userProfile.getCategory().contains(askerDto.getCategory())) {
//                categoryFollowerList.add(userProfile.getUserId());
//            }
        }
        askerResponseDto.setCategoryFollowerList(categoryFollowerList);

        if (null != askerDto.getTaggedProfileId()) {
            profile = profileRepository.findById(askerDto.getTaggedProfileId());
            if (profile.isPresent()) {
                if (profile.get().getProfile().equals("public")) {
                    askerResponseDto.setTagFollowerList(profile.get().getFollowerId());
                } else {
                    askerResponseDto.setModeratorList(profile.get().getModeratorId());
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
            askerResponseDto.setAskerFollowerList(profile.get().getFollowerId());
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestName().equals(askerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
//
//            if (userProfile.getCategory().contains(askerDto.getCategory())) {
//                categoryFollowerList.add(userProfile.getUserId());
//            }
        }
        askerResponseDto.setCategoryFollowerList(categoryFollowerList);

        profile = profileRepository.findById(askerDto.getTaggedProfileId());
        if (profile.isPresent()) {
            askerResponseDto.setTagFollowerList(profile.get().getFollowerId());
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
            answerResponseDto.setAskerFollowerList(profile.get().getFollowerId());
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestName().equals(answerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
        }
        answerResponseDto.setCategoryFollowerList(categoryFollowerList);

        if (null != answerDto.getTaggedProfileId()) {
            profile = profileRepository.findById(answerDto.getTaggedProfileId());
            if (profile.isPresent()) {
                if (profile.get().getProfile().equals("public")) {
                    answerResponseDto.setTagFollowerList(profile.get().getFollowerId());
                } else {
                    answerResponseDto.setModeratorList(profile.get().getModeratorId());
                }
            }

         if(answerProfile.isPresent()){
                answerResponseDto.setAnswerFollowerList(answerProfile.get().getFollowerId());
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
        profile = profileRepository.findById(answerDto.getTaggedProfileId());

        if (profile.isPresent()) {
            answerResponseDto.setAskerFollowerList(profile.get().getFollowerId());
        }

        profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            Profile userProfile = profileIterator.next();
            Iterator<InterestDto> interestDtoIterator = userProfile.getCategory().iterator();
            while (interestDtoIterator.hasNext()){
                if (interestDtoIterator.next().getInterestName().equals(answerDto.getCategory())) {
                    categoryFollowerList.add(userProfile.getUserId());
                }
            }
        }
        answerResponseDto.setCategoryFollowerList(categoryFollowerList);
        if (profile.isPresent()) {
                answerResponseDto.setTagFollowerList(profile.get().getFollowerId());
        }


        if(answerProfile.isPresent()){
            answerResponseDto.setAnswerFollowerList(answerProfile.get().getFollowerId());
        }
        return answerResponseDto;
    }

    @Override
    public ResponseEntity<String> addFollower(String followerId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            profile.get().getFollowerId().add(followerId);
            profileRepository.save(profile.get());
            //notification
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
            profile.get().getFollowingId().add(followingId);
            profileRepository.save(profile.get());
            //notification
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> addPoints(int points, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            profile.get().setPoints(profile.get().getPoints() + points);
            if(profile.get().getPoints() > 100){
                profile.get().setLevel("Intermediate");
                //notification
            }
            if(profile.get().getPoints() > 500){
                profile.get().setLevel("Advance");
                //notification
            }
            profileRepository.save(profile.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> addModerator(String moderatorId, String userId) {
        Optional<Profile> profile = profileRepository.findById(userId);
        if(profile.isPresent()){
            profile.get().getModeratorId().add(moderatorId);
            profileRepository.save(profile.get());
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
        Iterator<String> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        UserDetailDto userDetailDto = new UserDetailDto();
        if(profile.isPresent()){
            iterator = profile.get().getFollowerId().iterator();
            while(iterator.hasNext()){
                Optional<Profile> followerProfile = profileRepository.findById(iterator.next());
                if(followerProfile.isPresent()){
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
        Iterator<String> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        UserDetailDto userDetailDto = new UserDetailDto();
        if(profile.isPresent()){
            iterator = profile.get().getFollowingId().iterator();
            while(iterator.hasNext()){
                Optional<Profile> followingProfile = profileRepository.findById(iterator.next());
                if(followingProfile.isPresent()){
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
        Iterator<String> iterator;
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        UserDetailDto userDetailDto = new UserDetailDto();
        if(profile.isPresent()){
            iterator = profile.get().getModeratorId().iterator();
            while(iterator.hasNext()){
                Optional<Profile> moderatorProfile = profileRepository.findById(iterator.next());
                if(moderatorProfile.isPresent()){
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
            profile.get().getFollowerId().remove(followerId);
            profileRepository.save(profile.get());
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
            profile.get().getFollowingId().remove(followingId);
            profileRepository.save(profile.get());
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> removeModerator(String moderatorId, String userId) {
            Optional<Profile> profile = profileRepository.findById(userId);
            if(profile.isPresent()){
                profile.get().getModeratorId().remove(moderatorId);
                profileRepository.save(profile.get());
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


}
