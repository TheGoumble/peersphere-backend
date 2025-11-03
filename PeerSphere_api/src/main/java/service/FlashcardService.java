package service;

import dao.FlashcardDAO;
import model.Flashcard;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlashcardService {
    private final FlashcardDAO flashcardDAO;

    public FlashcardService(FlashcardDAO flashcardDAO) {
        this.flashcardDAO = flashcardDAO;
    }

    public int addCard(int deckId, String question, String answer, int createdBy) throws SQLException {
        Flashcard c = new Flashcard();
        c.setDeckId(deckId);
        c.setQuestion(question);
        c.setAnswer(answer);
        c.setCreatedBy(createdBy);
        return flashcardDAO.addFlashcard(c);
    }

    public boolean updateCard(int cardId, String question, String answer) throws SQLException {
        Optional<Flashcard> c = flashcardDAO.getFlashcardById(cardId);
        if (c.isEmpty()) return false;
        Flashcard upd = c.get();
        upd.setQuestion(question);
        upd.setAnswer(answer);
        return flashcardDAO.updateFlashcard(upd);
    }

    public boolean deleteCard(int cardId) throws SQLException {
        return flashcardDAO.deleteFlashcard(cardId);
    }

    public Optional<Flashcard> getCard(int cardId) throws SQLException {
        return flashcardDAO.getFlashcardById(cardId);
    }

    public List<Flashcard> listCardsByDeck(int deckId) throws SQLException {
        return flashcardDAO.getFlashcardsByDeck(deckId);
    }
}