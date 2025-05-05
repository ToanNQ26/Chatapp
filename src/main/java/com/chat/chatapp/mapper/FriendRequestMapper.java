package com.chat.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chat.chatapp.dto.request.SendFriendRequest;
import com.chat.chatapp.entity.FriendRequest;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FriendRequest toFriendRequest(SendFriendRequest request);
}

