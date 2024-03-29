package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    boolean save(T dto) throws ClassNotFoundException;
    boolean update(T dto) throws ClassNotFoundException;
    boolean delete(String id) throws ClassNotFoundException;

    List<T> getAll() throws ClassNotFoundException;

    T search(String id) throws ClassNotFoundException;

}
