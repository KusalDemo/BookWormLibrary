package org.example.bo.custom.impl;

import org.example.bo.custom.UserBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.dao.custom.BranchDAO;
import org.example.dao.custom.UserDAO;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.entity.BorrowBooks;
import org.example.entity.Branch;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserBOImpl implements UserBO {

    UserDAO userDAO=(UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);
    BorrowBooksDAO borrowBooksDAO=(BorrowBooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BORROWBOOKS);
    BranchDAO branchDAO=(BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.BRANCH);
    @Override
    public boolean saveUser(UserDto userDto) throws ClassNotFoundException {
        List<Branch> all = branchDAO.getAll();
        Branch selectedBranch = null;
        for (Branch branch : all) {
            if(branch.getBranchId().equals(userDto.getBranchDto().getBranchId())){
                selectedBranch = branch;
            }
        }
        return userDAO.save(new User(userDto.getUserName(),userDto.getEmail(),userDto.getPassword(),selectedBranch,null));

    }

    @Override
    public boolean updateUser(UserDto userDto) throws ClassNotFoundException {
        List<Branch> all = branchDAO.getAll();
        Branch selectedBranch = null;
        for (Branch branch : all) {
            if(branch.getBranchId().equals(userDto.getBranchDto().getBranchId())){
                selectedBranch = branch;
            }
        }
        List<BorrowBooks> allBorrowedBooks = borrowBooksDAO.getAll();
        List<BorrowBooks> filteredBorrowBooks = null;
        for(BorrowBooks borrowBooks : allBorrowedBooks){
           if(borrowBooks.getUser().getUserName().equals(userDto.getUserName())){
               /*filteredBorrowBooks = allBorrowedBooks.stream().filter(borrowBooks1 -> borrowBooks1.getUser().getUserName().equals(userDto.getUserName())).collect(Collectors.toList());*/
               filteredBorrowBooks.add(borrowBooks);
           }
        }
        return userDAO.update(new User(userDto.getUserName(),userDto.getEmail(),userDto.getPassword(),selectedBranch,filteredBorrowBooks));
    }

    @Override
    public boolean updateUserMinor(UserDto userDto) throws ClassNotFoundException {
        List<Branch> all = branchDAO.getAll();
        Branch selectedBranch = null;
        for (Branch branch : all) {
            if(branch.getBranchId().equals(userDto.getBranchDto().getBranchId())){
                selectedBranch = branch;
            }
        }
        List<BorrowBooks> allBorrowedBooks = borrowBooksDAO.getAll();
        List<BorrowBooks> filteredBorrowBooks = null;
        for(BorrowBooks borrowBooks : allBorrowedBooks){
            if(borrowBooks.getUser().getUserName().equals(userDto.getUserName())){
                /*filteredBorrowBooks = allBorrowedBooks.stream().filter(borrowBooks1 -> borrowBooks1.getUser().getUserName().equals(userDto.getUserName())).collect(Collectors.toList());*/
                filteredBorrowBooks.add(borrowBooks);
            }
        }
        return userDAO.updateMinor(new User(userDto.getUserName(),userDto.getEmail(),userDto.getPassword(),selectedBranch,filteredBorrowBooks));
    }

    @Override
    public boolean deleteUser(String userEmail) throws ClassNotFoundException {
        return userDAO.delete(userEmail);
    }

    @Override
    public List<UserDto> getAllUsers() throws ClassNotFoundException {
        List<User> all = userDAO.getAll();
        List<UserDto> userDtos = new ArrayList<>();

        for(User user : all){
            Branch branch = user.getBranch();
            BranchDto branchDto = new BranchDto(branch.getBranchId(),branch.getBranchName(),branch.getLocation(), branch.getEmail());
            userDtos.add(new UserDto(user.getUserName(),user.getEmail(),user.getPassword(),branchDto));
        }
        return userDtos;
    }


    @Override
    public boolean updatePassword(String username, String password) {
        return false;
    }
}
