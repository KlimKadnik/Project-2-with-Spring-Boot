package com.SpringBootProject2.SpringBootProject2.repositories;


import com.SpringBootProject2.SpringBootProject2.models.Book;
import com.SpringBootProject2.SpringBootProject2.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Page<Book> findAll(Pageable var1);
    List<Book> findAll(Sort var1);
    List<Book> findByOwner(Person owner);

    Book findByBookName(String bookName);

    List<Book> findByBookNameStartingWith(String bookName);

    @Modifying
    @Query("update Book b set b.personid1 = NULL where b.bookId = ?1")
    void freeTheBook(int id);

    @Modifying
    @Query("update Book b set b.pickUpTime = null where b.bookId = ?1")
    void setPickUpTimeNull(int bookId);

    @Modifying
    @Query("update Book b set b.personid1 = ?1 where b.bookId = ?2")
    void assign(int personid1, int bookid);

    @Modifying
    @Query("update Book b set b.pickUpTime = ?1 where b.bookId = ?2")
    void setPickUpTime(Long pickUpTime, int bookId);

}
