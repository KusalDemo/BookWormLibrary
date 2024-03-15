package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.entity.User;

public interface UserDAO extends CrudDAO<User> {
    boolean delete(String email) throws ClassNotFoundException;
    User search(String email) throws ClassNotFoundException;

    User searchFromName(String name) throws ClassNotFoundException;

    boolean updateMinor(User dto) throws ClassNotFoundException;

    boolean updatePassword(String username, String password) throws ClassNotFoundException;
}
