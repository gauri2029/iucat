package ed.iu.p566.iucat.config;

import ed.iu.p566.iucat.data.BookRepository;
import ed.iu.p566.iucat.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Only load data if database is empty
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3));
            bookRepository.save(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", 2));
            bookRepository.save(new Book("978-0135957059", "The Pragmatic Programmer", "David Thomas", 2));
            bookRepository.save(new Book("978-0201633610", "Design Patterns", "Gang of Four", 1));
            bookRepository.save(new Book("978-0321125215", "Domain-Driven Design", "Eric Evans", 2));
            bookRepository.save(new Book("978-0132350884", "Clean Code", "Robert Martin", 3));
            bookRepository.save(new Book("978-0134494166", "Clean Architecture", "Robert Martin", 2));
            bookRepository.save(new Book("978-1617294945", "Spring in Action", "Craig Walls", 2));
            bookRepository.save(new Book("978-1491950357", "Building Microservices", "Sam Newman", 1));
            bookRepository.save(new Book("978-0321349606", "Java Concurrency in Practice", "Brian Goetz", 2));
            
            System.out.println("Sample books loaded into database!");
        }
    }
}
