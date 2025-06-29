package com.chat.chatapp.mapper;

import com.chat.chatapp.dto.request.UsercreationRequest;
import com.chat.chatapp.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-29T21:58:37+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UsercreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( request.getUserId() );
        user.fullName( request.getFullName() );
        user.password( request.getPassword() );
        user.email( request.getEmail() );
        user.phone( request.getPhone() );
        user.dateOfBirth( request.getDateOfBirth() );

        return user.build();
    }
}
