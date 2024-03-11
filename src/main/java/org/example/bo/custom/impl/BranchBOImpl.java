package org.example.bo.custom.impl;

import org.example.bo.custom.BranchBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.BranchDAO;
import org.example.dao.custom.UserDAO;
import org.example.dto.BranchDto;
import org.example.entity.Book;
import org.example.entity.Branch;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BranchBOImpl implements BranchBO {
    BranchDAO branchDAO = (BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BRANCH);
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BOOK);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);
    @Override
    public boolean saveBranch(BranchDto branchDto) throws ClassNotFoundException {
        List<Book> allBooks = bookDAO.getAll();
        List<User> allUsers = userDAO.getAll();
        return branchDAO.save(new Branch(branchDto.getBranchId(),branchDto.getBranchName(),branchDto.getLocation(),branchDto.getEmail(),allBooks,allUsers));
    }

    @Override
    public boolean updateBranch(BranchDto branchDto) throws ClassNotFoundException {
        List<Book> allBooks = bookDAO.getAll();
        List<User> allUsers = userDAO.getAll();
        return branchDAO.update(new Branch(branchDto.getBranchId(),branchDto.getBranchName(),branchDto.getLocation(),branchDto.getEmail(),allBooks,allUsers));
    }

    @Override
    public boolean deleteBranch(String branchId) throws ClassNotFoundException {
        return branchDAO.delete(branchId);
    }

    @Override
    public ArrayList<BranchDto> getAllBranches() throws ClassNotFoundException {
        ArrayList<Branch> branches = (ArrayList<Branch>) branchDAO.getAll();
        ArrayList<BranchDto> branchDtos = new ArrayList<>();
        for (Branch branch : branches) {
            List<String> bookIds = branch.getBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            List<String> userIds = branch.getUsers().stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
            branchDtos.add(new BranchDto(branch.getBranchId(), branch.getBranchName(), branch.getLocation(), branch.getEmail(), bookIds, userIds));
        }
        return branchDtos;
    }
}
