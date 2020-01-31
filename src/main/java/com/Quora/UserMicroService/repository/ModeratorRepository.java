package com.Quora.UserMicroService.repository;

import com.Quora.UserMicroService.profileentity.Followers;
import com.Quora.UserMicroService.profileentity.Moderators;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ModeratorRepository extends MongoRepository<Moderators,String> {
    List<Moderators> findByUserId(String s);
    Optional<Moderators> findByUserIdAndModeratorId(String userId,String moderatorId);
}
