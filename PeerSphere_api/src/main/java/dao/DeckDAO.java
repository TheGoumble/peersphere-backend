package dao;

import model.Deck;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeckDAO {

    // CREATE deck
    public int createDeck(Deck deck) throws SQLException {
        String sql = "INSERT INTO decks (group_id, title, description, created_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, deck.getGroupId());
            ps.setString(2, deck.getTitle());
            ps.setString(3, deck.getDescription());
            ps.setInt(4, deck.getCreatedBy());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    deck.setDeckId(newId);
                    return newId;
                }
            }
        }

        return -1;
    }

    // UPDATE deck info (rename / change description)
    public boolean updateDeck(Deck deck) throws SQLException {
        String sql = "UPDATE decks SET title = ?, description = ? WHERE deck_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, deck.getTitle());
            ps.setString(2, deck.getDescription());
            ps.setInt(3, deck.getDeckId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // DELETE a deck
    public boolean deleteDeck(int deckId) throws SQLException {
        String sql = "DELETE FROM decks WHERE deck_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, deckId);
            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // READ one deck
    public Optional<Deck> getDeckById(int deckId) throws SQLException {
        String sql = "SELECT * FROM decks WHERE deck_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, deckId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    // LIST all decks in a group
    public List<Deck> getDecksByGroup(int groupId) throws SQLException {
        String sql = "SELECT * FROM decks WHERE group_id = ? ORDER BY created_at DESC";

        List<Deck> decks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    decks.add(mapRow(rs));
                }
            }
        }

        return decks;
    }

    private Deck mapRow(ResultSet rs) throws SQLException {
        Deck d = new Deck();
        d.setDeckId(rs.getInt("deck_id"));
        d.setGroupId(rs.getInt("group_id"));
        d.setTitle(rs.getString("title"));
        d.setDescription(rs.getString("description"));
        d.setCreatedBy(rs.getInt("created_by"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            d.setCreatedAt(ts.toLocalDateTime());
        }

        return d;
    }
}