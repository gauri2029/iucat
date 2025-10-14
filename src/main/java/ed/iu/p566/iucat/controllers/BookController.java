package ed.iu.p566.iucat.controllers;

import ed.iu.p566.iucat.data.BookRepository;
import ed.iu.p566.iucat.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/search";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query, Model model) {
        List<Book> books;
        
        if (query != null && !query.trim().isEmpty()) {
            books = bookRepository.searchBooks(query.trim());
            model.addAttribute("query", query);
        } else {
            books = bookRepository.findAll();
        }
        
        model.addAttribute("books", books);
        return "search";
    }
    
    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        return "book-details";
    }
}