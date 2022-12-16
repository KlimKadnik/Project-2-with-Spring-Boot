package com.SpringBootProject2.SpringBootProject2.util;


import com.SpringBootProject2.SpringBootProject2.models.Person;
import com.SpringBootProject2.SpringBootProject2.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class personValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public personValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.findByFullName(person.getFullName()) != null)
            errors.rejectValue("fullName", "", "Это ФИО уже занято!");
    }
}
