package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDto;

import java.util.ArrayList;

public interface BookBO extends SuperBO {

    boolean saveBook(BookDto bookDto) throws ClassNotFoundException;

    boolean updateBook(BookDto bookDto) throws ClassNotFoundException;

    boolean deleteBook(String title) throws ClassNotFoundException;

    String searchBook(String title);

    boolean updateAvailability(String title, boolean availability);

    ArrayList<BookDto> getAllBooks();



}
