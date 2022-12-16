package com.SpringBootProject2.SpringBootProject2.services;

import com.SpringBootProject2.SpringBootProject2.models.Book;
import com.SpringBootProject2.SpringBootProject2.models.Person;
import com.SpringBootProject2.SpringBootProject2.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }
    public Page<Book> findAll(Pageable var1){
        return findAll(var1);
    }
    public List<Book> findAll(Sort var1){
        return findAll(var1);
    }

    public Page<Book> findAll(Integer page, Integer booksPerPage, Boolean sortByYear){
        if (sortByYear == null || !sortByYear)
            if (page != null && booksPerPage != null)
                return booksRepository.findAll(PageRequest.of(page, booksPerPage));
            else return booksRepository.findAll(PageRequest.of(0, booksRepository.findAll().size()));

        if (page == null && booksPerPage == null)
            return booksRepository.findAll(PageRequest.of(0, booksRepository.findAll().size(), Sort.by("yearOfPublication")));

        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublication")));
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Book findByBookName(String bookName){
        return booksRepository.findByBookName(bookName);
    }


    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setBookId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findByBookNameStartingWith(String bookName){
        return booksRepository.findByBookNameStartingWith(bookName);
    }

    public List<Book> findByOwner(Person owner){
        return booksRepository.findByOwner(owner);
    }

    @Transactional
    public void freeTheBook(int id){
        booksRepository.freeTheBook(id);
        booksRepository.setPickUpTimeNull(id);
    }

    @Transactional
    public void assign(int personid, int bookid){
        booksRepository.assign(personid, bookid);
        Date currentDate = new Date();
        booksRepository.setPickUpTime(currentDate.getTime(), bookid);
    }

    public boolean isOutOfDate(Book book){
        Date currentDate = new Date();

        Long pickUpTime = book.getPickUpTime();
        if (pickUpTime == null)
            return false;
        pickUpTime = (long) pickUpTime;

        long tenDays = 684_000_000;
        if (currentDate.getTime() - pickUpTime > tenDays)
            return true;
        return false;
    }
    }
