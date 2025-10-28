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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final HoldRepository holdRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public BookController(BookRepository bookRepository, HoldRepository holdRepository, RentalRepository rentalRepository) {
        this.bookRepository = bookRepository;
        this.holdRepository = holdRepository;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "all") String searchField,
            @RequestParam(required = false) String filterAuthor,
            @RequestParam(required = false) String filterSubject,
            @RequestParam(required = false) String filterFormat,
            @RequestParam(required = false) String filterLanguage,
            @RequestParam(required = false) Integer filterYear,
            @RequestParam(required = false) Boolean availableOnly,
            Model model) {

        List<Book> books = null;

        if (query != null && !query.trim().isEmpty()) {
            String trimmedQuery = query.trim();

            switch (searchField.toLowerCase()) {
                case "title":
                    books = bookRepository.searchByTitle(trimmedQuery);
                    break;
                case "author":
                    books = bookRepository.searchByAuthor(trimmedQuery);
                    break;
                case "subject":
                    books = bookRepository.searchBySubject(trimmedQuery);
                    break;
                default:
                    books = bookRepository.searchBooks(trimmedQuery);
            }

            if (books != null && !books.isEmpty()) {
                if (filterAuthor != null && !filterAuthor.isEmpty()) {
                    books = books.stream()
                            .filter(b -> b.getAuthor().equalsIgnoreCase(filterAuthor))
                            .collect(Collectors.toList());
                }

                if (filterSubject != null && !filterSubject.isEmpty()) {
                    books = books.stream()
                            .filter(b -> b.getSubject().equalsIgnoreCase(filterSubject))
                            .collect(Collectors.toList());
                }

                if (filterFormat != null && !filterFormat.isEmpty()) {
                    books = books.stream()
                            .filter(b -> b.getFormat().equalsIgnoreCase(filterFormat))
                            .collect(Collectors.toList());
                }

                if (filterLanguage != null && !filterLanguage.isEmpty()) {
                    books = books.stream()
                            .filter(b -> b.getLanguage().equalsIgnoreCase(filterLanguage))
                            .collect(Collectors.toList());
                }

                if (filterYear != null) {
                    books = books.stream()
                            .filter(b -> b.getPublicationYear().equals(filterYear))
                            .collect(Collectors.toList());
                }

                if (availableOnly != null && availableOnly) {
                    books = books.stream()
                            .filter(Book::isAvailable)
                            .collect(Collectors.toList());
                }
            }

            if (books != null && !books.isEmpty()) {
                List<String> authors = books.stream()
                        .map(Book::getAuthor)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                List<String> subjects = books.stream()
                        .map(Book::getSubject)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                List<String> formats = books.stream()
                        .map(Book::getFormat)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                List<String> languages = books.stream()
                        .map(Book::getLanguage)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                List<Integer> years = books.stream()
                        .map(Book::getPublicationYear)
                        .distinct()
                        .sorted((a, b) -> b - a)
                        .collect(Collectors.toList());

                model.addAttribute("filterAuthors", authors);
                model.addAttribute("filterSubjects", subjects);
                model.addAttribute("filterFormats", formats);
                model.addAttribute("filterLanguages", languages);
                model.addAttribute("filterYears", years);
            }

            model.addAttribute("query", query);
            model.addAttribute("searchField", searchField);
            model.addAttribute("books", books);
            model.addAttribute("searched", true);

            model.addAttribute("selectedAuthor", filterAuthor);
            model.addAttribute("selectedSubject", filterSubject);
            model.addAttribute("selectedFormat", filterFormat);
            model.addAttribute("selectedLanguage", filterLanguage);
            model.addAttribute("selectedYear", filterYear);
            model.addAttribute("availableOnly", availableOnly);
        } else {
            model.addAttribute("searched", false);
            model.addAttribute("searchField", "all");
        }

        return "search";
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Long id, Model model, HttpSession session) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        Long holdCount = holdRepository.countPendingHoldsByBook(book);
        
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Hold userHold = null;
        Rental userRental = null;
        
        if (loggedInUser != null) {
            // bug fix- checking for active hold
            Optional<Hold> holdOpt = holdRepository.findActiveHoldByUserAndBook(loggedInUser, book);
            if (holdOpt.isPresent()) {
                userHold = holdOpt.get();
            }
            
            // bug fixx- Check for active rental
            Optional<Rental> rentalOpt = rentalRepository.findActiveRentalByUserAndBook(loggedInUser, book);
            if (rentalOpt.isPresent()) {
                userRental = rentalOpt.get();
            }
        }
        
        model.addAttribute("book", book);
        model.addAttribute("holdCount", holdCount);
        model.addAttribute("userHold", userHold);
        model.addAttribute("userRental", userRental);
        
        return "book-details";
    }
}