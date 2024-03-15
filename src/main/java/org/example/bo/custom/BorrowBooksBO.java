package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;

import java.util.List;

public interface BorrowBooksBO extends SuperBO {
    boolean saveBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException;

    boolean updateBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException;

    boolean deleteBorrowBook(String id) throws ClassNotFoundException;

    List<BorrowBooksDto> getAllBorrowBooks() throws ClassNotFoundException;

    List<BorrowBooksDto> getAllBorrowBooksFromUserId(String id) throws ClassNotFoundException;

    List<BorrowBooksDto> getAllAvailableBooksFromBranchId(String id) throws ClassNotFoundException;

    List<BorrowBooksDto> getReturnDateExceededBooks() throws ClassNotFoundException;
    List<BorrowBooksDto> getReturnDateExceededBooks(String id) throws ClassNotFoundException;

}
