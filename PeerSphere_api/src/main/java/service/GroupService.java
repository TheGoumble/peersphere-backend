package service;

import dao.GroupDAO;
import dao.MembershipDAO;
import model.Group;
import model.Membership;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GroupService {
    private final GroupDAO groupDAO;
    private final MembershipDAO membershipDAO;

    public GroupService(GroupDAO groupDAO, MembershipDAO membershipDAO) {
        this.groupDAO = groupDAO;
        this.membershipDAO = membershipDAO;
    }

    public int createGroup(String name, String courseCode, int creatorUserId) throws SQLException {
        Group g = new Group();
        g.setGroupName(name);
        g.setCourseCode(courseCode);
        g.setGroupCode(generateJoinCode());
        g.setCreatedBy(creatorUserId);

        int groupId = groupDAO.createGroup(g);
        membershipDAO.addMember(creatorUserId, groupId, true); // creator is admin
        return groupId;
    }

    public boolean joinByCode(int userId, String groupCode) throws SQLException {
        Optional<Group> g = groupDAO.getGroupByJoinCode(groupCode);
        if (g.isEmpty()) return false;
        return membershipDAO.addMember(userId, g.get().getGroupId(), false);
    }

    public List<Group> listGroupsForUser(int userId) throws SQLException {
        return groupDAO.getGroupsForUser(userId);
    }

    private String generateJoinCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Optional<Group> getById(int groupId) throws SQLException {
        return groupDAO.getGroupById(groupId);
    }

    public Optional<Membership> getMembership(int userId, int groupId) throws SQLException {
        return membershipDAO.getMembership(userId, groupId);
    }
}