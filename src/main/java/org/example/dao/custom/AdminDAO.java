package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.entity.Admin;

public interface AdminDAO extends CrudDAO<Admin> {
    boolean updatePassword(String username, String password) throws ClassNotFoundException;
}
