package controller;

import model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MessageService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request) {
        try {
            int messageId = messageService.sendMessage(request.getGroupId(), request.getAuthorId(), 
                    request.getContent());
            return ResponseEntity.ok().body(new MessageIdResponse(messageId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getMessages(@PathVariable int groupId, 
                                         @RequestParam(defaultValue = "50") int limit) {
        try {
            List<Message> messages = messageService.getRecentMessages(groupId, limit);
            return ResponseEntity.ok(messages);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class SendMessageRequest {
        private int groupId;
        private int authorId;
        private String content;
        
        public int getGroupId() { return groupId; }
        public void setGroupId(int groupId) { this.groupId = groupId; }
        public int getAuthorId() { return authorId; }
        public void setAuthorId(int authorId) { this.authorId = authorId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    private static class MessageIdResponse {
        private int messageId;
        public MessageIdResponse(int messageId) { this.messageId = messageId; }
        public int getMessageId() { return messageId; }
    }
    
    private static class ErrorResponse {
        private String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
    }
}


