package ed.iu.p566.iucat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class RentalTest {
    
    private User user;
    private Book book;
    private Rental rental;
    
    @BeforeEach
    void setUp() {
        user = new User("testuser", "password123", "USER");
        book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3);
        rental = new Rental(user, book, LocalDate.now(), LocalDate.now().plusDays(14));
    }
    
    @Test
    void testRentalCreation() {
        assertNotNull(rental);
        assertEquals(user, rental.getUser());
        assertEquals(book, rental.getBook());
        assertEquals("active", rental.getStatus());
        assertEquals(0, rental.getExtensionCount());
        assertEquals(2, rental.getExtensionLimit());
    }
    
    @Test
    void testRentalCreationWithCustomExtensionLimit() {
        Rental customRental = new Rental(user, book, LocalDate.now(), 
                                        LocalDate.now().plusDays(14), 5);
        
        assertEquals(5, customRental.getExtensionLimit());
    }
    
    @Test
    void testIsOverdue_WhenNotOverdue() {
        assertFalse(rental.isOverdue());
    }
    
    @Test
    void testIsOverdue_WhenOverdue() {
        rental.setDueDate(LocalDate.now().minusDays(1));
        assertTrue(rental.isOverdue());
    }
    
    @Test
    void testIsOverdue_WhenReturned() {
        rental.setDueDate(LocalDate.now().minusDays(1));
        rental.markReturned();
        assertFalse(rental.isOverdue());
    }
    
    @Test
    void testMarkReturned() {
        rental.markReturned();
        
        assertEquals("returned", rental.getStatus());
        assertNotNull(rental.getReturnDate());
        assertEquals(LocalDate.now(), rental.getReturnDate());
    }
    
    @Test
    void testCanExtend_WhenEligible() {
        assertTrue(rental.canExtend());
    }
    
    @Test
    void testCanExtend_WhenOverdue() {
        rental.setDueDate(LocalDate.now().minusDays(1));
        assertFalse(rental.canExtend());
    }
    
    @Test
    void testCanExtend_WhenReturned() {
        rental.markReturned();
        assertFalse(rental.canExtend());
    }
    
    @Test
    void testCanExtend_WhenExtensionLimitReached() {
        rental.setExtensionCount(2);
        assertFalse(rental.canExtend());
    }
    
    @Test
    void testExtendDueDate_Success() {
        LocalDate originalDueDate = rental.getDueDate();
        boolean result = rental.extendDueDate();
        
        assertTrue(result);
        assertEquals(originalDueDate.plusDays(14), rental.getDueDate());
        assertEquals(1, rental.getExtensionCount());
    }
    
    @Test
    void testExtendDueDate_Failure_WhenOverdue() {
        rental.setDueDate(LocalDate.now().minusDays(1));
        boolean result = rental.extendDueDate();
        
        assertFalse(result);
        assertEquals(0, rental.getExtensionCount());
    }
    
    @Test
    void testExtendDueDate_MultipleExtensions() {
        rental.extendDueDate();
        assertEquals(1, rental.getExtensionCount());
        
        rental.extendDueDate();
        assertEquals(2, rental.getExtensionCount());
        
        boolean thirdExtension = rental.extendDueDate();
        assertFalse(thirdExtension);
        assertEquals(2, rental.getExtensionCount());
    }
    
    @Test
    void testIsExtensionLimitReached() {
        assertFalse(rental.isExtensionLimitReached());
        
        rental.setExtensionCount(2);
        assertTrue(rental.isExtensionLimitReached());
    }
    
    @Test
    void testGetRemainingExtensions() {
        assertEquals(2, rental.getRemainingExtensions());
        
        rental.setExtensionCount(1);
        assertEquals(1, rental.getRemainingExtensions());
        
        rental.setExtensionCount(2);
        assertEquals(0, rental.getRemainingExtensions());
    }
    
    @Test
    void testGetExtensionDays() {
        assertEquals(14, Rental.getExtensionDays());
    }
}