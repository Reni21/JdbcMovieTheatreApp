package com.reni.hi.web.command;

import com.reni.hi.dao.DaoFactory;
import com.reni.hi.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> getCommandMap = new HashMap<>();
    private static Map<String, Command> postCommandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static {
        getCommandMap.put("app", new HomeCommand());
        getCommandMap.put("movie", new MovieCommand(DaoFactory.getMovieDao()));
        getCommandMap.put("schedule", new ScheduleCommand(
                ServiceFactory.getMovieSessionService(), ServiceFactory.getWeekScheduleDatesService()));
        getCommandMap.put("404", defaultCommand);
    }

    private CommandFactory() {
    }

    public static Command getCommand(String path, String type) {
        return "GET".equals(type)
                ? getCommand(path)
                : postCommand(path);
    }

    private static Command getCommand(String path) {
        return getCommandMap.getOrDefault(path, defaultCommand);
    }

    private static Command postCommand(String path) {
        return postCommandMap.getOrDefault(path, defaultCommand);
    }
}
