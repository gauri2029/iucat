package ed.iu.p566.iucat.data;

import ed.iu.p566.iucat.model.Rental;
import ed.iu.p566.iucat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndStatus(User user, String status);
}