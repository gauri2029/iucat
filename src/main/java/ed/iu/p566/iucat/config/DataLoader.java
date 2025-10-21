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
            userRepository.save(new User("demo1", "demo123", "USER"));
            userRepository.save(new User("demo2", "demo1234", "USER"));
            userRepository.save(new User("demo3", "demo12345", "USER"));
            userRepository.save(new User("demoUser", "demoUser123", "USER"));
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
            bookRepository.save(new Book("978-0131103627", "The C Programming Language", "Brian Kernighan", 2, 1988, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0201616224", "The Mythical Man-Month", "Frederick Brooks", 2, 1995, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0596517748", "Beautiful Code", "Andy Oram", 1, 2007, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-1449373320", "Designing Data-Intensive Applications", "Martin Kleppmann", 2, 2017, "English", "Book", "Computer Science"));
            bookRepository.save(new Book("978-0262033848", "Introduction to Algorithms", "Thomas Cormen", 3, 2009, "English", "Book", "Computer Science"));
            
            bookRepository.save(new Book("978-1491957660", "Deep Learning with Python", "François Chollet", 2, 2017, "English", "eBook", "Data Science"));
            bookRepository.save(new Book("978-1449369415", "Python for Data Analysis", "Wes McKinney", 3, 2017, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1492078005", "Machine Learning Design Patterns", "Valliappa Lakshmanan", 1, 2020, "English", "eBook", "Data Science"));
            bookRepository.save(new Book("978-0262035613", "Deep Learning", "Ian Goodfellow", 1, 2016, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1491952960", "Hands-On Machine Learning", "Aurélien Géron", 2, 2019, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1119142966", "Big Data", "Nathan Marz", 1, 2015, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1491904244", "Data Science from Scratch", "Joel Grus", 2, 2019, "English", "Book", "Data Science"));
            bookRepository.save(new Book("978-1492032649", "Practical Statistics for Data Scientists", "Peter Bruce", 1, 2020, "English", "Book", "Data Science"));
            
            bookRepository.save(new Book("978-0307465351", "The Lean Startup", "Eric Ries", 2, 2011, "English", "Book", "Business"));
            bookRepository.save(new Book("978-0062457714", "Atomic Habits", "James Clear", 3, 2018, "English", "Book", "Self-Help"));
            bookRepository.save(new Book("978-0671027032", "Rich Dad Poor Dad", "Robert Kiyosaki", 3, 1997, "English", "Audio", "Business"));
            bookRepository.save(new Book("978-1982137274", "Think Like a Monk", "Jay Shetty", 2, 2020, "English", "Book", "Self-Help"));
            bookRepository.save(new Book("978-0066620992", "Good to Great", "Jim Collins", 2, 2001, "English", "Book", "Business"));
            bookRepository.save(new Book("978-1591846444", "The 4-Hour Workweek", "Tim Ferriss", 2, 2009, "English", "Book", "Business"));
            bookRepository.save(new Book("978-1501144318", "Principles", "Ray Dalio", 2, 2017, "English", "Book", "Business"));
            bookRepository.save(new Book("978-0062315007", "Zero to One", "Peter Thiel", 2, 2014, "English", "Book", "Business"));
            bookRepository.save(new Book("978-1591848363", "Hooked", "Nir Eyal", 1, 2014, "English", "Book", "Business"));
            bookRepository.save(new Book("978-0812993011", "Thinking, Fast and Slow", "Daniel Kahneman", 2, 2011, "English", "Book", "Psychology"));
            bookRepository.save(new Book("978-1476754369", "Mindset", "Carol Dweck", 2, 2006, "English", "Book", "Psychology"));
            bookRepository.save(new Book("978-0143115762", "The Power of Habit", "Charles Duhigg", 3, 2012, "English", "Book", "Self-Help"));
            
            bookRepository.save(new Book("978-0062316097", "Sapiens", "Yuval Noah Harari", 3, 2014, "English", "Book", "History"));
            bookRepository.save(new Book("978-0062464347", "Homo Deus", "Yuval Noah Harari", 2, 2017, "English", "Book", "History"));
            bookRepository.save(new Book("978-0141034591", "Guns, Germs, and Steel", "Jared Diamond", 2, 1997, "English", "Book", "History"));
            bookRepository.save(new Book("978-0141033570", "Outliers", "Malcolm Gladwell", 3, 2008, "English", "Audio", "Social Science"));
            bookRepository.save(new Book("978-0316346627", "The Tipping Point", "Malcolm Gladwell", 2, 2000, "English", "Book", "Social Science"));
            bookRepository.save(new Book("978-1476756905", "The Sixth Extinction", "Elizabeth Kolbert", 1, 2014, "English", "Book", "Science"));
            bookRepository.save(new Book("978-0307887894", "The Signal and the Noise", "Nate Silver", 2, 2012, "English", "Book", "Statistics"));
            bookRepository.save(new Book("978-0374533557", "Thinking in Systems", "Donella Meadows", 1, 2008, "English", "Book", "Systems Theory"));
            
            bookRepository.save(new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee", 3, 1960, "English", "Book", "Fiction"));
            bookRepository.save(new Book("978-0451524935", "1984", "George Orwell", 3, 1949, "English", "Book", "Fiction"));
            bookRepository.save(new Book("978-0316769488", "The Catcher in the Rye", "J.D. Salinger", 2, 1951, "English", "Book", "Fiction"));
            bookRepository.save(new Book("978-0544003415", "The Lord of the Rings", "J.R.R. Tolkien", 2, 1954, "English", "Book", "Fantasy"));
            bookRepository.save(new Book("978-0439023481", "The Hunger Games", "Suzanne Collins", 3, 2008, "English", "Book", "Fiction"));
            bookRepository.save(new Book("978-0307277671", "The Da Vinci Code", "Dan Brown", 2, 2003, "English", "Book", "Mystery"));
            bookRepository.save(new Book("978-0553803709", "A Game of Thrones", "George R.R. Martin", 2, 1996, "English", "Book", "Fantasy"));
            
            System.out.println("50 sample books loaded into database!");
        }
    }
}
