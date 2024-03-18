package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
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
        session.merge(dto);
        transaction.commit();
        session.close();
        return true;
    }
    @Override
    public boolean updateMinor(User dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        System.out.println(dto.getUserName()+" "+dto.getEmail());
        String hql = "update User set userName=:name, branch=:branch where email=:email";
        Query query = session.createQuery(hql);
        query.setParameter("name", dto.getUserName());
        query.setParameter("branch", dto.getBranch());
        query.setParameter("email", dto.getEmail());
        int isUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return isUpdated > 0;
    }

    @Override
    public boolean updatePassword(String username, String password) throws ClassNotFoundException {
        System.out.println(username+" "+password);
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "update User set password=:password where email=:username";
        Query query = session.createQuery(hql);
        query.setParameter("password", password);
        query.setParameter("username", username);
        int isUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
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
        transaction.commit();
        session.close();
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
    public User searchFromName(String name) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User where userName=:user";
        Query query = session.createQuery(hql);
        query.setParameter("user", name);
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
