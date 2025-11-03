package dao;

import model.Membership;
import util.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class MembershipDAO {

    // ADD user to group
    public boolean addMember(int userId, int groupId, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO memberships (user_id, group_id, is_admin) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);
            ps.setBoolean(3, isAdmin);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // CHECK membership
    public Optional<Membership> getMembership(int userId, int groupId) throws SQLException {
        String sql = "SELECT * FROM memberships WHERE user_id = ? AND group_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    // REMOVE user from group
    public boolean removeMember(int userId, int groupId) throws SQLException {
        String sql = "DELETE FROM memberships WHERE user_id = ? AND group_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, groupId);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    // PROMOTE / DEMOTE admin
    public boolean setAdmin(int userId, int groupId, boolean isAdmin) throws SQLException {
        String sql = "UPDATE memberships SET is_admin = ? WHERE user_id = ? AND group_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, isAdmin);
            ps.setInt(2, userId);
            ps.setInt(3, groupId);

            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    private Membership mapRow(ResultSet rs) throws SQLException {
        Membership m = new Membership();
        m.setUserId(rs.getInt("user_id"));
        m.setGroupId(rs.getInt("group_id"));
        m.setAdmin(rs.getBoolean("is_admin"));

        Timestamp ts = rs.getTimestamp("joined_at");
        if (ts != null) {
            m.setJoinedAt(ts.toLocalDateTime());
        }

        return m;
    }
}