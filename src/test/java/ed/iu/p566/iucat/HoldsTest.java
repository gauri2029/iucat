package ed.iu.p566.iucat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ed.iu.p566.iucat.model.Book;
import ed.iu.p566.iucat.model.Hold;
import ed.iu.p566.iucat.model.User;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class HoldsTest {
    
    private User user;
    private Book book;
    private Hold hold;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "USER");
        book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3);
        hold = new Hold(user, book, LocalDate.now(), "pending", 1);
    }
    
    @Test
    void testHoldCreation() {
        assertNotNull(hold);
        assertEquals(user, hold.getUser());
        assertEquals(book, hold.getBook());
        assertEquals(LocalDate.now(), hold.getHoldDate());
        assertEquals("pending", hold.getStatus());
        assertEquals(1, hold.getQueuePosition());
    }
    
    @Test
    void testIsExpired_WhenNoExpirationDate() {
        assertFalse(hold.isExpired());
    }
    
    @Test
    void testIsExpired_WhenNotExpired() {
        hold.setExpirationDate(LocalDate.now().plusDays(7));
        assertFalse(hold.isExpired());
    }
    
    @Test
    void testIsExpired_WhenExpired() {
        hold.setExpirationDate(LocalDate.now().minusDays(1));
        assertTrue(hold.isExpired());
    }
    
    @Test
    void testIsReady_WhenStatusReady() {
        hold.setStatus("ready");
        assertTrue(hold.isReady());
    }
    
    @Test
    void testIsReady_WhenStatusPending() {
        assertFalse(hold.isReady());
    }
    
    @Test
    void testIsPending_WhenStatusPending() {
        assertTrue(hold.isPending());
    }
    
    @Test
    void testIsPending_WhenStatusReady() {
        hold.setStatus("ready");
        assertFalse(hold.isPending());
    }
    
    @Test
    void testSetExpirationDate() {
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        hold.setExpirationDate(expirationDate);
        
        assertEquals(expirationDate, hold.getExpirationDate());
    }
    
    @Test
    void testQueuePositionUpdate() {
        hold.setQueuePosition(5);
        assertEquals(5, hold.getQueuePosition());
        
        hold.setQueuePosition(1);
        assertEquals(1, hold.getQueuePosition());
    }
}