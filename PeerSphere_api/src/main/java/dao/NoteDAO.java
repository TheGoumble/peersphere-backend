package dao;

import model.Note;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDAO {

    // CREATE note
    public int createNote(Note note) throws SQLException {
        String sql = "INSERT INTO notes (group_id, author_id, title, content) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, note.getGroupId());
            ps.setInt(2, note.getAuthorId());
            ps.setString(3, note.getTitle());
            ps.setString(4, note.getContent());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    note.setNoteId(newId);
                    return newId;
                }
            }
        }

        return -1;
    }

    // UPDATE note
    public boolean updateNote(Note note) throws SQLException {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE note_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getNoteId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // DELETE note
    public boolean deleteNote(int noteId) throws SQLException {
        String sql = "DELETE FROM notes WHERE note_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, noteId);
            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // READ one
    public Optional<Note> getNoteById(int noteId) throws SQLException {
        String sql = "SELECT * FROM notes WHERE note_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, noteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    // LIST notes in a group
    public List<Note> getNotesByGroup(int groupId) throws SQLException {
        String sql = "SELECT * FROM notes WHERE group_id = ? ORDER BY updated_at DESC";

        List<Note> notes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notes.add(mapRow(rs));
                }
            }
        }

        return notes;
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        Note n = new Note();
        n.setNoteId(rs.getInt("note_id"));
        n.setGroupId(rs.getInt("group_id"));
        n.setAuthorId(rs.getInt("author_id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));

        Timestamp ts = rs.getTimestamp("updated_at");
        if (ts != null) {
            n.setUpdatedAt(ts.toLocalDateTime());
        }

        return n;
    }
}