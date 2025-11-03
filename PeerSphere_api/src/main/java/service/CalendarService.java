package service;

import dao.CalendarEventDAO;
import model.CalendarEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CalendarService {
    private final CalendarEventDAO eventDAO;

    public CalendarService(CalendarEventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public int createEvent(int groupId, String title, String desc,
                           LocalDateTime start, LocalDateTime end, int createdBy) throws SQLException {
        CalendarEvent e = new CalendarEvent();
        e.setGroupId(groupId);
        e.setTitle(title);
        e.setDescription(desc);
        e.setStartTime(start);
        e.setEndTime(end);
        e.setCreatedBy(createdBy);
        return eventDAO.createEvent(e);
    }

    public boolean updateEvent(int eventId, String title, String desc,
                               LocalDateTime start, LocalDateTime end) throws SQLException {
        Optional<CalendarEvent> e = eventDAO.getEventById(eventId);
        if (e.isEmpty()) return false;
        CalendarEvent upd = e.get();
        upd.setTitle(title);
        upd.setDescription(desc);
        upd.setStartTime(start);
        upd.setEndTime(end);
        return eventDAO.updateEvent(upd);
    }

    public boolean deleteEvent(int eventId) throws SQLException {
        return eventDAO.deleteEvent(eventId);
    }

    public Optional<CalendarEvent> getEvent(int eventId) throws SQLException {
        return eventDAO.getEventById(eventId);
    }

    public List<CalendarEvent> listEventsByGroup(int groupId) throws SQLException {
        return eventDAO.getEventsByGroup(groupId);
    }
}