package ed.iu.p566.iucat.data;
import ed.iu.p566.iucat.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooks(@Param("query") String query);
    
    // Search by Title only
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> searchByTitle(@Param("title") String title);
    
    // Search by Author only
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> searchByAuthor(@Param("author") String author);
    
    // Search by Subject (ISBN or Subject field)
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :subject, '%')) OR " +
           "LOWER(b.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Book> searchBySubject(@Param("subject") String subject);
    
    List<Book> findByAvailableCopiesGreaterThan(Integer copies);
    
    @Query("SELECT DISTINCT b.author FROM Book b ORDER BY b.author")
    List<String> findDistinctAuthors();
    
    @Query("SELECT DISTINCT b.subject FROM Book b ORDER BY b.subject")
    List<String> findDistinctSubjects();
    
    @Query("SELECT DISTINCT b.format FROM Book b ORDER BY b.format")
    List<String> findDistinctFormats();
    
    @Query("SELECT DISTINCT b.language FROM Book b ORDER BY b.language")
    List<String> findDistinctLanguages();
    
    @Query("SELECT DISTINCT b.publicationYear FROM Book b ORDER BY b.publicationYear DESC")
    List<Integer> findDistinctPublicationYears();
}