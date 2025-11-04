package ed.iu.p566.iucat.controllers;
import ed.iu.p566.iucat.data.RentalRepository;
import ed.iu.p566.iucat.data.UserRepository;
import ed.iu.p566.iucat.model.Rental;
import ed.iu.p566.iucat.model.User;
import ed.iu.p566.iucat.service.EmailService;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RentalRepository rentalRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session,
                       Model model) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);
            
            checkDueBooks(user);
            return "redirect:/search";
        } else {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
    
    /**
     * checking for books due in 2 days
     */
    private void checkDueBooks(User user) {
        LocalDate twoDaysFromNow = LocalDate.now().plusDays(2);
        List<Rental> rentals = rentalRepository.findByUserAndStatus(user, "active");

        for (Rental rental : rentals) {
            if (rental.getDueDate().equals(twoDaysFromNow)) {
                String recipientEmail = user.getUsername().contains("@") 
                    ? user.getUsername() 
                    : user.getUsername() + "@iu.edu";
                
                logger.info("📧 Due date reminder email sent to: {} for book '{}' due on {}", 
                            recipientEmail, rental.getBook().getTitle(), rental.getDueDate());
        }
    }
}
}