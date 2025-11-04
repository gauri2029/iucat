package ed.iu.p566.iucat.service;

import ed.iu.p566.iucat.data.RentalRepository;
import ed.iu.p566.iucat.model.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * Scheduled task that runs every day at midnight (12:00 AM)
     * checking for books that are due in 2 days and sending reminder emails
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDueDateReminders() {
        logger.info("Starting scheduled task: sendDueDateReminders at {}", LocalDate.now());
        
        try {
            LocalDate twoDaysFromNow = LocalDate.now().plusDays(2);
            
            List<Rental> upcomingDueRentals = rentalRepository.findRentalsDueOnDate(twoDaysFromNow);
            
            logger.info("Found {} rentals due on {}", upcomingDueRentals.size(), twoDaysFromNow);
            
            for (Rental rental : upcomingDueRentals) {
                try {
                    sendDueDateReminderEmail(rental);
                } catch (Exception e) {
                    logger.error("Failed to send reminder email for rental ID: {}", rental.getId(), e);
                }
            }
            
            logger.info("Completed sending {} reminder emails", upcomingDueRentals.size());
            
        } catch (Exception e) {
            logger.error("Error in sendDueDateReminders scheduled task", e);
        }
    }
    
    private void sendDueDateReminderEmail(Rental rental) {
        String username = rental.getUser().getUsername();
        String recipientEmail = username.contains("@") ? username : username + "@iu.edu";

        String subject = "Book Due Date Reminder - IU Cat Library";
        String message = buildReminderEmailMessage(rental);
        
        if (!emailService.isValidEmail(recipientEmail)) {
            logger.warn("invalid address: {}", recipientEmail);
            return;
        }
        emailService.sendEmail(recipientEmail, subject, message);
            
        logger.info("Sent reminder email to {} for book: {}", recipientEmail, rental.getBook().getTitle());
    }
    
    /**
     * buidling the email message content
     */
    private String buildReminderEmailMessage(Rental rental) {
        StringBuilder message = new StringBuilder();
        
        message.append("Dear ").append(rental.getUser().getUsername()).append(",\n\n");
        message.append("This is a reminder that your borrowed book is due soon.\n\n");
        message.append("Book Details:\n");
        message.append("Title: ").append(rental.getBook().getTitle()).append("\n");
        message.append("Author: ").append(rental.getBook().getAuthor()).append("\n");
        message.append("ISBN: ").append(rental.getBook().getIsbn()).append("\n");
        message.append("Due Date: ").append(rental.getDueDate()).append("\n\n");
        
        if (rental.canExtend()) {
            message.append("You have ").append(rental.getRemainingExtensions())
                   .append(" extension(s) remaining. You can extend the due date by logging into your account.\n\n");
        } else if (rental.isExtensionLimitReached()) {
            message.append("Note: You have reached the maximum number of extensions for this book.\n\n");
        }
        
        message.append("Please return the book on or before the due date to avoid any late fees.\n\n");
        message.append("Thank you for using IU Cat Library!\n\n");
        message.append("Regards,\n");
        message.append("IU Cat Library System");
        
        return message.toString();
    }
}