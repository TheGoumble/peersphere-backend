package app;

import dao.*;
import model.*;
import service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SeedDemo {
    public static void main(String[] args) {
        try {
            // DAOs
            UserDAO userDAO = new UserDAO();
            GroupDAO groupDAO = new GroupDAO();
            MembershipDAO membershipDAO = new MembershipDAO();
            DeckDAO deckDAO = new DeckDAO();
            FlashcardDAO flashcardDAO = new FlashcardDAO();
            NoteDAO noteDAO = new NoteDAO();
            MessageDAO messageDAO = new MessageDAO();
            CalendarEventDAO eventDAO = new CalendarEventDAO();

            // Services
            AuthService auth = new AuthService(userDAO);
            GroupService peergroups = new GroupService(groupDAO, membershipDAO);
            DeckService decks = new DeckService(deckDAO);
            FlashcardService cards = new FlashcardService(flashcardDAO);
            NoteService notes = new NoteService(noteDAO);
            MessageService chat = new MessageService(messageDAO);
            CalendarService calendar = new CalendarService(eventDAO);

            // 1) Create two users
            int sallyId = auth.register("Sally", "sally@fau.edu", "sallypw");
            int marioId   = auth.register("Mario",   "mario@fau.edu",   "mariopw");
            System.out.println("Users: " + sallyId + ", " + marioId);

            // 2) Create group by Sally
            int groupId = peergroups.createGroup("COP4331 Study", "COP4331", sallyId);
            System.out.println("Group created: " + groupId);

            // 3) Mario joins via code
            Optional<Group> g = peergroups.getById(groupId);
            boolean joined = g.isPresent() && peergroups.joinByCode(marioId, g.get().getGroupCode());
            System.out.println("Mario joined? " + joined);

            // 4) Create a deck + add cards
            int deckId = decks.createDeck(groupId, "Midterm Ch5", "Patterns & UML", sallyId);
            cards.addCard(deckId, "Strategy pattern purpose?", "Encapsulate algorithms; interchangeable.", sallyId);
            cards.addCard(deckId, "Decorator pattern use?", "Add responsibilities dynamically.", sallyId);

            // 5) Note
            int noteId = notes.createNote(groupId, marioId, "Lecture 5 Summary", "Key points on Observer, Composite...");
            System.out.println("Note ID: " + noteId);

            // 6) Message
            int msgId = chat.sendMessage(groupId, sallyId, "Welcome to the group! Start with Deck: Midterm Ch5.");
            System.out.println("Message ID: " + msgId);

            // 7) Calendar event
            int eventId = calendar.createEvent(groupId, "Group Study",
                    "Review Chapter 5 problems", LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(2).plusHours(2), sallyId);
            System.out.println("Event ID: " + eventId);

            // 8) List sanity checks
            List<Deck> groupDecks = decks.listDecksByGroup(groupId);
            System.out.println("Decks in group: " + groupDecks.size());

            System.out.println("Seed demo complete. Backend is alive.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Seed failed: " + e.getMessage());
        }
    }
}