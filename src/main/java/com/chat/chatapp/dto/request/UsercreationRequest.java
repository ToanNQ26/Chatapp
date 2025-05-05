package com.chat.chatapp.dto.request;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsercreationRequest {
     String UserId;
     String fullName;    
     String password;
     String email;
     String phone;
     Date dateOfBirth; 
     boolean isActive;
}
