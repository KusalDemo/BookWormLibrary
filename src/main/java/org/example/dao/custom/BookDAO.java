package org.example.dao.custom;

import org.example.bo.SuperBO;
import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.entity.Book;

import java.util.ArrayList;
import java.util.List;

public interface BookDAO extends CrudDAO<Book> {
    Book search(String title) throws ClassNotFoundException;

    boolean delete(String title) throws ClassNotFoundException;

    boolean updateAvailability(String title, boolean availability);
    ArrayList<Book> getAllBooksFromBranchId(String id) throws ClassNotFoundException;

    List<Book> getAllAvailableBooksFromBranchId(String id) throws ClassNotFoundException;
}
