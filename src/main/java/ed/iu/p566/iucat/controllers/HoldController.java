package ed.iu.p566.iucat.controllers;

import ed.iu.p566.iucat.data.BookRepository;
import ed.iu.p566.iucat.data.HoldRepository;
import ed.iu.p566.iucat.data.RentalRepository;
import ed.iu.p566.iucat.model.Book;
import ed.iu.p566.iucat.model.Hold;
import ed.iu.p566.iucat.model.Rental;
import ed.iu.p566.iucat.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class HoldController {
    
    @Autowired
    private HoldRepository holdRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @PostMapping("/holds/place/{bookId}")
    public String placeHold(@PathVariable Long bookId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
            
            Optional<Hold> existingHold = holdRepository.findActiveHoldByUserAndBook(loggedInUser, book);
            if (existingHold.isPresent()) {
                return "redirect:/books/" + bookId + "?error=alreadyHold";
            }
            
            Long pendingCount = holdRepository.countPendingHoldsByBook(book);
            Integer queuePosition = pendingCount.intValue() + 1;
            
            Hold hold = new Hold(loggedInUser, book, LocalDate.now(), "pending", queuePosition);
            holdRepository.save(hold);
            
            return "redirect:/my-holds?success=placed";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books/" + bookId + "?error=true";
        }
    }
    
    @PostMapping("/holds/cancel/{holdId}")
    public String cancelHold(@PathVariable Long holdId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            Hold hold = holdRepository.findById(holdId)
                .orElseThrow(() -> new RuntimeException("Hold not found"));
            
            if (!hold.getUser().getId().equals(loggedInUser.getId())) {
                return "redirect:/my-holds?error=unauthorized";
            }
            
            Book book = hold.getBook();
            String originalStatus = hold.getStatus();
            
            holdRepository.delete(hold);
            
            if ("ready".equals(originalStatus)) {
                processNextHoldOrMakeAvailable(book);
            } else if ("pending".equals(originalStatus)) {
                updateQueuePositions(book);
            }
            
            return "redirect:/my-holds?success=cancelled";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/my-holds?error=true";
        }
    }
    
    @PostMapping("/holds/pickup/{holdId}")
    public String pickupHold(@PathVariable Long holdId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            Hold hold = holdRepository.findById(holdId)
                .orElseThrow(() -> new RuntimeException("Hold not found"));
            
            if (!hold.getUser().getId().equals(loggedInUser.getId())) {
                return "redirect:/my-holds?error=unauthorized";
            }
            
            if (!"ready".equals(hold.getStatus())) {
                return "redirect:/my-holds?error=notReady";
            }
            
            if (hold.isExpired()) {
                hold.setStatus("expired");
                holdRepository.save(hold);
                processNextHoldOrMakeAvailable(hold.getBook());
                return "redirect:/my-holds?error=expired";
            }
            
            Book book = hold.getBook();
            Rental rental = new Rental(loggedInUser, book, LocalDate.now(), LocalDate.now().plusDays(14));
            rentalRepository.save(rental);
            
            hold.setStatus("fulfilled");
            holdRepository.save(hold);            
            return "redirect:/my-rentals?success=pickedUp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/my-holds?error=true";
        }
    }
    
    @GetMapping("/my-holds")
    public String myHolds(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            expireHolds();
            
            List<Hold> activeHolds = holdRepository.findActiveHoldsByUser(loggedInUser);
            model.addAttribute("holds", activeHolds);
            return "my-holds";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("holds", List.of());
            return "my-holds";
        }
    }
    
    private void expireHolds() {
        List<Hold> expiredHolds = holdRepository.findExpiredReadyHolds(LocalDate.now());
        for (Hold hold : expiredHolds) {
            hold.setStatus("expired");
            holdRepository.save(hold);
            
            processNextHoldOrMakeAvailable(hold.getBook());
        }
    }
    
    private void processNextHoldOrMakeAvailable(Book book) {
        List<Hold> pendingHolds = holdRepository.findByBookAndStatusOrderByQueuePosition(book, "pending");
        
        if (!pendingHolds.isEmpty()) {
            Hold nextHold = pendingHolds.get(0);
            nextHold.setStatus("ready");
            nextHold.setExpirationDate(LocalDate.now().plusDays(7));
            nextHold.setQueuePosition(0);
            holdRepository.save(nextHold);
            
            for (int i = 1; i < pendingHolds.size(); i++) {
                Hold h = pendingHolds.get(i);
                h.setQueuePosition(i);
                holdRepository.save(h);
            }
        } else {
            book.incrementAvailableCopies();
            bookRepository.save(book);
        }
    }
    
    private void updateQueuePositions(Book book) {
        List<Hold> pendingHolds = holdRepository.findByBookAndStatusOrderByQueuePosition(book, "pending");
        for (int i = 0; i < pendingHolds.size(); i++) {
            Hold h = pendingHolds.get(i);
            h.setQueuePosition(i + 1);
            holdRepository.save(h);
        }
    }
}