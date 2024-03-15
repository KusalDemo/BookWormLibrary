package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.AdminDAO;
import org.example.entity.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean save(Admin dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(dto);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Admin dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(dto);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete from Admin where id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        int isDeleted = query.executeUpdate();
        transaction.commit();
        session.close();
        return isDeleted > 0;
    }

    @Override
    public List<Admin> getAll() throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Admin";
        Query query = session.createQuery(hql);
        List<Admin> admins = query.list();
        transaction.commit();
        session.close();
        return admins;
    }

    @Override
    public Admin search(String id) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Admin where adminId=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        Admin admin = (Admin) query.uniqueResult();
        transaction.commit();
        session.close();
        return admin;
    }
}
