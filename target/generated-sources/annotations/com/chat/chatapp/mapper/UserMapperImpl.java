package com.chat.chatapp.mapper;

import com.chat.chatapp.dto.request.UsercreationRequest;
import com.chat.chatapp.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T14:59:10+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UsercreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.dateOfBirth( request.getDateOfBirth() );
        user.email( request.getEmail() );
        user.fullName( request.getFullName() );
        user.password( request.getPassword() );
        user.phone( request.getPhone() );
        user.userId( request.getUserId() );

        return user.build();
    }
}
