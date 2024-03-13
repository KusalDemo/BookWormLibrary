package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.BorrowBooksDAO;
import org.example.entity.BorrowBooks;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BorrowBooksDAOImpl implements BorrowBooksDAO {
    @Override
    public boolean save(BorrowBooks dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(dto);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(BorrowBooks dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<BorrowBooks> getAll() throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from BorrowBooks";
        Query query = session.createQuery(hql);
        List<BorrowBooks> borrowBooks = query.list();
        transaction.commit();
        session.close();
        return borrowBooks;
    }

    @Override
    public BorrowBooks search(String id) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from BorrowBooks where id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        BorrowBooks borrowBooks = (BorrowBooks) query.uniqueResult();
        transaction.commit();
        session.close();
        return borrowBooks;
    }

    @Override
    public List<BorrowBooks> getAllBorrowBooksFromUserId(String id) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from BorrowBooks where user=:userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", id);
        List<BorrowBooks> borrowBooks = query.list();
        transaction.commit();
        session.close();
        return borrowBooks;
    }

}
