package com.chat.chatapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.chatapp.dto.request.BulkAddParticipantsRequest;
import com.chat.chatapp.dto.request.ConversationCreationRequest;
import com.chat.chatapp.dto.request.ConversationParticipantRequest;
import com.chat.chatapp.dto.request.ConversationUpdateRequest;
import com.chat.chatapp.dto.response.ApiResponse;
import com.chat.chatapp.dto.response.ConversationResponeNotMessage;
import com.chat.chatapp.dto.response.MessageResponsedto;
import com.chat.chatapp.entity.Conversation;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.services.ConversationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/conversation")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {
    ConversationService conversationService;
    
    // nhìn tên hàm tự hiểu, truyền vào tên nhóm
    @PostMapping()
    public ApiResponse<Conversation> createConversation(@RequestBody ConversationCreationRequest request) {
        
         var result = conversationService.createdConversation(request);
         return ApiResponse.<Conversation>builder()
                            .code(1000)
                            .result(result)
                            .build();
    }

    // cập nhật, truyền vào tên nhóm ở body
    @PutMapping("/{id}")
    public ApiResponse<Conversation> upadteConversation(@PathVariable String id,@RequestBody ConversationUpdateRequest request) {
        return ApiResponse.<Conversation>builder()
                            .result(conversationService.updateConversation(id,request))
                            .build();
    }

    // nhận tất cả các nhóm
    @GetMapping()
    public List<Conversation> getAllConversation() {
        return conversationService.getAllConversation();
    }

    //xóa nhóm, truyền vào id nhóm
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteConversation(@PathVariable String id) {
        return ApiResponse.<Boolean>builder()
                        .message(conversationService.deleteConversation(id))
                        .result(true)
                        .build();
    }

    //thêm thành viên, truyền vào id nhóm, id người dùng
    @PostMapping("/members")
    public ApiResponse<Boolean> addMember(@RequestBody ConversationParticipantRequest request) {
        return ApiResponse.<Boolean>builder()
                           .message(conversationService.addMember(request))
                           .result(true)
                           .build();
    }

    @PostMapping("/listmembers")
    public ApiResponse<Boolean> addMember(@RequestBody BulkAddParticipantsRequest request) {
        return ApiResponse.<Boolean>builder()
                           .message(conversationService.addMemberWithList(request))
                           .result(true)
                           .build();
    } 

    // xóa thành viên trong nhóm, truyền vào id người dùng và id nhóm trong body
    @DeleteMapping("/members")
    public ApiResponse<Boolean> deleteMembers(@RequestBody ConversationParticipantRequest request) {
        return ApiResponse.<Boolean>builder()
                            .message(conversationService.deleteMember(request))
                            .result(true)
                            .build();
    }
    
    // rời nhóm, truyền vào id người dùng và id nhóm
    @DeleteMapping("/members/leave")
    public ApiResponse<Boolean> memberleave(@RequestBody ConversationParticipantRequest request) {
        return ApiResponse.<Boolean>builder()
                            .message(conversationService.leaveConversation(request))
                            .result(true)
                            .build();
    }

    // lấy hội thoại theo id của người dùng, có chứa tin nhắn
    // @GetMapping("/{userId}")
    // public ApiResponse<List<Conversation>> getListConversation(@PathVariable String userId) {

    //     System.err.println(userId);
    //     return ApiResponse.<List<Conversation>>builder()
    //                         .result(conversationService.getConversationIdByUserId(userId))
    //                         .build();
    // }

    // lấy tin nhắn trong hội thoại với id hội thoại
    @GetMapping("/{id}/messages") 
    public ApiResponse<List<MessageResponsedto>> getMessageByGroupId(@PathVariable String id) {

        return ApiResponse.<List<MessageResponsedto>>builder()
                            .result(conversationService.getMessageByGroupId(id))
                            .build();
    }

    //Lấy hội thoại không chứa tin nhắn theo id người dùng
    @GetMapping("/{id}/notMessage")
    public ApiResponse<List<ConversationResponeNotMessage>>
            getConversationWithoutMessage(@PathVariable String id) {
                System.out.println(id + "\n Xuất từ getconversationIdNotMessage");
                return ApiResponse.<List<ConversationResponeNotMessage>>builder()
                                    .code(1000)
                                    .message("Successly!")
                                    .result(conversationService.getConversationByUserIdWithoutMessage(id))
                                    .build();
            }

    @GetMapping("/members/{id}")
    public ApiResponse<List<User>> getMember_in_group(@PathVariable String id) {
        return ApiResponse.<List<User>>builder()
                        .message("Successly!")
                        .result(conversationService.getMember(id))
                        .build();
    }
}
