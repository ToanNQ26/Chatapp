package com.chat.chatapp.dto.request;
import java.sql.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsercreationRequest {
     String UserId;
     String fullName;    
     @NotBlank(message = "Password is required")
     String password;
     @Email(message = "Invalid email format")
     String email;
     @NotBlank(message = "Phone is required")
     String phone;
     Date dateOfBirth; 
     boolean isActive;
}
