package com.lambdaschool.bookstore.service;

import com.lambdaschool.bookstore.exception.ResourceNotFoundException;
import com.lambdaschool.bookstore.model.Author;
import com.lambdaschool.bookstore.model.Book;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;

@Service(value = "bookService")
public class BookServiceImpl implements BookService
{

    private BookRepository bookrepo;
    private AuthorRepository authorrepo;

    public BookServiceImpl(BookRepository bookrepo, AuthorRepository authorrepo)
    {
        this.bookrepo = bookrepo;
        this.authorrepo = authorrepo;
    }

    @Override
    public ArrayList<Book> findAll(Pageable pageable)
    {
        ArrayList<Book> books = new ArrayList<>();
        bookrepo.findAll(pageable).iterator().forEachRemaining(books::add);
        return books;
    }

    @Override
    public Book findById(long id)
    {
        return bookrepo.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Could not find book with id: " + id));
    }

    @Transactional
    @Override
    public Book update(Book book, long id)
    {
        // Currently does not support updating authors
        var current = bookrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find a book with id \"" + id + "\" to update"));
        if (book.getBooktitle() != null)
            current.setBooktitle(book.getBooktitle());
        if(book.getISBN() != null)
            current.setISBN(book.getISBN());
        if(book.getCopy() > 0)
            current.setCopy(book.getCopy());

        return bookrepo.save(current);
    }

    @Transactional
    @Override
    public Book addAuthor(long bookid, long authorid)
    {
        Book b = bookrepo.findById(bookid)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find book with id: " + bookid));
        Author a = authorrepo.findById(authorid)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find book with id: " + bookid));
        b.getAuthors().add(a);
        a.getBooks().add(b);

        bookrepo.save(b);
        authorrepo.save(a);

        return b;
    }

    @Override
    public void delete(long id) {
        bookrepo.delete(findById(id));
    }
}
