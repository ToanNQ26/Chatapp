package com.chat.chatapp.mapper;

import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.entity.FriendRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-30T09:18:29+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class FriendRequestMapperImpl implements FriendRequestMapper {

    @Override
    public FriendRequest toFriendRequest(SendFriendRequest request) {
        if ( request == null ) {
            return null;
        }

        FriendRequest.FriendRequestBuilder friendRequest = FriendRequest.builder();

        friendRequest.senderId( request.getSenderId() );
        friendRequest.receiverId( request.getReceiverId() );

        return friendRequest.build();
    }
}
