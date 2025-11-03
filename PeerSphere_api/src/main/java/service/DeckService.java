package service;

import dao.DeckDAO;
import model.Deck;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeckService {
    private final DeckDAO deckDAO;

    public DeckService(DeckDAO deckDAO) {
        this.deckDAO = deckDAO;
    }

    public int createDeck(int groupId, String title, String description, int createdBy) throws SQLException {
        Deck d = new Deck();
        d.setGroupId(groupId);
        d.setTitle(title);
        d.setDescription(description);
        d.setCreatedBy(createdBy);
        return deckDAO.createDeck(d);
    }

    public boolean renameDeck(int deckId, String newTitle, String newDesc) throws SQLException {
        Optional<Deck> d = deckDAO.getDeckById(deckId);
        if (d.isEmpty()) return false;
        Deck upd = d.get();
        upd.setTitle(newTitle);
        upd.setDescription(newDesc);
        return deckDAO.updateDeck(upd);
    }

    public boolean deleteDeck(int deckId) throws SQLException {
        return deckDAO.deleteDeck(deckId);
    }

    public Optional<Deck> getDeck(int deckId) throws SQLException {
        return deckDAO.getDeckById(deckId);
    }

    public List<Deck> listDecksByGroup(int groupId) throws SQLException {
        return deckDAO.getDecksByGroup(groupId);
    }
}