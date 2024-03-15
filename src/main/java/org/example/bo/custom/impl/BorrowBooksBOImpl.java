package org.example.bo.custom.impl;

import org.example.bo.custom.BorrowBooksBO;
import org.example.config.FactoryConfiguration;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.entity.Book;
import org.example.entity.BorrowBooks;
import org.example.entity.Branch;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BorrowBooksBOImpl implements BorrowBooksBO {

    BorrowBooksDAO borrowBooksDAO = (BorrowBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BORROWBOOKS);
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BOOK);


    @Override
    public boolean saveBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException {
        BookDto bookDto = borrowBooksDto.getBook();
        Book book = new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),bookDto.isAvailability(),null,null);
        UserDto userDto = borrowBooksDto.getUser();
        Branch branch = new Branch(userDto.getBranchDto().getBranchId(),userDto.getBranchDto().getBranchName(),userDto.getBranchDto().getLocation(),userDto.getBranchDto().getEmail(),null,null);
        User user = new User(userDto.getEmail(),userDto.getUserName(),userDto.getPassword(),branch,null);
        BorrowBooks borrowBooks = new BorrowBooks(borrowBooksDto.getId(),user,book,borrowBooksDto.getBorrowDate(),borrowBooksDto.getReturnDate(),borrowBooksDto.getStatus());

       /* Boolean isBorroweBookProcceed= false;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.persist(borrowBooks);

            book.setAvailability(false);
            session.update(book);

            transaction.commit();
            isBorroweBookProcceed = true;
        }catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }return isBorroweBookProcceed;*/

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(borrowBooks);
        book.setAvailability(false);
        session.update(book);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean updateBorrowBook(BorrowBooksDto borrowBooksDto) throws ClassNotFoundException {
        BookDto bookDto = borrowBooksDto.getBook();
        Book book = new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),bookDto.isAvailability(),null,null);
        UserDto userDto = borrowBooksDto.getUser();
        Branch branch = new Branch(userDto.getBranchDto().getBranchId(),userDto.getBranchDto().getBranchName(),userDto.getBranchDto().getLocation(),userDto.getBranchDto().getEmail(),null,null);
        User user = new User(userDto.getEmail(),userDto.getUserName(),userDto.getPassword(),branch,null);
        /*BorrowBooks borrowBooks = new BorrowBooks(borrowBooksDto.getId(),user,book,borrowBooksDto.getBorrowDate(),borrowBooksDto.getReturnDate(),borrowBooksDto.getStatus());*/
        // Create a new BorrowBooks entity
        BorrowBooks borrowBooks = new BorrowBooks();
        borrowBooks.setId(borrowBooksDto.getId());
        borrowBooks.setUser(user);
        borrowBooks.setBook(book);
        borrowBooks.setBorrowDate(borrowBooksDto.getBorrowDate());
        borrowBooks.setReturnDate(borrowBooksDto.getReturnDate());
        borrowBooks.setStatus(borrowBooksDto.getStatus());

        Boolean isBorroweBookReturn = false;

        Session session = FactoryConfiguration.getInstance().getSession();
        try{
            Transaction transaction = session.beginTransaction();
            borrowBooksDto.setStatus("Returned");
            session.update(borrowBooks);

            bookDto.setAvailability(true);
            session.update(book);

            transaction.commit();
            isBorroweBookReturn = true;
        }catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }return isBorroweBookReturn;
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

    @Override
    public List<BorrowBooksDto> getReturnDateExceededBooks(String id) throws ClassNotFoundException {
        List<BorrowBooks> returnDateExceededBooks = borrowBooksDAO.getReturnDateExceededBooks(id);
        List<BorrowBooksDto> borrowBooksDtos = new ArrayList<>();
        for (BorrowBooks borrowBooks : returnDateExceededBooks) {
            User user = borrowBooks.getUser();
            Book book = borrowBooks.getBook();
            BranchDto branchDto = new BranchDto(user.getBranch().getBranchId(),user.getBranch().getBranchName(),user.getBranch().getEmail(),user.getBranch().getLocation());
            UserDto userDto = new UserDto(user.getEmail(),user.getUserName(),user.getPassword(),branchDto);
            BranchDto branchDto1 = new BranchDto(book.getBranch().getBranchId(),book.getBranch().getBranchName(),book.getBranch().getEmail(),book.getBranch().getLocation());
            BookDto bookDto = new BookDto(book.getId(),book.getAuthor(),book.getTitle(),book.getGenre(),book.isAvailability(),branchDto1);
            borrowBooksDtos.add(new BorrowBooksDto(borrowBooks.getId(),userDto,bookDto,borrowBooks.getBorrowDate(),borrowBooks.getReturnDate(),borrowBooks.getStatus()));
        }return borrowBooksDtos;
    }

}
