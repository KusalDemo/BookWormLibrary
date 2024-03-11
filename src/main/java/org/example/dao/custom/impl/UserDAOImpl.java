package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.HQLUtil;
import org.example.dao.custom.UserDAO;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object save = session.save(dto);
        boolean isSaved = save != null;
        transaction.commit();
        session.close();
        return isSaved;
    }

    @Override
    public boolean update(User dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "update User set userName=:name, password=:password where email=:email";
        Query query = session.createQuery(hql);
        query.setParameter("name", dto.getUserName());
        query.setParameter("password", dto.getPassword());
        query.setParameter("email", dto.getEmail());
        int isUpdated = query.executeUpdate();
        return isUpdated > 0;
    }


    @Override
    public boolean delete(String email) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete from User where email=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        int isDeleted = query.executeUpdate();
        return isDeleted > 0;
    }
    @Override
    public User search(String email) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User where email=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        User user = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User";
        Query query = session.createQuery(hql);
        List<User> users = query.list();
        transaction.commit();
        session.close();
        return users;
    }
}
