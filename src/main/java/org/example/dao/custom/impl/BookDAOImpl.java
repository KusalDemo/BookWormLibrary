package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.HQLUtil;
import org.example.dao.custom.BookDAO;
import org.example.entity.Book;
import org.hibernate.Session;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean save(Book dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Object save = session.save(dto);
        boolean isSaved = save != null;
        session.close();
        return isSaved;
    }

    @Override
    public boolean update(Book dto) throws ClassNotFoundException {
       String hql = "UPDATE Book SET title=:title, author=:author, genre=:genre, availability=:availability WHERE id=:id";
       return HQLUtil.executeUpdate(hql, dto);
    }

    @Override
    public boolean delete(String id) throws ClassNotFoundException {
        String hql = "DELETE FROM Book WHERE id=:id";
        return HQLUtil.executeUpdate(hql, id);
    }

    @Override
    public List<Book> getAll() throws ClassNotFoundException {
        String hql = "FROM Book";
        return HQLUtil.select(hql);
    }

    @Override
    public Book search(String title) throws ClassNotFoundException {
        String hql = "FROM Book WHERE title=:title";
        List<Book> books = HQLUtil.select(hql, title);
        if (books.size() > 0) {
            return books.get(0);
        }else{
            return null;
        }
    }
}
