package com.chat.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "app_user")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
     String userId;     
     String fullName;    
     String password;
     String email;
     String phone;
     Date dateOfBirth; 
     boolean isActive; 
     String avatar;   

     Set<String> roles;

     @OneToMany(mappedBy = "senderId", cascade = CascadeType.REMOVE, orphanRemoval = true)
     //@OneToMany(mappedBy = "senderId")
     @JsonIgnore
     List<Message> messages;
}

