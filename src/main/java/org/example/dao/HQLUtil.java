package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HQLUtil {
    public static <T> List<T> select(String hql, Object... args) {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<T> query = session.createQuery(hql);

            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }

            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute select query", e);
        }
    }

    public static boolean executeUpdate(String hql, Object... args) {

        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
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
    }
}
