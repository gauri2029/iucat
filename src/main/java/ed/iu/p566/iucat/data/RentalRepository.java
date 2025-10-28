package ed.iu.p566.iucat.data;

import ed.iu.p566.iucat.model.Book;
import ed.iu.p566.iucat.model.Rental;
import ed.iu.p566.iucat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndStatus(User user, String status);
    
    @Query("SELECT r FROM Rental r WHERE r.id = :rentalId AND r.user.id = :userId")
    Optional<Rental> findByIdAndUserId(@Param("rentalId") Long rentalId, @Param("userId") Long userId);
    
    /**
     * finding all extendable rentals for a user
     * book rentls that are active, not overdue, and haven't reached extension limit
     */
    @Query("SELECT r FROM Rental r WHERE r.user = :user " +
           "AND r.status = 'active' " +
           "AND r.returnDate IS NULL " +
           "AND r.dueDate >= CURRENT_DATE " +
           "AND r.extensionCount < 1")
    List<Rental> findExtendableRentalsByUser(@Param("user") User user);
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Rental r WHERE r.id = :rentalId AND r.user.id = :userId")
    boolean existsByIdAndUserId(@Param("rentalId") Long rentalId, @Param("userId") Long userId);
    
    @Query("SELECT r FROM Rental r WHERE r.user = :user AND r.book = :book AND r.status = 'active'")
    Optional<Rental> findActiveRentalByUserAndBook(@Param("user") User user, @Param("book") Book book);
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Rental r WHERE r.user = :user AND r.book = :book AND r.status = 'active'")
    boolean hasActiveRental(@Param("user") User user, @Param("book") Book book);
}