package service;

import dao.MessageDAO;
import model.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public int sendMessage(int groupId, int authorId, String content) throws SQLException {
        Message m = new Message();
        m.setGroupId(groupId);
        m.setAuthorId(authorId);
        m.setContent(content);
        return messageDAO.addMessage(m);
    }

    public List<Message> getRecentMessages(int groupId, int limit) throws SQLException {
        return messageDAO.getRecentMessages(groupId, limit);
    }
}