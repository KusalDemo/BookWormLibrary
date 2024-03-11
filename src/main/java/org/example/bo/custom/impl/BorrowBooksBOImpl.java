package org.example.bo.custom.impl;

import org.example.bo.custom.BorrowBooksBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.dto.BorrowBooksDto;
import org.example.entity.BorrowBooks;

import java.util.List;

public class BorrowBooksBOImpl implements BorrowBooksBO {

    BorrowBooksDAO borrowBooksDAO = (BorrowBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BORROWBOOKS);
    @Override
    public boolean saveBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException {
        return false;
        /*return borrowBooksDAO.save(new BorrowBooks(borrowBooksDto.getId(),borrowBooksDto.getUser(),borrowBooksDto.getBook(),borrowBooksDto.getUser(),borrowBooksDto.getBorrowDate(),borrowBooksDto.getReturnDate()));*/
    }

    @Override
    public boolean updateBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteBorrowBook(String id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<BorrowBooksDto> getAllBorrowBooks() throws ClassNotFoundException {
        return null;
    }
}
