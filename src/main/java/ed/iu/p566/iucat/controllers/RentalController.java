package ed.iu.p566.iucat.controllers;

import ed.iu.p566.iucat.data.BookRepository;
import ed.iu.p566.iucat.data.RentalRepository;
import ed.iu.p566.iucat.model.Book;
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

@Controller
public class RentalController {
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @PostMapping("/rent/{bookId}")
    public String rentBook(@PathVariable Long bookId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
            
            if (book.getAvailableCopies() > 0) {
                Rental rental = new Rental(loggedInUser, book, LocalDate.now(), LocalDate.now().plusDays(14));
                rentalRepository.save(rental);
                
                book.decrementAvailableCopies();
                bookRepository.save(book);
            }
            
            return "redirect:/my-rentals";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/books/" + bookId + "?error=true";
        }
    }
    
    @PostMapping("/return/{rentalId}")
    public String returnBook(@PathVariable Long rentalId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
            
            if (!rental.getUser().getId().equals(loggedInUser.getId())) {
                return "redirect:/my-rentals?error=unauthorized";
            }
            
            rental.markReturned();
            rentalRepository.save(rental);
            
            Book book = rental.getBook();
            book.incrementAvailableCopies();
            bookRepository.save(book);
            
            return "redirect:/my-rentals?success=returned";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/my-rentals?error=true";
        }
    }
    
    @GetMapping("/my-rentals")
    public String myRentals(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        try {
            List<Rental> activeRentals = rentalRepository.findByUserAndStatus(loggedInUser, "active");
            model.addAttribute("rentals", activeRentals);
            return "my-rentals";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("rentals", List.of());
            return "my-rentals";
        }
    }
}