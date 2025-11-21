package controller;

import model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.NoteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;
    
    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest request) {
        try {
            int noteId = noteService.createNote(request.getGroupId(), request.getAuthorId(), 
                    request.getTitle(), request.getContent());
            return ResponseEntity.ok().body(new NoteIdResponse(noteId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getNotesByGroup(@PathVariable int groupId) {
        try {
            List<Note> notes = noteService.listNotesByGroup(groupId);
            return ResponseEntity.ok(notes);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{noteId}")
    public ResponseEntity<?> getNote(@PathVariable int noteId) {
        try {
            Optional<Note> note = noteService.getNote(noteId);
            if (note.isPresent()) {
                return ResponseEntity.ok(note.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable int noteId, @RequestBody UpdateNoteRequest request) {
        try {
            boolean updated = noteService.updateNote(noteId, request.getTitle(), request.getContent());
            if (updated) {
                return ResponseEntity.ok().body(new SuccessResponse("Note updated successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable int noteId) {
        try {
            boolean deleted = noteService.deleteNote(noteId);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Note deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class CreateNoteRequest {
        private int groupId;
        private int authorId;
        private String title;
        private String content;
        
        public int getGroupId() { return groupId; }
        public void setGroupId(int groupId) { this.groupId = groupId; }
        public int getAuthorId() { return authorId; }
        public void setAuthorId(int authorId) { this.authorId = authorId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    private static class UpdateNoteRequest {
        private String title;
        private String content;
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    private static class NoteIdResponse {
        private int noteId;
        public NoteIdResponse(int noteId) { this.noteId = noteId; }
        public int getNoteId() { return noteId; }
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


