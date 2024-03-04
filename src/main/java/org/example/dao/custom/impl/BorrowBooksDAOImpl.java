package org.example.dao.custom.impl;

import org.example.dao.custom.BorrowBooksDAO;
import org.example.entity.BorrowBooks;

import java.util.List;

public class BorrowBooksDAOImpl implements BorrowBooksDAO {
    @Override
    public boolean save(BorrowBooks dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BorrowBooks dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<BorrowBooks> getAll() throws ClassNotFoundException {
        return null;
    }

    @Override
    public BorrowBooks search(int id) throws ClassNotFoundException {
        return null;
    }
}
