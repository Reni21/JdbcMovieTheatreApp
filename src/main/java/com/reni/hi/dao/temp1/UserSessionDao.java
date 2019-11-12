package com.reni.hi.dao.temp1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserSessionDao {
    private static final UserSessionDao SESSION_DAO = new UserSessionDao();
    private final Map<String, String> sessionIds = new HashMap<>();

    private UserSessionDao() {
    }

    public static UserSessionDao getInstance(){
        return SESSION_DAO;
    }

    public synchronized String createSession(String login) {
        String session_id = UUID.randomUUID().toString();
        sessionIds.put(session_id, login);
        return session_id;
    }

    public synchronized String getLoginBySessId(String sessId){
        return sessionIds.get(sessId);
    }

    public synchronized String getActiveUsersAsString(){
        String activeUsers = "";
        for (String value : sessionIds.values()) {
            activeUsers = activeUsers + value + "\n";
        }
        return activeUsers.trim();
    }

    public synchronized boolean isSessionIdExist(String id){
        return sessionIds.containsKey(id);
    }

    public synchronized void deleteSessionId(String id){
        sessionIds.remove(id);
    }



}
