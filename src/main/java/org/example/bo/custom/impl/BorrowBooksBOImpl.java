package org.example.bo.custom.impl;

import org.example.bo.custom.BorrowBooksBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.entity.Book;
import org.example.entity.BorrowBooks;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class BorrowBooksBOImpl implements BorrowBooksBO {

    BorrowBooksDAO borrowBooksDAO = (BorrowBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BORROWBOOKS);

    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BOOK);
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

    @Override
    public List<BorrowBooksDto> getAllBorrowBooksFromUserId(String id) throws ClassNotFoundException {
        List<BorrowBooks> allBorrowBooksFromUserId = borrowBooksDAO.getAllBorrowBooksFromUserId(id);
        List<BorrowBooksDto> borrowBooksDtos = new ArrayList<>();
        for (BorrowBooks borrowBooks : allBorrowBooksFromUserId) {
            User user = borrowBooks.getUser();
            Book book = borrowBooks.getBook();
            BranchDto branchDto = new BranchDto(user.getBranch().getBranchId(),user.getBranch().getBranchName(),user.getBranch().getEmail(),user.getBranch().getLocation());
            UserDto userDto = new UserDto(user.getEmail(),user.getUserName(),user.getPassword(),branchDto);
            BranchDto branchDto1 = new BranchDto(book.getBranch().getBranchId(),book.getBranch().getBranchName(),book.getBranch().getEmail(),book.getBranch().getLocation());
            BookDto bookDto = new BookDto(book.getId(),book.getAuthor(),book.getTitle(),book.getGenre(),book.isAvailability(),branchDto1);
            borrowBooksDtos.add(new BorrowBooksDto(borrowBooks.getId(),userDto,bookDto,borrowBooks.getBorrowDate(),borrowBooks.getReturnDate(),borrowBooks.getStatus()));
        }
        return borrowBooksDtos;
    }

    @Override
    public List<BorrowBooksDto> getAllAvailableBooksFromBranchId(String id) throws ClassNotFoundException {
        List<BorrowBooks> allBorrowBooksFromUserId = borrowBooksDAO.getAllBorrowBooksFromUserId(id);
        List<BorrowBooksDto> borrowBooksDtos = new ArrayList<>();
        for (BorrowBooks borrowBooks : allBorrowBooksFromUserId) {
            User user = borrowBooks.getUser();
            Book book = borrowBooks.getBook();
            BranchDto branchDto = new BranchDto(user.getBranch().getBranchId(),user.getBranch().getBranchName(),user.getBranch().getEmail(),user.getBranch().getLocation());
            UserDto userDto = new UserDto(user.getEmail(),user.getUserName(),user.getPassword(),branchDto);
            BranchDto branchDto1 = new BranchDto(book.getBranch().getBranchId(),book.getBranch().getBranchName(),book.getBranch().getEmail(),book.getBranch().getLocation());
            BookDto bookDto = new BookDto(book.getId(),book.getAuthor(),book.getTitle(),book.getGenre(),book.isAvailability(),branchDto1);
            borrowBooksDtos.add(new BorrowBooksDto(borrowBooks.getId(),userDto,bookDto,borrowBooks.getBorrowDate(),borrowBooks.getReturnDate(),borrowBooks.getStatus()));
        }
        return borrowBooksDtos;
    }

}
