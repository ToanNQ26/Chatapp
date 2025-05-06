package com.chat.chatapp.mapper;

import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.entity.FriendRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T14:59:10+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class FriendRequestMapperImpl implements FriendRequestMapper {

    @Override
    public FriendRequest toFriendRequest(SendFriendRequest request) {
        if ( request == null ) {
            return null;
        }

        FriendRequest.FriendRequestBuilder friendRequest = FriendRequest.builder();

        friendRequest.receiverid( request.getReceiverid() );
        friendRequest.senderid( request.getSenderid() );

        return friendRequest.build();
    }
}
