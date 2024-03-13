package org.example.bo.custom.impl;

import org.example.bo.custom.BookBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.dao.custom.BranchDAO;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.entity.Book;
import org.example.entity.BorrowBooks;
import org.example.entity.Branch;

import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BOOK);
    BranchDAO branchDAO = (BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BRANCH);
    BorrowBooksDAO borrowBooksDAO = (BorrowBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BORROWBOOKS);
    @Override
    public boolean saveBook(BookDto bookDto) throws ClassNotFoundException {
        Branch selectedBranch = null;
        List<Branch> all = branchDAO.getAll();
        for (Branch branch : all) {
            if(branch.getBranchId().equals(bookDto.getBranch().getBranchId())){
                selectedBranch = branch;
            }
        }
        List<BorrowBooks> allBorrow = borrowBooksDAO.getAll();
        return bookDAO.save(new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),true,selectedBranch,allBorrow));
    }

    @Override
    public boolean updateBook(BookDto bookDto) throws ClassNotFoundException {
        Branch selectedBranch = null;
        List<Branch> all = branchDAO.getAll();
        for (Branch branch : all) {
            if(branch.getBranchId().equals(bookDto.getBranch().getBranchId())){
                selectedBranch = branch;
            }
        }
        List<BorrowBooks> allBorrow = borrowBooksDAO.getAll();
        return bookDAO.update(new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),bookDto.isAvailability(),selectedBranch,allBorrow));
    }

    @Override
    public boolean deleteBook(String title) throws ClassNotFoundException {
        return bookDAO.delete(title);
    }

    @Override
    public boolean updateAvailability(String id, boolean availability) {
        return bookDAO.updateAvailability(id, availability);
    }

    @Override
    public ArrayList<BookDto> getAllBooks() throws ClassNotFoundException {
        ArrayList<Book> books = (ArrayList<Book>) bookDAO.getAll();
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            Branch branch = book.getBranch();
            BranchDto branchDto = new BranchDto(branch.getBranchId(), branch.getBranchName(), branch.getLocation(), branch.getEmail());
            bookDtos.add(new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailability(),branchDto));
        }
        return bookDtos;
    }

    @Override
    public ArrayList<BookDto> getAllAvailableBooksFromBranchId(String branchId) throws ClassNotFoundException {
        ArrayList<Book> books = (ArrayList<Book>) bookDAO.getAllAvailableBooksFromBranchId(branchId);
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            Branch branch = book.getBranch();
            BranchDto branchDto = new BranchDto(branch.getBranchId(), branch.getBranchName(), branch.getLocation(), branch.getEmail());
            bookDtos.add(new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailability(),branchDto));
        }
        return bookDtos;
    }
}
