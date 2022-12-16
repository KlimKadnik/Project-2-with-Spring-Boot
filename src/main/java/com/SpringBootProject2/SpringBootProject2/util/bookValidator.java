package com.SpringBootProject2.SpringBootProject2.util;

import com.SpringBootProject2.SpringBootProject2.models.Book;
import com.SpringBootProject2.SpringBootProject2.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
public class bookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public bookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (booksService.findByBookName(book.getBookName()) != null)
            errors.rejectValue("bookName", "", "Это название книги уже занято!");
    }
}
