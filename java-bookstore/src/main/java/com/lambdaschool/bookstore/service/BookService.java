package com.lambdaschool.bookstore.service;

import com.lambdaschool.bookstore.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface BookService
{
    ArrayList<Book> findAll(Pageable pageable);
    Book findById(long id);
    Book update(Book book, long id);
    Book addAuthor(long bookid, long authorid);
    void delete(long id);
}
