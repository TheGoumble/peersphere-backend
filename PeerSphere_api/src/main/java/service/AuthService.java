package service;

import dao.UserDAO;
import model.User;
import util.PasswordHasher;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int register(String name, String email, String rawPassword) throws SQLException {
        if (userDAO.getUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash(PasswordHasher.hash(rawPassword));
        return userDAO.createUser(u);
    }

    public Optional<User> login(String email, String rawPassword) throws SQLException {
        Optional<User> u = userDAO.getUserByEmail(email);
        if (u.isEmpty()) return Optional.empty();
        String hashed = PasswordHasher.hash(rawPassword);
        return hashed.equals(u.get().getPasswordHash()) ? u : Optional.empty();
    }
}