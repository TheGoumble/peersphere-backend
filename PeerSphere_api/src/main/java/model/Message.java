package model;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int groupId;
    private int authorId;
    private String content;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(int messageId, int groupId, int authorId,
                   String content, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.groupId = groupId;
        this.authorId = authorId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}