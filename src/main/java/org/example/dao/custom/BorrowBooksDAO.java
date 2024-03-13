package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.BorrowBooks;

import java.util.List;

public interface BorrowBooksDAO extends CrudDAO<BorrowBooks> {

    List<BorrowBooks> getAllBorrowBooksFromUserId(String id) throws ClassNotFoundException;
}
