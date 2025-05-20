package com.chat.chatapp.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String senderId;
    String receiverId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdAt;
}
