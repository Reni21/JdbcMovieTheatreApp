package com.theatre.movie.web;

import com.theatre.movie.web.command.Command;
import com.theatre.movie.web.command.CommandFactory;
import com.theatre.movie.web.command.response.CommandResponse;
import com.theatre.movie.web.command.response.ErrorResponse;
import com.theatre.movie.web.command.response.PageResponse;
import com.theatre.movie.web.command.response.SuccessResponse;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/app/*")
public class DispatcherServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("doGET");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("doPost");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        LOG.info("Will get command py path: " + path);
        Command command = CommandFactory.getCommand(path, req.getMethod());
        LOG.info("Extracted command " + command + command.getClass());

        CommandResponse commandResponse = command.execute(req);

        switch (commandResponse.getResponseType()) {
            case PAGE: {
                PageResponse pageResponse = (PageResponse) commandResponse;
                if (pageResponse.isRedirect()) {
                    String url = pageResponse.getUrl();
                    LOG.info("Request redirect into new url: " + url);
                    resp.sendRedirect(url);

                } else {
                    String modifiedPath = "/WEB-INF/pages/" + pageResponse.getUrl();
                    LOG.info("Request forward into modified path: " + modifiedPath);
                    req.getRequestDispatcher(modifiedPath).forward(req, resp);
                }
                break;
            }
            case PAYLOAD: {
                SuccessResponse successResponse = (SuccessResponse) commandResponse;
                PrintWriter out = resp.getWriter();
                resp.setCharacterEncoding("UTF-8");
                out.print(successResponse.getPayload());
                out.flush();
                break;
            }
            case ERROR: {
                ErrorResponse errorResponse = (ErrorResponse) commandResponse;
                resp.setStatus(errorResponse.getHttpStatus());
                PrintWriter out = resp.getWriter();
                resp.setCharacterEncoding("UTF-8");
                out.print(errorResponse.getErrorMsg());
                out.flush();
                break;
            }
            default:
                throw new IllegalStateException("Unknown command response type");
        }
    }

    private String getPath(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        int cutFromIndex = requestUri.lastIndexOf("app/") + 4;
        String endPoint = requestUri.substring(cutFromIndex);

        if (endPoint.contains("/")) {
            int cutToIndex = endPoint.lastIndexOf('/');
            endPoint = endPoint.substring(0, cutToIndex);
        }
        return endPoint;
    }
}
