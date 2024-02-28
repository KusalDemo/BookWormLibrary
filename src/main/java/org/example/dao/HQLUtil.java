package org.example.dao;

import org.example.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HQLUtil {
    public static <T> T execute(String hql, Object... args) throws ClassNotFoundException {


        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(hql);

        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }

        if (hql.startsWith("SELECT") || hql.startsWith("select")) {
            transaction.commit();
            session.close();
            return (T) query.list();
        } else {
            transaction.commit();
            session.close();
            return (T) (Boolean) (query.executeUpdate() > 0);
        }
    }

}
