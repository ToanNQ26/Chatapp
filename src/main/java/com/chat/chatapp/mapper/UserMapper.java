package com.chat.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chat.chatapp.dto.request.UsercreationRequest;
import com.chat.chatapp.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "messages", ignore = true)
    User toUser(UsercreationRequest request);
    
} 
