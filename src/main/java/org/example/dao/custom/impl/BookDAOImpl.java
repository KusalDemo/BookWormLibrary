package org.example.dao.custom.impl;

import org.example.dao.custom.BookDAO;
import org.example.entity.Book;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean save(Book dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Book dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<Book> getAll() throws ClassNotFoundException {
        return null;
    }

    @Override
    public Book search(int id) throws ClassNotFoundException {
        return null;
    }
}
