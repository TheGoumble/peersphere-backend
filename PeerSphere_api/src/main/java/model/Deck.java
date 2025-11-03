package model;

import java.time.LocalDateTime;

public class Deck {
    private int deckId;
    private int groupId;
    private String title;
    private String description;
    private int createdBy;
    private LocalDateTime createdAt;

    public Deck() {}

    public Deck(int deckId, int groupId, String title,
                String description, int createdBy, LocalDateTime createdAt) {
        this.deckId = deckId;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getDeckId() {
        return deckId;
    }
    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}