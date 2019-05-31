package com.lambdaschool.bookstore.service;

import com.lambdaschool.bookstore.model.Author;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface AuthorService
{
    ArrayList<Author> findAll(Pageable pageable);
    Author findById(long id);
}
