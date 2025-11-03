package dao;

import model.CalendarEvent;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CalendarEventDAO {

    // CREATE event
    public int createEvent(CalendarEvent event) throws SQLException {
        String sql = "INSERT INTO calendar_events " +
                     "(group_id, title, description, start_time, end_time, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, event.getGroupId());
            ps.setString(2, event.getTitle());
            ps.setString(3, event.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(event.getStartTime()));
            ps.setTimestamp(5, Timestamp.valueOf(event.getEndTime()));
            ps.setInt(6, event.getCreatedBy());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    event.setEventId(newId);
                    return newId;
                }
            }
        }

        return -1;
    }

    // UPDATE event
    public boolean updateEvent(CalendarEvent event) throws SQLException {
        String sql = "UPDATE calendar_events " +
                     "SET title = ?, description = ?, start_time = ?, end_time = ? " +
                     "WHERE event_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(event.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(event.getEndTime()));
            ps.setInt(5, event.getEventId());

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // DELETE event
    public boolean deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM calendar_events WHERE event_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // READ one
    public Optional<CalendarEvent> getEventById(int eventId) throws SQLException {
        String sql = "SELECT * FROM calendar_events WHERE event_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    // LIST events by group
    public List<CalendarEvent> getEventsByGroup(int groupId) throws SQLException {
        String sql = "SELECT * FROM calendar_events WHERE group_id = ? ORDER BY start_time ASC";

        List<CalendarEvent> events = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    events.add(mapRow(rs));
                }
            }
        }

        return events;
    }

    private CalendarEvent mapRow(ResultSet rs) throws SQLException {
        CalendarEvent e = new CalendarEvent();

        e.setEventId(rs.getInt("event_id"));
        e.setGroupId(rs.getInt("group_id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));

        Timestamp startTs = rs.getTimestamp("start_time");
        if (startTs != null) {
            e.setStartTime(startTs.toLocalDateTime());
        }

        Timestamp endTs = rs.getTimestamp("end_time");
        if (endTs != null) {
            e.setEndTime(endTs.toLocalDateTime());
        }

        e.setCreatedBy(rs.getInt("created_by"));

        Timestamp createdTs = rs.getTimestamp("created_at");
        if (createdTs != null) {
            e.setCreatedAt(createdTs.toLocalDateTime());
        }

        return e;
    }
}