package model;

import java.time.LocalDateTime;

public class Group {
    private int groupId;
    private String groupName;
    private String courseCode;
    private String groupCode;   // join code
    private int createdBy;      // user_id of creator
    private LocalDateTime createdAt;

    public Group() {}

    public Group(int groupId, String groupName, String courseCode,
                 String groupCode, int createdBy, LocalDateTime createdAt) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.courseCode = courseCode;
        this.groupCode = groupCode;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public int getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}