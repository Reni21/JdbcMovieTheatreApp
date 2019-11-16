package com.reni.hi;

import com.reni.hi.web.command.Command;
import com.reni.hi.web.command.CommandFactory;
import com.reni.hi.web.PageData;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Command command = CommandFactory.getCommand(path, req.getMethod());

        PageData pageData = command.execute(req);
        if (pageData.isRedirect()) {
            resp.sendRedirect(pageData.getUrl());
        } else {
            req.getRequestDispatcher(pageData.getUrl()).forward(req, resp);
        }
    }

    private String getPath(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        int index = requestUri.lastIndexOf('/');
        String endPoint = requestUri.substring(index + 1);
        if (endPoint.isEmpty()) {
            endPoint = "home";
        }
        LOG.info("Path: " + endPoint);
        return endPoint;
    }
}
