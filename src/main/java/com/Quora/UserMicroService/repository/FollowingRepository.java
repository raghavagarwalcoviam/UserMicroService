package com.Quora.UserMicroService.repository;

import com.Quora.UserMicroService.profileentity.Followers;
import com.Quora.UserMicroService.profileentity.Following;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends MongoRepository<Following,String> {
    List<Following> findByUserId(String s);
    Optional<Following> findByUserIdAndFollowingId(String userId, String followingId);
}
