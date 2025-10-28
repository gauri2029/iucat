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
            
            bookRepository.save(new Book("978-1491943533", "Learning React", "Alex Banks", 2, 2020, "English", "Book", "Web Development"));
            bookRepository.save(new Book("978-1492053811", "React Native in Action", "Nader Dabit", 1, 2019, "English", "eBook", "Mobile Development"));
            bookRepository.save(new Book("978-1617294136", "Node.js in Action", "Mike Cantelon", 2, 2017, "English", "Book", "Web Development"));
            bookRepository.save(new Book("978-1491954621", "Flask Web Development", "Miguel Grinberg", 2, 2018, "English", "Book", "Web Development"));
            bookRepository.save(new Book("978-1098139872", "Learning TypeScript", "Josh Goldberg", 1, 2022, "English", "Book", "Programming"));
            bookRepository.save(new Book("978-1680502862", "Programming Phoenix", "Chris McCord", 1, 2019, "English", "Book", "Web Development"));
            bookRepository.save(new Book("978-1492041146", "Vue.js: Up and Running", "Callum Macrae", 2, 2018, "English", "eBook", "Web Development"));
            
            bookRepository.save(new Book("978-1449373320", "PostgreSQL: Up and Running", "Regina Obe", 2, 2017, "English", "Book", "Database"));
            bookRepository.save(new Book("978-0596809485", "MongoDB: The Definitive Guide", "Kristina Chodorow", 1, 2013, "English", "Book", "Database"));
            bookRepository.save(new Book("978-1449358063", "Redis in Action", "Josiah Carlson", 2, 2013, "English", "Book", "Database"));
            bookRepository.save(new Book("978-1491961414", "Site Reliability Engineering", "Niall Murphy", 1, 2016, "English", "Book", "DevOps"));
            bookRepository.save(new Book("978-1492046530", "Kubernetes: Up and Running", "Brendan Burns", 2, 2019, "English", "Book", "DevOps"));
            
            bookRepository.save(new Book("978-0385537858", "Astrophysics for People in a Hurry", "Neil deGrasse Tyson", 3, 2017, "English", "Book", "Science"));
            bookRepository.save(new Book("978-0307389732", "The Elegant Universe", "Brian Greene", 2, 2003, "English", "Book", "Physics"));
            bookRepository.save(new Book("978-0393328622", "The Gene", "Siddhartha Mukherjee", 2, 2016, "English", "Book", "Biology"));
            bookRepository.save(new Book("978-0393347777", "The Meaning of Human Existence", "E.O. Wilson", 1, 2014, "English", "Book", "Philosophy"));
            bookRepository.save(new Book("978-0691158518", "Mathematics for the Nonmathematician", "Morris Kline", 1, 1985, "English", "Book", "Mathematics"));
            
            bookRepository.save(new Book("978-8408093732", "Cien Años de Soledad", "Gabriel García Márquez", 2, 1967, "Spanish", "Book", "Fiction"));
            bookRepository.save(new Book("978-2070360024", "Le Petit Prince", "Antoine de Saint-Exupéry", 3, 1943, "French", "Book", "Fiction"));
            bookRepository.save(new Book("978-4062768948", "Norwegian Wood", "Haruki Murakami", 1, 1987, "Japanese", "Book", "Fiction"));
            bookRepository.save(new Book("978-3518461891", "Siddharta", "Hermann Hesse", 2, 1922, "German", "Book", "Philosophy"));
            
            bookRepository.save(new Book("978-1401238964", "Watchmen", "Alan Moore", 2, 1987, "English", "Graphic Novel", "Fiction"));
            bookRepository.save(new Book("978-0375714832", "Maus", "Art Spiegelman", 1, 1991, "English", "Graphic Novel", "History"));
            bookRepository.save(new Book("978-0375423949", "Persepolis", "Marjane Satrapi", 2, 2003, "English", "Graphic Novel", "Biography"));
            
            bookRepository.save(new Book("978-0143127741", "The Body Keeps the Score", "Bessel van der Kolk", 2, 2014, "English", "Book", "Psychology"));
            bookRepository.save(new Book("978-0316478526", "Why We Sleep", "Matthew Walker", 3, 2017, "English", "Book", "Health"));
            bookRepository.save(new Book("978-0399563829", "Breath", "James Nestor", 1, 2020, "English", "Book", "Health"));
            
            bookRepository.save(new Book("978-0385537254", "This Changes Everything", "Naomi Klein", 1, 2014, "English", "Book", "Environment"));
            bookRepository.save(new Book("978-0143115625", "The Uninhabitable Earth", "David Wallace-Wells", 2, 2019, "English", "Book", "Environment"));
            bookRepository.save(new Book("978-0062316110", "Braiding Sweetgrass", "Robin Wall Kimmerer", 1, 2013, "English", "Book", "Nature"));
            
            bookRepository.save(new Book("978-1593279929", "The Art of Exploitation", "Jon Erickson", 1, 2008, "English", "Book", "Cybersecurity"));
            bookRepository.save(new Book("978-1119186113", "The Web Application Hacker's Handbook", "Dafydd Stuttard", 2, 2011, "English", "Book", "Cybersecurity"));
            bookRepository.save(new Book("978-1491902103", "Network Security Through Data Analysis", "Michael Collins", 1, 2014, "English", "eBook", "Networking"));
            bookRepository.save(new Book("978-0134477367", "Computer Networking: A Top-Down Approach", "James Kurose", 3, 2017, "English", "Book", "Networking"));
            
            bookRepository.save(new Book("978-1568814681", "Game Engine Architecture", "Jason Gregory", 1, 2018, "English", "Book", "Game Development"));
            bookRepository.save(new Book("978-0321940315", "The Art of Game Design", "Jesse Schell", 2, 2019, "English", "Book", "Game Development"));
            bookRepository.save(new Book("978-1484237717", "Unity in Action", "Joseph Hocking", 2, 2018, "English", "Book", "Game Development"));
            
            bookRepository.save(new Book("978-0393358919", "Capital in the Twenty-First Century", "Thomas Piketty", 1, 2014, "English", "Book", "Economics"));
            bookRepository.save(new Book("978-0143115762", "Freakonomics", "Steven Levitt", 3, 2005, "English", "Book", "Economics"));
            bookRepository.save(new Book("978-0060555665", "The Intelligent Investor", "Benjamin Graham", 2, 1949, "English", "Book", "Finance"));
            bookRepository.save(new Book("978-0812979183", "A Random Walk Down Wall Street", "Burton Malkiel", 1, 2019, "English", "Book", "Finance"));
            
            bookRepository.save(new Book("978-1501156847", "Educated", "Tara Westover", 3, 2018, "English", "Book", "Memoir"));
            bookRepository.save(new Book("978-1501127625", "Born a Crime", "Trevor Noah", 2, 2016, "English", "Book", "Memoir"));
            bookRepository.save(new Book("978-0743264747", "Steve Jobs", "Walter Isaacson", 2, 2011, "English", "Book", "Biography"));
            bookRepository.save(new Book("978-0316769174", "The Autobiography of Malcolm X", "Malcolm X", 1, 1965, "English", "Book", "Biography"));
            
            bookRepository.save(new Book("978-0061965524", "The Sun and Her Flowers", "Rupi Kaur", 2, 2017, "English", "Book", "Poetry"));
            bookRepository.save(new Book("978-0062457738", "On Writing", "Stephen King", 3, 2000, "English", "Book", "Writing"));
            bookRepository.save(new Book("978-0060930530", "Bird by Bird", "Anne Lamott", 1, 1994, "English", "Book", "Writing"));
            
            bookRepository.save(new Book("978-0848732486", "Joy of Cooking", "Irma Rombauer", 2, 1931, "English", "Book", "Cooking"));
            bookRepository.save(new Book("978-0385344623", "Salt Fat Acid Heat", "Samin Nosrat", 3, 2017, "English", "Book", "Cooking"));
            
            bookRepository.save(new Book("978-1577314806", "The Power of Now", "Eckhart Tolle", 2, 1997, "English", "Book", "Spirituality"));
            bookRepository.save(new Book("978-0062509581", "The Alchemist", "Paulo Coelho", 3, 1988, "English", "Book", "Philosophy"));
            bookRepository.save(new Book("978-0060920043", "The Prophet", "Kahlil Gibran", 1, 1923, "English", "Book", "Poetry"));
            bookRepository.save(new Book("978-0143106968", "Man's Search for Meaning", "Viktor Frankl", 2, 1946, "English", "Book", "Philosophy"));
        }
    }
}
