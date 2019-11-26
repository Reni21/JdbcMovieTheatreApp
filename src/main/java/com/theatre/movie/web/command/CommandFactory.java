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
        getCommandMap.put("schedule", new ScheduleCommand(
                ServiceFactory.getMovieSessionService(), ServiceFactory.getWeekScheduleDatesService()));
        getCommandMap.put("404", defaultCommand);
        getCommandMap.put("login", new LoginCommand(ServiceFactory.getUserService()));
        getCommandMap.put("sign-up", new SignUpCommand(ServiceFactory.getUserService()));
        getCommandMap.put("account", new AccountCommand(ServiceFactory.getBookingService()));
        getCommandMap.put("logout", new LogOutCommand());
        getCommandMap.put("movie-session", new MovieSessionCommand(
                ServiceFactory.getWeekScheduleDatesService(), ServiceFactory.getMovieSessionService()));

        postCommandMap.put("locale", new LanguageCommand());
        postCommandMap.put("login", new LoginCommand(ServiceFactory.getUserService()));
        postCommandMap.put("sign-up", new SignUpCommand(ServiceFactory.getUserService()));
        postCommandMap.put("booking", new BookingCommand(ServiceFactory.getBookingService()));
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
