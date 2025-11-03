package ed.iu.p566.iucat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    
    private Book book;
    
    @BeforeEach
    void setUp() {
        book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3);
    }
    
    @Test
    void testBookCreation() {
        assertNotNull(book);
        assertEquals("978-0134685991", book.getIsbn());
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(3, book.getTotalCopies());
        assertEquals(3, book.getAvailableCopies());
    }
    
    @Test
    void testBookCreationWithAllFields() {
        Book detailedBook = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 
                                     3, 2018, "English", "Book", "Programming");
        
        assertEquals(2018, detailedBook.getPublicationYear());
        assertEquals("English", detailedBook.getLanguage());
        assertEquals("Book", detailedBook.getFormat());
        assertEquals("Programming", detailedBook.getSubject());
    }
    
    @Test
    void testIsAvailable_WhenCopiesAvailable() {
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testIsAvailable_WhenNoCopiesAvailable() {
        book.setAvailableCopies(0);
        assertFalse(book.isAvailable());
    }
    
    @Test
    void testDecrementAvailableCopies() {
        int initialCopies = book.getAvailableCopies();
        book.decrementAvailableCopies();
        
        assertEquals(initialCopies - 1, book.getAvailableCopies());
    }
    
    @Test
    void testDecrementAvailableCopies_WhenZero() {
        book.setAvailableCopies(0);
        book.decrementAvailableCopies();
        
        assertEquals(0, book.getAvailableCopies());
    }
    
    @Test
    void testIncrementAvailableCopies() {
        book.setAvailableCopies(1);
        book.incrementAvailableCopies();
        
        assertEquals(2, book.getAvailableCopies());
    }
    
    @Test
    void testIncrementAvailableCopies_CannotExceedTotal() {
        book.setAvailableCopies(3);
        book.incrementAvailableCopies();
        
        assertEquals(3, book.getAvailableCopies());
    }
}