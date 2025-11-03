package model;

import java.time.LocalDateTime;

public class Flashcard {
    private int cardId;
    private int deckId;
    private String question;
    private String answer;
    private int createdBy;
    private LocalDateTime createdAt;

    public Flashcard() {}

    public Flashcard(int cardId, int deckId, String question,
                      String answer, int createdBy, LocalDateTime createdAt) {
        this.cardId = cardId;
        this.deckId = deckId;
        this.question = question;
        this.answer = answer;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getCardId() {
        return cardId;
    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
    public int getDeckId() {
        return deckId;
    }
    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
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