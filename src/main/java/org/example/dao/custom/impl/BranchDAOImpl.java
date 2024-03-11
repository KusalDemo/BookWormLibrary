package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.HQLUtil;
import org.example.dao.custom.BranchDAO;
import org.example.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BranchDAOImpl implements BranchDAO {
    @Override
    public boolean save(Branch dto) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(dto);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Branch dto) throws ClassNotFoundException {
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
        String hql = "delete from Branch where branchId=:branchId";
        Query query = session.createQuery(hql);
        query.setParameter("branchId", id);
        int isDeleted = query.executeUpdate();
        transaction.commit();
        session.close();
        return isDeleted > 0;
    }

    @Override
    public List<Branch> getAll() throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Branch";
        Query query = session.createQuery(hql);
        List<Branch> branches = query.list();
        transaction.commit();
        session.close();
        return branches;
    }

    @Override
    public Branch search(String id) throws ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from Branch where branchId=:branchId";
        Query query = session.createQuery(hql);
        query.setParameter("branchId", id);
        Branch branch = (Branch) query.uniqueResult();
        transaction.commit();
        session.close();
        return branch;
    }
}
