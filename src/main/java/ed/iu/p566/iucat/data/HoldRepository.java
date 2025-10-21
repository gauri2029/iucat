package ed.iu.p566.iucat.data;

import ed.iu.p566.iucat.model.Book;
import ed.iu.p566.iucat.model.Hold;
import ed.iu.p566.iucat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoldRepository extends JpaRepository<Hold, Long> {
    
    List<Hold> findByUserAndStatus(User user, String status);
    
    @Query("SELECT h FROM Hold h WHERE h.user = :user AND h.status IN ('pending', 'ready') ORDER BY h.holdDate")
    List<Hold> findActiveHoldsByUser(@Param("user") User user);
    
    List<Hold> findByBookAndStatusOrderByQueuePosition(Book book, String status);
    
    @Query("SELECT COUNT(h) FROM Hold h WHERE h.book = :book AND h.status = 'pending'")
    Long countPendingHoldsByBook(@Param("book") Book book);
    
    @Query("SELECT h FROM Hold h WHERE h.user = :user AND h.book = :book AND h.status IN ('pending', 'ready')")
    Optional<Hold> findActiveHoldByUserAndBook(@Param("user") User user, @Param("book") Book book);
    
    @Query("SELECT h FROM Hold h WHERE h.status = 'ready' AND h.expirationDate < :date")
    List<Hold> findExpiredReadyHolds(@Param("date") LocalDate date);
    
    Optional<Hold> findByUserAndBook(User user, Book book);
}