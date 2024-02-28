package org.example.bo;

import org.example.bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getBoFactory(){
        return boFactory== null ? boFactory=new BoFactory() : boFactory;
    }

    public enum BOType{
        USER,
        BOOK,
        BRANCH,
        ADMIN
    }
    public SuperBO getBO(BOType boType){
        switch (boType){
            case USER:
                return new UserBOImpl();
            case BOOK:
                return new BookBOImpl();
            case BRANCH:
                return new BranchBOImpl();
            case ADMIN:
                return new AdminBOImpl();
            default:
                return null;
        }
    }
}
