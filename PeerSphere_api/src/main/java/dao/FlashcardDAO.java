package dao;

import model.Flashcard;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlashcardDAO {

    // CREATE flashcard
    public int addFlashcard(Flashcard card) throws SQLException {
        String sql = "INSERT INTO flashcards (deck_id, question, answer, created_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, card.getDeckId());
            ps.setString(2, card.getQuestion());
            ps.setString(3, card.getAnswer());
            ps.setInt(4, card.getCreatedBy());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    card.setCardId(newId);
                    return newId;
                }
            }
        }

        return -1;
    }

    // UPDATE flashcard
    public boolean updateFlashcard(Flashcard card) throws SQLException {
        String sql = "UPDATE flashcards SET question = ?, answer = ? WHERE card_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, card.getQuestion());
            ps.setString(2, card.getAnswer());
            ps.setInt(3, card.getCardId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // DELETE flashcard
    public boolean deleteFlashcard(int cardId) throws SQLException {
        String sql = "DELETE FROM flashcards WHERE card_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cardId);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // READ one
    public Optional<Flashcard> getFlashcardById(int cardId) throws SQLException {
        String sql = "SELECT * FROM flashcards WHERE card_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cardId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    // LIST all flashcards in a deck
    public List<Flashcard> getFlashcardsByDeck(int deckId) throws SQLException {
        String sql = "SELECT * FROM flashcards WHERE deck_id = ? ORDER BY created_at ASC";

        List<Flashcard> cards = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, deckId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cards.add(mapRow(rs));
                }
            }
        }

        return cards;
    }

    private Flashcard mapRow(ResultSet rs) throws SQLException {
        Flashcard f = new Flashcard();
        f.setCardId(rs.getInt("card_id"));
        f.setDeckId(rs.getInt("deck_id"));
        f.setQuestion(rs.getString("question"));
        f.setAnswer(rs.getString("answer"));
        f.setCreatedBy(rs.getInt("created_by"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            f.setCreatedAt(ts.toLocalDateTime());
        }

        return f;
    }
}