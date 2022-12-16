package com.SpringBootProject2.SpringBootProject2.controllers;

import com.SpringBootProject2.SpringBootProject2.models.Book;
import com.SpringBootProject2.SpringBootProject2.models.Person;
import com.SpringBootProject2.SpringBootProject2.services.BooksService;
import com.SpringBootProject2.SpringBootProject2.services.PeopleService;
import com.SpringBootProject2.SpringBootProject2.util.bookValidator;
import com.SpringBootProject2.SpringBootProject2.util.personValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final personValidator personValidator;
    private final bookValidator bookValidator;


    @Autowired
    public BookController(PeopleService peopleService, BooksService booksService, personValidator personValidator, bookValidator bookValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
        this.bookValidator = bookValidator;
    }


    @GetMapping()
    public String index(@ModelAttribute("request") String request,
                        Model model,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer books_per_page,
                        @RequestParam(required = false) Boolean sort_by_year){


        model.addAttribute("books", booksService.findAll(page, books_per_page, sort_by_year));
        model.addAttribute("booksService", booksService);
        return"/books/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String request, @ModelAttribute("requestForSearchField") String requestForSearchField, Model model){

        List<Book> SearchBooks = booksService.findByBookNameStartingWith(request);

        model.addAttribute("SearchBooks", SearchBooks);
        return"/books/search";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "books/new";
        }

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/edit")
    public String edit(Model model, @PathVariable("bookId") int bookId){
        model.addAttribute("book", booksService.findOne(bookId));
        return "/books/edit";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(bookId));
        Person bookOwner = booksService.findOne(bookId).getOwner();

        model.addAttribute("booksService", booksService);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @PatchMapping("/{id}/release")
    public String FreeTheBook(@PathVariable("id") int id){
        booksService.freeTheBook(id);
        return "redirect:/books/" + id;
    }


    @PatchMapping("/{bookId}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("bookId") int bookId){

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            System.out.println("bindingResult has errors");
            return "books/edit";
        }

        booksService.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{bookId}")
    public String delete(@ModelAttribute("bookId") int bookId){
        booksService.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/assign")
    public String assignBook(@PathVariable("bookId") int bookId, @ModelAttribute("person") Person person){
        booksService.assign(person.getId(), bookId);
        return "redirect:/books/" + bookId;
    }


}
