package com.SpringBootProject2.SpringBootProject2.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "bookid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "book name should not be empty")
    @Size(min = 2, max = 250, message = "book name should be between 2 and 250 characters")
    @Column(name = "bookname")
    private String bookName;


    @NotEmpty(message = "author name should not be empty")
    @Size(min = 2, max = 150, message = "author name should be between 2 and 150 characters")
    @Column(name = "author")
    private String Author;

    @Min(value = 1500, message = "year of publication should be greater than 1500")
    @Max(value = 2022, message = "max = 2022")
    @Column(name = "yearofpublication")
    private int yearOfPublication;


    @Column(name = "personid")
    private Integer personid1;

    @Transient
    private Date currentDate = new Date();

    @Column(name = "pickuptime")
    private Long pickUpTime;

    @ManyToOne
    @JoinColumn(name = "personid", referencedColumnName = "id", insertable = false, updatable = false)
    private Person owner;


    public Book(String bookName, String author, int yearOfPublication) {
        this.bookName = bookName;
        Author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public Book() {}


    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Long getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Long pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Optional<Person> superGetOwner(){
        return Collections.singletonList(this.owner).stream().findAny();
    }
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }



    public void setPersonid1(Integer personid1) {
        this.personid1 = personid1;
    }



    public int getPersonid1() {
        return personid1;
    }

    public void setPersonid1(int personid1) {
        this.personid1 = personid1;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", Author='" + Author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
