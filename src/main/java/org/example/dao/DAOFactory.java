package org.example.dao;

import org.example.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        USER, BOOK, BRANCH,ADMIN,BORROWBOOKS
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case USER:
                return new UserDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case BRANCH:
                return new BranchDAOImpl();
            case ADMIN:
                return new AdminDAOImpl();
            case BORROWBOOKS:
                return new BorrowBooksDAOImpl();
            default:
                return null;
        }
    }
}
