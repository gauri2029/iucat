package ed.iu.p566.iucat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(name = "rental_date", nullable = false)
    private LocalDate rentalDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "extension_count", nullable = false)
    private Integer extensionCount = 0;
    
    private static final int MAX_EXTENSIONS = 1;
    
    private static final int EXTENSION_DAYS = 14;
    
    public Rental() {}
    
    public Rental(User user, Book book, LocalDate rentalDate, LocalDate dueDate) {
        this.user = user;
        this.book = book;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.status = "active";
        this.extensionCount = 0;
    }
    
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }
    
    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.status = "returned";
    }
    
    public boolean canExtend() {
        return "active".equals(this.status) 
            && this.returnDate == null
            && !this.isOverdue()
            && this.extensionCount < MAX_EXTENSIONS;
    }
    
    public boolean extendDueDate() {
        if (!canExtend()) {
            return false;
        }
        
        this.dueDate = this.dueDate.plusDays(EXTENSION_DAYS);
        this.extensionCount++;
        return true;
    }
    
    public boolean isExtensionLimitReached() {
        return this.extensionCount >= MAX_EXTENSIONS;
    }

    public int getRemainingExtensions() {
        return Math.max(0, MAX_EXTENSIONS - this.extensionCount);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public LocalDate getRentalDate() {
        return rentalDate;
    }
    
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getExtensionCount() {
        return extensionCount;
    }
    
    public void setExtensionCount(Integer extensionCount) {
        this.extensionCount = extensionCount;
    }
    
    public static int getMaxExtensions() {
        return MAX_EXTENSIONS;
    }
    
    public static int getExtensionDays() {
        return EXTENSION_DAYS;
    }
}