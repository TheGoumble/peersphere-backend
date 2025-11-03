package model;

import java.time.LocalDateTime;

public class Membership {
    private int userId;
    private int groupId;
    private boolean isAdmin;
    private LocalDateTime joinedAt;

    public Membership() {}

    public Membership(int userId, int groupId, boolean isAdmin, LocalDateTime joinedAt) {
        this.userId = userId;
        this.groupId = groupId;
        this.isAdmin = isAdmin;
        this.joinedAt = joinedAt;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}