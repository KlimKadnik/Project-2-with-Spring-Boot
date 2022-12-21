# Project-2-with-Spring-Boot



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
О проекте:

Здесь осуществлён простой веб-интерфейс на обычном локальном хосте (localhost) для работников библиотеки, в котором можно назначать книги человеку, которые он взял, добавлять/удалять/изменять нового человека/книгу в БД. Осуществлять поиск по первым символам в названии книги, пагинацией и сортировкой по алфавиту. 

Есть индивидуальные страницы для каждого человека (ФИО, список взятых книг или сообщение об их отсутствии), индивидуальные страницы для каждой книги (название, автор, и выпадающих список для назначения книги человеку, если она свободна).

Также, если книга будет у человека больше 10 дней, то ссылка на эту книгу будет красного цвета. 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





DATABASE TABLES:


 

CREATE TABLE book
(
    bookid INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,

    personid INT REFERENCES person(id) ON DELETE SET NULL,

    bookname VARCHAR(200) NOT NULL UNIQUE,

    author VARCHAR(150) NOT NULL,

    yearofpublication INT CHECK ((yearofpublication > 1500) AND (yearofpublication < 2022)),

    pickuptime BIGINT CHECK (pickuptime > 0)
);





CREATE TABLE person
(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,

    fullname VARCHAR(400) NOT NULL UNIQUE,

    yearofbirth INT CHECK ((yearofbirth > 1920) AND (yearofbirth < 2022))
);
