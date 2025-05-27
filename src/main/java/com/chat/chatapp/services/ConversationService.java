package com.chat.chatapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.dto.request.BulkAddParticipantsRequest;
import com.chat.chatapp.dto.request.ConversationCreationRequest;
import com.chat.chatapp.dto.request.ConversationParticipantRequest;
import com.chat.chatapp.dto.request.ConversationUpdateRequest;
import com.chat.chatapp.dto.request.GetConversationByListUserRequest;
import com.chat.chatapp.dto.response.ConversationResponeNotMessage;
import com.chat.chatapp.entity.Conversation;
import com.chat.chatapp.entity.Conversationparticipant;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.repository.ConnversationparticipantRepository;
import com.chat.chatapp.repository.ConversationRepository;
import com.chat.chatapp.repository.MessageRepository;
import com.chat.chatapp.repository.UserRepository;
import com.chat.chatapp.dto.response.MessageResponsedto;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ConversationService {
    ConversationRepository conversationRepository;
    ConnversationparticipantRepository repository;
    UserRepository userRepository;
    MessageRepository mRepository;

    // Các hàm không phụ trợ

    public ConversationResponeNotMessage mapToNotMessage(Conversation conversation) {
        return ConversationResponeNotMessage.builder()
                                            .ConversationId(conversation.getConversationId())
                                            .name(conversation.getName())
                                            .build();
    }


    //Các hàm chính sử dụng trong controller

    public boolean checkConversationExisted(String conversationId) {
        if(conversationRepository.findById(conversationId).isPresent()) {
            return true;
        }   
        return false;
    }

    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    public Conversation createdConversation(ConversationCreationRequest request) {

        if(request.getMemberId().isEmpty())
            throw new AppException(ErrorCode.EMPTY);

        if (request.getMemberId().size() == 2) {
        var foundConversationIds = repository
        .findConversationIdsByExactParticipants(request.getMemberId(), request.getMemberId().size());

        if (!foundConversationIds.isEmpty()) {
            throw new AppException(ErrorCode.CONVERSATION_EXISTED);
        }
    }

        Conversation conversation = Conversation.builder()
                                                //.conversationId(request.getConversationId())
                                                .name(request.getName())
                                                .build();

        var saveconversation = conversationRepository.save(conversation);
        String conversationId = saveconversation.getConversationId();
                                                
        BulkAddParticipantsRequest bRequest = BulkAddParticipantsRequest.builder()
                                                                        .conversationId(conversationId)
                                                                        .userId(request.getMemberId())
                                                                        .build();
        addMemberWithList(bRequest);
        return saveconversation;
    }

    public Conversation updateConversation(String conversationId, ConversationUpdateRequest request) {
        var conversation = conversationRepository.findById(conversationId)
        .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        conversation.setName(request.getName());

        return conversationRepository.save(conversation);
    }

    public List<Conversation> getConversationIdByUserId(String userId) {
         var listparticipant = repository.findByUserId(userId);

         List<String> conversationIds = listparticipant.stream()
        .map(Conversationparticipant::getConversationId) 
        .collect(Collectors.toList());

        return conversationRepository.findAllById(conversationIds);
    }

    public List<ConversationResponeNotMessage> 
    getConversationByUserIdWithoutMessage(String id) {
        var listparticipant = repository.findByUserId(id);

        List<String> conversationIds = listparticipant.stream()
        .map(Conversationparticipant::getConversationId) 
        .collect(Collectors.toList());

        List<Conversation> conversation = conversationRepository.findAllById(conversationIds);
        return conversation.stream()
                .map(this::mapToNotMessage)
                .collect(Collectors.toList());
    }

    public String addMember(ConversationParticipantRequest request) {

        var existed = repository.
        findByUserIdAndConversationId(request.getUserId(), request.getConversationId());


        if(existed.isPresent()) 
            throw new AppException(ErrorCode.USER_ALREADY_IN_GROUP);

        if(!conversationRepository.existsById(request.getConversationId()))
            throw new AppException(ErrorCode.CONVERSATION_NOT_EXISTED);

        if(!userRepository.existsById(request.getUserId()))
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        

        Conversationparticipant conversationparticipant 
        = Conversationparticipant.builder()
                                .conversationId(request.getConversationId())
                                .userId(request.getUserId())
                                .build();
        repository.save(conversationparticipant);
        return "Add Successly!";
    }

    public String addMemberWithList(BulkAddParticipantsRequest request) {
        var exist = repository.findAllByConversationIdAndUserIdIn(request.getConversationId(), request.getUserId());

        if(!exist.isEmpty())
            throw new AppException(ErrorCode.USER_ALREADY_IN_GROUP);
        
        if (!conversationRepository.existsById(request.getConversationId())) {
            throw new AppException(ErrorCode.CONVERSATION_NOT_EXISTED);
        }

        List<User> users = userRepository.findAllById(request.getUserId());
        if (users.size() != request.getUserId().size()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        List<Conversationparticipant> toAdd = request.getUserId().stream()
            .map(userId -> Conversationparticipant.builder()
                                .conversationId(request.getConversationId())
                                .userId(userId)
                                .build())
            .collect(Collectors.toList());

            repository.saveAll(toAdd);
        return "Addsuccessly";
    }

    @Transactional
    public String deleteMember(ConversationParticipantRequest request) {

        repository
        .findByUserIdAndConversationId(request.getUserId(), request.getConversationId())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED_IN_GROUP));
        

        conversationRepository.findById(request.getConversationId()).orElseThrow(() ->
        new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        repository.deleteByUserIdAndConversationId(request.getUserId(), request.getConversationId());
        return "Delete Successly!"; 
    }

    @Transactional
    public String leaveConversation(ConversationParticipantRequest request) {
        deleteMember(request);
        return  request.getUserId() + " was leaved!";
    } 

    public String deleteConversation(String conversationId) {
        var deleteconversation = conversationRepository.findById(conversationId).orElseThrow(() ->
        new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        conversationRepository.delete(deleteconversation);

        List<Conversationparticipant> deleteParticipants = repository.findAllByConversationId(conversationId);
        if(deleteParticipants.isEmpty())
            return "Have been deleted but this conversation haven't messsage!";
        repository.deleteAll(deleteParticipants);
        return "Delete successly!";
    }

    public List<MessageResponsedto> getMessageByGroupId(String id) {
        var conversation = conversationRepository.findById(id).
        orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
       
        return mRepository.findByConversationIdOrderByTimeStampAsc(conversation)
        .stream()
        .map(MessageResponsedto::new)
        .collect(Collectors.toList());
    }

    public List<User> getMember(String conversationId) {
        var list = repository.findAllByConversationId(conversationId);
        if(list.isEmpty()) 
            throw new AppException(ErrorCode.CONVERSATION_NOT_EXISTED);
        List<String> userId = list.stream()
                            .map(Conversationparticipant::getUserId)
                            .toList();
        
        var listUser = userRepository.findAllById(userId);

        if(listUser.size() != userId.size())
            throw new AppException(ErrorCode.ERROR_LOAD_DATA);        
    
        return listUser;
    }

    public List<Conversation> getConversationsWithOption(String userId, int optional) {
        return conversationRepository.findConversationsWithParticipantsGreaterThan(optional);
    }

    public Conversation getConversationByListUser(GetConversationByListUserRequest request) {
        long memberCount = request.getUserId().size();
        List<String> conversationIds = repository.findConversationIdsByExactParticipants(request.getUserId(), memberCount);

        if (conversationIds.isEmpty()) {
            throw new AppException(ErrorCode.EMPTY);
        }

        var conversation = conversationRepository.findById(conversationIds.get(0))
        .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        return conversation;
    }
}
