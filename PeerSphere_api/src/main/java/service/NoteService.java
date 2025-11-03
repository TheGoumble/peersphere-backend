package service;

import dao.NoteDAO;
import model.Note;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class NoteService {
    private final NoteDAO noteDAO;

    public NoteService(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    public int createNote(int groupId, int authorId, String title, String content) throws SQLException {
        Note n = new Note();
        n.setGroupId(groupId);
        n.setAuthorId(authorId);
        n.setTitle(title);
        n.setContent(content);
        return noteDAO.createNote(n);
    }

    public boolean updateNote(int noteId, String title, String content) throws SQLException {
        Optional<Note> n = noteDAO.getNoteById(noteId);
        if (n.isEmpty()) return false;
        Note upd = n.get();
        upd.setTitle(title);
        upd.setContent(content);
        return noteDAO.updateNote(upd);
    }

    public boolean deleteNote(int noteId) throws SQLException {
        return noteDAO.deleteNote(noteId);
    }

    public Optional<Note> getNote(int noteId) throws SQLException {
        return noteDAO.getNoteById(noteId);
    }

    public List<Note> listNotesByGroup(int groupId) throws SQLException {
        return noteDAO.getNotesByGroup(groupId);
    }
}