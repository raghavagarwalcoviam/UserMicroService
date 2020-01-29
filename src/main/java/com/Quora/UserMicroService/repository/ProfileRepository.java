package com.Quora.UserMicroService.repository;

import com.Quora.UserMicroService.profileentity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository <Profile,String>{
}
