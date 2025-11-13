package controller;

import model.Flashcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FlashcardService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
    
    @Autowired
    private FlashcardService flashcardService;
    
    @PostMapping
    public ResponseEntity<?> createFlashcard(@RequestBody CreateFlashcardRequest request) {
        try {
            int cardId = flashcardService.addCard(request.getDeckId(), request.getQuestion(), 
                    request.getAnswer(), request.getCreatedBy());
            return ResponseEntity.ok().body(new CardIdResponse(cardId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/deck/{deckId}")
    public ResponseEntity<?> getFlashcardsByDeck(@PathVariable int deckId) {
        try {
            List<Flashcard> cards = flashcardService.listCardsByDeck(deckId);
            return ResponseEntity.ok(cards);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{cardId}")
    public ResponseEntity<?> getFlashcard(@PathVariable int cardId) {
        try {
            Optional<Flashcard> card = flashcardService.getCard(cardId);
            if (card.isPresent()) {
                return ResponseEntity.ok(card.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateFlashcard(@PathVariable int cardId, @RequestBody UpdateFlashcardRequest request) {
        try {
            boolean updated = flashcardService.updateCard(cardId, request.getQuestion(), request.getAnswer());
            if (updated) {
                return ResponseEntity.ok().body(new SuccessResponse("Flashcard updated successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable int cardId) {
        try {
            boolean deleted = flashcardService.deleteCard(cardId);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Flashcard deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class CreateFlashcardRequest {
        private int deckId;
        private String question;
        private String answer;
        private int createdBy;
        
        public int getDeckId() { return deckId; }
        public void setDeckId(int deckId) { this.deckId = deckId; }
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
        public int getCreatedBy() { return createdBy; }
        public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    }
    
    private static class UpdateFlashcardRequest {
        private String question;
        private String answer;
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
    }
    
    private static class CardIdResponse {
        private int cardId;
        public CardIdResponse(int cardId) { this.cardId = cardId; }
        public int getCardId() { return cardId; }
    }
    
    private static class SuccessResponse {
        private String message;
        public SuccessResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
    
    private static class ErrorResponse {
        private String error;
        public ErrorResponse(String error) { this.error = error; }
        public String getError() { return error; }
    }
}


