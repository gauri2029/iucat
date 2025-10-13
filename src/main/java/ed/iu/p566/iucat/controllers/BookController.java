package ed.iu.p566.iucat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/search";
    }
    
    @GetMapping("/search")
    public String search() {
        return "search";
    }
}