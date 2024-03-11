package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.example.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HQLUtil {
    /*public static <T> List<T> select(String hql, Object... args) {
        Session session1 = FactoryConfiguration.getInstance().getSession();
        try {
            Query<T> query = session1.createQuery(hql);

            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }

            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute select query", e);
        }finally {
            session1.close();
        }
    }

    public static boolean executeUpdate(String hql, Object... args) {
        Transaction transaction = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);

            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }

            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to execute update query", e);
        }
        finally {
            session.close();
        }
    }*/
    public static <T> List<T> select(String hql, Object... args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<T> query = session.createQuery(hql);

            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }

            List<T> resultList = query.list();
            transaction.commit();
            return resultList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to execute select query", e);
        } finally {
            session.close();
        }
    }

    public static boolean executeUpdate(String hql, Object... args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);

            /*for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }*/
            for (int i = 0; i < args.length; i++) {
                query.setParameter((String) args[i], args[i+1]);
            }

            int result = query.executeUpdate();
            transaction.commit();
            session.close();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to execute update query", e);
        } finally {

        }
    }
}
