package org.example.dao.custom.impl;

import org.example.dao.custom.BranchDAO;
import org.example.entity.Branch;

import java.util.List;

public class BranchDAOImpl implements BranchDAO {
    @Override
    public boolean save(Branch dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Branch dto) throws ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ClassNotFoundException {
        return false;
    }

    @Override
    public List<Branch> getAll() throws ClassNotFoundException {
        return null;
    }

    @Override
    public Branch search(int id) throws ClassNotFoundException {
        return null;
    }
}
