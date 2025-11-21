package controller;

import dto.LoginRequest;
import dto.RegisterRequest;
import dto.UserResponse;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthService;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            int userId = authService.register(request.getName(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(new RegisterResponse(userId, "Registration successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> user = authService.login(request.getEmail(), request.getPassword());
            if (user.isPresent()) {
                User u = user.get();
                UserResponse response = new UserResponse(
                    u.getUserId(),
                    u.getName(),
                    u.getEmail(),
                    u.getCreatedAt()
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid email or password"));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Database error: " + e.getMessage()));
        }
    }
    
    private static class RegisterResponse {
        private int userId;
        private String message;
        
        public RegisterResponse(int userId, String message) {
            this.userId = userId;
            this.message = message;
        }
        
        public int getUserId() { return userId; }
        public String getMessage() { return message; }
    }
    
    private static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() { return error; }
    }
}

