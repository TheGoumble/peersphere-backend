package dao;

import model.Message;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // CREATE message
    public int addMessage(Message msg) throws SQLException {
        String sql = "INSERT INTO messages (group_id, author_id, content) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, msg.getGroupId());
            ps.setInt(2, msg.getAuthorId());
            ps.setString(3, msg.getContent());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    msg.setMessageId(newId);
                    return newId;
                }
            }
        }
        return -1;
    }

    // LIST latest messages in a group
    public List<Message> getRecentMessages(int groupId, int limit) throws SQLException {
        String sql = "SELECT * FROM messages WHERE group_id = ? ORDER BY timestamp DESC LIMIT ?";

        List<Message> messages = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapRow(rs));
                }
            }
        }

        return messages;
    }

    private Message mapRow(ResultSet rs) throws SQLException {
        Message m = new Message();
        m.setMessageId(rs.getInt("message_id"));
        m.setGroupId(rs.getInt("group_id"));
        m.setAuthorId(rs.getInt("author_id"));
        m.setContent(rs.getString("content"));

        Timestamp ts = rs.getTimestamp("timestamp");
        if (ts != null) {
            m.setTimestamp(ts.toLocalDateTime());
        }

        return m;
    }
}