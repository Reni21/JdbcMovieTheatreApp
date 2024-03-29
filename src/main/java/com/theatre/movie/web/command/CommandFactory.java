package com.theatre.movie.web.command;

import com.theatre.movie.dao.DaoFactory;
import com.theatre.movie.service.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Logger LOG = Logger.getLogger(CommandFactory.class);
    private static Map<String, Command> getCommandMap = new HashMap<>();
    private static Map<String, Command> postCommandMap = new HashMap<>();
    private static Command defaultCommand = new NotFoundCommand();

    static {
        getCommandMap.put("index", new HomeCommand());
        getCommandMap.put("movies", new MovieCommand(ServiceFactory.getMovieService()));
        getCommandMap.put("schedule", new ScheduleCommand(
                ServiceFactory.getMovieSessionService(),
                ServiceFactory.getWeekScheduleDatesService()));
        getCommandMap.put("404", defaultCommand);
        getCommandMap.put("login", new LoginCommand(ServiceFactory.getUserService()));
        getCommandMap.put("sign-up", new SignUpCommand(ServiceFactory.getUserService()));
        getCommandMap.put("account", new AccountCommand());
        getCommandMap.put("logout", new LogOutCommand());
        getCommandMap.put("movie-session", new MovieSessionCommand(
                ServiceFactory.getWeekScheduleDatesService(), ServiceFactory.getMovieSessionService()));
        getCommandMap.put("tickets", new BookingCommand(ServiceFactory.getBookingService()));

        postCommandMap.put("locale", new LanguageCommand());
        postCommandMap.put("movie-session", new MovieSessionCommand(
                ServiceFactory.getWeekScheduleDatesService(), ServiceFactory.getMovieSessionService()));
        postCommandMap.put("movies", new MovieCommand(ServiceFactory.getMovieService()));
        postCommandMap.put("login", new LoginCommand(ServiceFactory.getUserService()));
        postCommandMap.put("sign-up", new SignUpCommand(ServiceFactory.getUserService()));
        postCommandMap.put("booking", new BookingCommand(ServiceFactory.getBookingService()));
        postCommandMap.put("schedule", new ScheduleCommand(
                ServiceFactory.getMovieSessionService(),
                ServiceFactory.getWeekScheduleDatesService()));
    }

    private CommandFactory() {
    }

    public static Command getCommand(String path, String type) {
        LOG.info("Extract command " + type + " for path=" + path);
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
