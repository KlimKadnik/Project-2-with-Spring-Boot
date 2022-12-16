package com.SpringBootProject2.SpringBootProject2.controllers;


import com.SpringBootProject2.SpringBootProject2.models.Person;
import com.SpringBootProject2.SpringBootProject2.services.BooksService;
import com.SpringBootProject2.SpringBootProject2.services.PeopleService;
import com.SpringBootProject2.SpringBootProject2.util.personValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final personValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, personValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());

        for(Person person : peopleService.findAll()){
            System.out.println(person);
        }

        return"/people/index";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){

        System.out.println(person);

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String edit(Model model, @PathVariable("personId") int personId){
        model.addAttribute("person", peopleService.findOne(personId));
        return "people/edit";
    }

    @PatchMapping("/{personId}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("personId") int personId){

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "people/edit";
        }

        peopleService.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{personId}")
    public String delete(@ModelAttribute("personId") int personId){
        peopleService.delete(personId);
        return "redirect:/people";
    }

    @GetMapping("/{personId}")
    public String show(Model model, @PathVariable("personId") int personId){
        model.addAttribute("person", peopleService.findOne(personId));
        model.addAttribute("PersonsBooks", booksService.findByOwner(peopleService.findOne(personId)));
        return "/people/show";
    }


}
