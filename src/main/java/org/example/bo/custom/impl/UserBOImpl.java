package org.example.bo.custom.impl;

import org.example.bo.custom.UserBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.UserDAO;
import org.example.dto.UserDto;
import org.example.entity.User;

public class UserBOImpl implements UserBO {

    UserDAO userDAO=(UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);
    @Override
    public boolean saveUser(UserDto userDto) throws ClassNotFoundException {
        return userDAO.save(new User(userDto.getUserName(),userDto.getEmail(),userDto.getPassword(),null));

    }

    @Override
    public boolean updateUser(UserDto userDto) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public UserDto searchUser(String username) {
        return null;
    }

    @Override
    public boolean updatePassword(String username, String password) {
        return false;
    }
}
