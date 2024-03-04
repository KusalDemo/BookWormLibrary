package org.example.bo.custom.impl;

import org.example.bo.custom.BookBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dto.BookDto;
import org.example.entity.Book;

import java.util.ArrayList;

public class BookBOImpl implements BookBO {
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BOOK);
    @Override
    public boolean saveBook(BookDto bookDto) throws ClassNotFoundException {
        return bookDAO.save(new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),true,bookDto.getBranch()));
    }

    @Override
    public boolean updateBook(BookDto bookDto) throws ClassNotFoundException {
        return bookDAO.update(new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),bookDto.isAvailability(),bookDto.getBranch()));
    }

    @Override
    public boolean deleteBook(String title) throws ClassNotFoundException {
        return bookDAO.delete(title);
    }

    @Override
    public String searchBook(String title) {
        return null;
    }

    @Override
    public boolean updateAvailability(String title, boolean availability) {
        return false;
    }

    @Override
    public ArrayList<BookDto> getAllBooks() {
        return null;
    }
}
