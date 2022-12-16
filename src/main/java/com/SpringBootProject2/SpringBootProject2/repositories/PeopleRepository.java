package com.SpringBootProject2.SpringBootProject2.repositories;

import com.SpringBootProject2.SpringBootProject2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findByFullName(String fullName);
}
