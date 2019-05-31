package com.lambdaschool.bookstore.service;

import com.lambdaschool.bookstore.exception.ResourceNotFoundException;
import com.lambdaschool.bookstore.model.Author;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value="authorService")
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorrepo;

    public AuthorServiceImpl(AuthorRepository authorrepo)
    {
        this.authorrepo = authorrepo;
    }

    @Override
    public ArrayList<Author> findAll(Pageable pageable)
    {
        ArrayList<Author> authors = new ArrayList<>();
        authorrepo.findAll(pageable).iterator().forEachRemaining(authors::add);
        return authors;
    }

    @Override
    public Author findById(long id)
    {
        return authorrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find Author with id: " + id));
    }
}
