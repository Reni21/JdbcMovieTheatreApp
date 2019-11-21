package com.theatre.movie.web.command;

import com.theatre.movie.dao.DaoFactory;
import com.theatre.movie.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> getCommandMap = new HashMap<>();
    private static Map<String, Command> postCommandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static {
        getCommandMap.put("main", new HomeCommand());
        getCommandMap.put("movie", new MovieCommand(DaoFactory.getMovieDao()));
        getCommandMap.put("schedule", new ScheduleCommand(
                ServiceFactory.getMovieSessionService(), ServiceFactory.getWeekScheduleDatesService()));
        getCommandMap.put("404", defaultCommand);
        getCommandMap.put("login", new LoginGetCommand());
        getCommandMap.put("sign-up", new SignUpGetCommand());
        getCommandMap.put("account", new AccountCommand());
        getCommandMap.put("logout", new LogOutCommand());
        getCommandMap.put("movie-session", new MovieSessionCommand(
                ServiceFactory.getWeekScheduleDatesService(), ServiceFactory.getMovieSessionsScheduleService()));

        postCommandMap.put("locale", new LanguageCommand());
        postCommandMap.put("login", new LoginPostCommand(ServiceFactory.getUserService()));
        postCommandMap.put("sign-up", new SignUpPostCommand(ServiceFactory.getUserService()));
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
