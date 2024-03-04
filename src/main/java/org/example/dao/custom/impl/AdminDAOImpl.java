package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.HQLUtil;
import org.example.dao.custom.AdminDAO;
import org.example.entity.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean save(Admin dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Boolean isSaved = (Boolean) session.save(dto);
        transaction.commit();
        session.close();
        return isSaved;
    }

    @Override
    public boolean update(Admin dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<Admin> getAll() throws ClassNotFoundException {
        return null;
    }

    @Override
    public Admin search(int id) throws ClassNotFoundException {
        return null;
    }
}
