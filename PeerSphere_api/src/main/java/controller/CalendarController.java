package controller;

import model.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CalendarService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    
    @Autowired
    private CalendarService calendarService;
    
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequest request) {
        try {
            int eventId = calendarService.createEvent(
                    request.getGroupId(),
                    request.getTitle(),
                    request.getDescription(),
                    LocalDateTime.parse(request.getStartTime()),
                    LocalDateTime.parse(request.getEndTime()),
                    request.getCreatedBy()
            );
            return ResponseEntity.ok().body(new EventIdResponse(eventId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid date format: " + e.getMessage()));
        }
    }
    
    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getEventsByGroup(@PathVariable int groupId) {
        try {
            List<CalendarEvent> events = calendarService.listEventsByGroup(groupId);
            return ResponseEntity.ok(events);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable int eventId) {
        try {
            Optional<CalendarEvent> event = calendarService.getEvent(eventId);
            if (event.isPresent()) {
                return ResponseEntity.ok(event.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable int eventId, @RequestBody UpdateEventRequest request) {
        try {
            boolean updated = calendarService.updateEvent(
                    eventId,
                    request.getTitle(),
                    request.getDescription(),
                    LocalDateTime.parse(request.getStartTime()),
                    LocalDateTime.parse(request.getEndTime())
            );
            if (updated) {
                return ResponseEntity.ok().body(new SuccessResponse("Event updated successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid date format: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable int eventId) {
        try {
            boolean deleted = calendarService.deleteEvent(eventId);
            if (deleted) {
                return ResponseEntity.ok().body(new SuccessResponse("Event deleted successfully"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class CreateEventRequest {
        private int groupId;
        private String title;
        private String description;
        private String startTime;
        private String endTime;
        private int createdBy;
        
        public int getGroupId() { return groupId; }
        public void setGroupId(int groupId) { this.groupId = groupId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
        public int getCreatedBy() { return createdBy; }
        public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    }
    
    private static class UpdateEventRequest {
        private String title;
        private String description;
        private String startTime;
        private String endTime;
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }
        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
    }
    
    private static class EventIdResponse {
        private int eventId;
        public EventIdResponse(int eventId) { this.eventId = eventId; }
        public int getEventId() { return eventId; }
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


