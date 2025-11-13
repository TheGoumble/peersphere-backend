package controller;

import model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DeckService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
    
    @Autowired
    private DeckService deckService;
    
    @PostMapping
    public ResponseEntity<?> createDeck(@RequestBody CreateDeckRequest request) {
        try {
            int deckId = deckService.createDeck(request.getGroupId(), request.getTitle(), 
                    request.getDescription(), request.getCreatedBy());
            return ResponseEntity.ok().body(new DeckIdResponse(deckId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getDecksByGroup(@PathVariable int groupId) {
        try {
            List<Deck> decks = deckService.listDecksByGroup(groupId);
            return ResponseEntity.ok(decks);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{deckId}")
    public ResponseEntity<?> getDeck(@PathVariable int deckId) {
        try {
            Optional<Deck> deck = deckService.getDeck(deckId);
            if (deck.isPresent()) {
                return ResponseEntity.ok(deck.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{deckId}")
    public ResponseEntity<?> updateDeck(@PathVariable int deckId, @RequestBody UpdateDeckRequest request) {
        try {
            boolean updated = deckService.renameDeck(deckId, request.getTitle(), request.getDescription());
            if (updated) {
                return ResponseEntity.ok().body(new SuccessResponse("Deck updated successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{deckId}")
    public ResponseEntity<?> deleteDeck(@PathVariable int deckId) {
        try {
            boolean deleted = deckService.deleteDeck(deckId);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Deck deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class CreateDeckRequest {
        private int groupId;
        private String title;
        private String description;
        private int createdBy;
        
        public int getGroupId() { return groupId; }
        public void setGroupId(int groupId) { this.groupId = groupId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public int getCreatedBy() { return createdBy; }
        public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    }
    
    private static class UpdateDeckRequest {
        private String title;
        private String description;
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    private static class DeckIdResponse {
        private int deckId;
        public DeckIdResponse(int deckId) { this.deckId = deckId; }
        public int getDeckId() { return deckId; }
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


