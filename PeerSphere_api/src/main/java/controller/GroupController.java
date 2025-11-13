package controller;

import dto.JoinGroupRequest;
import model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.GroupService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    
    @Autowired
    private GroupService groupService;
    
    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            int groupId = groupService.createGroup(request.getName(), request.getCourseCode(), request.getCreatorUserId());
            return ResponseEntity.ok().body(new GroupIdResponse(groupId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestParam int userId, @RequestBody JoinGroupRequest request) {
        try {
            boolean joined = groupService.joinByCode(userId, request.getGroupCode());
            if (joined) {
                return ResponseEntity.ok().body(new SuccessResponse("Successfully joined group"));
            } else {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid group code"));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserGroups(@PathVariable int userId) {
        try {
            List<Group> groups = groupService.listGroupsForUser(userId);
            return ResponseEntity.ok(groups);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable int groupId) {
        try {
            Optional<Group> group = groupService.getById(groupId);
            if (group.isPresent()) {
                return ResponseEntity.ok(group.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class CreateGroupRequest {
        private String name;
        private String courseCode;
        private int creatorUserId;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public int getCreatorUserId() { return creatorUserId; }
        public void setCreatorUserId(int creatorUserId) { this.creatorUserId = creatorUserId; }
    }
    
    private static class GroupIdResponse {
        private int groupId;
        public GroupIdResponse(int groupId) { this.groupId = groupId; }
        public int getGroupId() { return groupId; }
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


