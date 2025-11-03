package dao;

import model.Group;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDAO {

    // CREATE new group
    public int createGroup(Group g) throws SQLException {
        String sql = "INSERT INTO peergroups (group_name, course_code, group_code, created_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, g.getGroupName());
            ps.setString(2, g.getCourseCode());
            ps.setString(3, g.getGroupCode());
            ps.setInt(4, g.getCreatedBy());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    g.setGroupId(newId);
                    return newId;
                }
            }
        }
        return -1;
    }

    // READ group by id
    public Optional<Group> getGroupById(int groupId) throws SQLException {
        String sql = "SELECT * FROM peergroups WHERE group_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    // READ group by join code
    public Optional<Group> getGroupByJoinCode(String groupCode) throws SQLException {
        String sql = "SELECT * FROM peergroups WHERE group_code = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, groupCode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    // LIST peergroups a user is in
    public List<Group> getGroupsForUser(int userId) throws SQLException {
        String sql = """
            SELECT g.*
            FROM peergroups g
            JOIN memberships m ON g.group_id = m.group_id
            WHERE m.user_id = ?
            ORDER BY g.created_at DESC
        """;

        List<Group> peergroups = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    peergroups.add(mapRow(rs));
                }
            }
        }
        return peergroups;
    }

    private Group mapRow(ResultSet rs) throws SQLException {
        Group g = new Group();
        g.setGroupId(rs.getInt("group_id"));
        g.setGroupName(rs.getString("group_name"));
        g.setCourseCode(rs.getString("course_code"));
        g.setGroupCode(rs.getString("group_code"));
        g.setCreatedBy(rs.getInt("created_by"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            g.setCreatedAt(ts.toLocalDateTime());
        }
        return g;
    }
}