package com.Quora.UserMicroService.repository;

import com.Quora.UserMicroService.profileentity.Followers;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends MongoRepository<Followers,String> {
    List<Followers> findByUserId(String s);
    Optional<Followers> findByUserIdAndFollowerId(String userId,String followerId);
}
