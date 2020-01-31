package com.Quora.UserMicroService.profileentity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("Following")
public class Following {
    @Id
    private String id;
    private String userId;
    private String followingId;
}
