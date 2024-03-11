package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.HQLUtil;
import org.example.dao.custom.BookDAO;
import org.example.entity.Book;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean save(Book dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(dto);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Book dto) throws ClassNotFoundException {
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
        String hql = "delete from Book where id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        int isDeleted = query.executeUpdate();
        transaction.commit();
        session.close();
        return isDeleted > 0;
    }

    @Override
    public List<Book> getAll() throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Book";
        Query query = session.createQuery(hql);
        List<Book> books = query.list();
        transaction.commit();
        session.close();
        return books;
    }

    @Override
    public Book search(String title) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Book where title=:title";
        Query query = session.createQuery(hql);
        query.setParameter("title", title);
        Book book = (Book) query.uniqueResult();
        transaction.commit();
        session.close();
        return book;
    }
}
