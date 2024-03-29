package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDto;

import java.util.ArrayList;

public interface BookBO extends SuperBO {

    boolean saveBook(BookDto bookDto) throws ClassNotFoundException;

    boolean updateBook(BookDto bookDto) throws ClassNotFoundException;

    boolean deleteBook(String title) throws ClassNotFoundException;


    boolean updateAvailability(String id, boolean availability);

    ArrayList<BookDto> getAllBooks() throws ClassNotFoundException;

    ArrayList<BookDto>getAllBooksFromBranchId(String branch) throws ClassNotFoundException;

    ArrayList<BookDto> getAllAvailableBooksFromBranchId(String branch) throws ClassNotFoundException;

    BookDto searchBook(String id) throws ClassNotFoundException;


}
