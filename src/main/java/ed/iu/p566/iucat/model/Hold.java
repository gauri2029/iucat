package ed.iu.p566.iucat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holds")
public class Hold {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(name = "hold_date", nullable = false)
    private LocalDate holdDate;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "queue_position")
    private Integer queuePosition;
    
    public Hold() {}
    
    public Hold(User user, Book book, LocalDate holdDate, String status, Integer queuePosition) {
        this.user = user;
        this.book = book;
        this.holdDate = holdDate;
        this.status = status;
        this.queuePosition = queuePosition;
    }
    
    public boolean isExpired() {
        return expirationDate != null && LocalDate.now().isAfter(expirationDate);
    }
    
    public boolean isReady() {
        return "ready".equals(status);
    }
    
    public boolean isPending() {
        return "pending".equals(status);
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
    
    public LocalDate getHoldDate() {
        return holdDate;
    }
    
    public void setHoldDate(LocalDate holdDate) {
        this.holdDate = holdDate;
    }
    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getQueuePosition() {
        return queuePosition;
    }
    
    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }
}