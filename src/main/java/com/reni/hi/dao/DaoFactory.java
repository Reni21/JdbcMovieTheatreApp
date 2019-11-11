package com.reni.hi.dao;

import com.reni.hi.dao.DaoType;
import com.reni.hi.dao.EntityDao;
import com.reni.hi.dao.MovieDaoImpl;
import com.reni.hi.dao.UserDaoImpl;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static Map<DaoType, EntityDao> daoMap = new HashMap<>();

    static {
        daoMap.put(DaoType.USER, new UserDaoImpl());
        daoMap.put(DaoType.MOVIE, new MovieDaoImpl());
    }

    private DaoFactory() {
    }

    public static EntityDao getEntityDao(DaoType daoType){
        EntityDao entityDao = daoMap.get(daoType);
        if(entityDao != null ){
            return entityDao;
        }
        throw new RuntimeException("Dao with current dao type do not exist: " + daoType.name());
    }
}
