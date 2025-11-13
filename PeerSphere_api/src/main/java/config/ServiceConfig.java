package config;

import dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.*;

@Configuration
public class ServiceConfig {
    
    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }
    
    @Bean
    public GroupDAO groupDAO() {
        return new GroupDAO();
    }
    
    @Bean
    public MembershipDAO membershipDAO() {
        return new MembershipDAO();
    }
    
    @Bean
    public DeckDAO deckDAO() {
        return new DeckDAO();
    }
    
    @Bean
    public FlashcardDAO flashcardDAO() {
        return new FlashcardDAO();
    }
    
    @Bean
    public NoteDAO noteDAO() {
        return new NoteDAO();
    }
    
    @Bean
    public MessageDAO messageDAO() {
        return new MessageDAO();
    }
    
    @Bean
    public CalendarEventDAO calendarEventDAO() {
        return new CalendarEventDAO();
    }
    
    @Bean
    public AuthService authService() {
        return new AuthService(userDAO());
    }
    
    @Bean
    public GroupService groupService() {
        return new GroupService(groupDAO(), membershipDAO());
    }
    
    @Bean
    public DeckService deckService() {
        return new DeckService(deckDAO());
    }
    
    @Bean
    public FlashcardService flashcardService() {
        return new FlashcardService(flashcardDAO());
    }
    
    @Bean
    public NoteService noteService() {
        return new NoteService(noteDAO());
    }
    
    @Bean
    public MessageService messageService() {
        return new MessageService(messageDAO());
    }
    
    @Bean
    public CalendarService calendarService() {
        return new CalendarService(calendarEventDAO());
    }
}


