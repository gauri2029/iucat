package ed.iu.p566.iucat.config;

import ed.iu.p566.iucat.data.BookRepository;
import ed.iu.p566.iucat.data.UserRepository;
import ed.iu.p566.iucat.model.Book;
import ed.iu.p566.iucat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public DataLoader(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User("abc", "password123", "USER"));
            userRepository.save(new User("pqr", "password123", "USER"));
            userRepository.save(new User("admin", "adminpassword123", "ADMIN"));
            System.out.println("Sample users loaded into database!");
        }
        
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("978-0134685991", "Effective Java", "Joshua Bloch", 3, 2018, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", 2, 2004, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0135957059", "The Pragmatic Programmer", "David Thomas", 2, 2019, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0201633610", "Design Patterns", "Gang of Four", 1, 1994, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0321125215", "Domain-Driven Design", "Eric Evans", 2, 2003, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0132350884", "Clean Code", "Robert Martin", 3, 2008, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0134494166", "Clean Architecture", "Robert Martin", 2, 2017, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-1617294945", "Spring in Action", "Craig Walls", 2, 2018, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-1491950357", "Building Microservices", "Sam Newman", 1, 2015, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-0321349606", "Java Concurrency in Practice", "Brian Goetz", 2, 2006, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-1491957660", "Deep Learning with Python", "François Chollet", 2, 2017, "English", "eBook", "Data Science"));
            bookRepository.save(new Book("978-1449373320", "Python for Data Analysis", "Wes McKinney", 3, 2017, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1492078005", "Machine Learning Design Patterns", "Valliappa Lakshmanan", 1, 2020, "English", "eBook", "Data Science"));
            bookRepository.save(new Book("978-0262033848", "Deep Learning", "Ian Goodfellow", 1, 2016, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-0131103627", "The C Programming Language", "Brian Kernighan and Dennis Ritchie", 2, 1988, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0201616224", "The Mythical Man-Month", "Frederick P. Brooks Jr.", 2, 1995, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-1118531648", "Big Data: Principles and Best Practices", "Nathan Marz", 1, 2015, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0596517748", "Beautiful Data", "Toby Segaran", 1, 2008, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0141033570", "Outliers: The Story of Success", "Malcolm Gladwell", 3, 2008, "English", "Audio", "Business"));
            bookRepository.save(new Book("978-0062316097", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 3, 2014, "English", "Book", "History"));
            bookRepository.save(new Book("978-0307465351", "The Lean Startup", "Eric Ries", 2, 2011, "English", "Book", "Business"));
            bookRepository.save(new Book("978-0062457714", "Atomic Habits", "James Clear", 3, 2018, "English", "Book", "Self-Help"));
            bookRepository.save(new Book("978-0671027032", "Rich Dad Poor Dad", "Robert Kiyosaki", 3, 1997, "English", "Audio", "Business"));
            bookRepository.save(new Book("978-1982137274", "Think Like a Monk", "Jay Shetty", 2, 2020, "English", "Book", "Self-Help"));
            System.out.println("Sample books loaded into database!");
        }
    }
}
