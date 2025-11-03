package model;

import java.time.LocalDateTime;

public class Note {
    private int noteId;
    private int groupId;
    private int authorId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public Note() {}

    public Note(int noteId, int groupId, int authorId,
                String title, String content, LocalDateTime updatedAt) {
        this.noteId = noteId;
        this.groupId = groupId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public int getNoteId() {
        return noteId;
    }
    public void setNoteId(int noteId) {
        this.noteId = noteId;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}