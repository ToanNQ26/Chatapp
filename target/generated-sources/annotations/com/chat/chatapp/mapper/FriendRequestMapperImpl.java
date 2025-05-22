package com.chat.chatapp.mapper;

import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.entity.FriendRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T20:56:39+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class FriendRequestMapperImpl implements FriendRequestMapper {

    @Override
    public FriendRequest toFriendRequest(SendFriendRequest request) {
        if ( request == null ) {
            return null;
        }

        FriendRequest.FriendRequestBuilder friendRequest = FriendRequest.builder();

        friendRequest.receiverId( request.getReceiverId() );
        friendRequest.senderId( request.getSenderId() );

        return friendRequest.build();
    }
}
