package com.reni.hi.dao;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static Map<DaoType, EntityDao> daoMap = new HashMap<>();

    static {
        daoMap.put(DaoType.MOVIE_SESSION, new MovieSessionDaoImpl());
    }

    private DaoFactory() {
    }

    public static EntityDao getEntityDao(DaoType daoType){
        EntityDao entityDao = daoMap.get(daoType);
        if(entityDao != null ){
            return entityDao;
        }
        throw new RuntimeException("Dao with current dao-type does not exist: " + daoType.name());
    }
}
