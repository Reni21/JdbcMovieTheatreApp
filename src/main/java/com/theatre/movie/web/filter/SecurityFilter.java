package com.theatre.movie.web.filter;

import com.theatre.movie.entity.User;
import com.theatre.movie.web.SecurityConfig;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = getPath(req);
        if (!SecurityConfig.isSecurePage(path)) {
            LOG.info("Page is not secured: " + path);
            chain.doFilter(request, response);
            return;
        }

        String contextPath = req.getContextPath();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            LOG.info("User is not logged");
            resp.sendRedirect(contextPath + "/login");
            return;
        }

        boolean hasPermission = SecurityConfig.hasPermission(path, user.getRole());

        if (!hasPermission) {
            LOG.info("User has no permission : " + user + " , " + path);
            resp.sendRedirect(contextPath + "/403.jsp");
            return;
        }

        LOG.info("User has permission. Continue");
        chain.doFilter(request, response);
    }

    private String getPath(HttpServletRequest req) {
        String requestUri = req.getRequestURI();
        int indexFrom = requestUri.lastIndexOf("theatre/") + 8;
        String endPoint = requestUri.substring(indexFrom);
        if (endPoint.contains("/")) {
            int indexTo = endPoint.lastIndexOf('/');
            endPoint = endPoint.substring(0, indexTo);
        }
        LOG.info("Path: " + (endPoint.isEmpty() ? "context path" : endPoint));
        return "/" + endPoint;
    }
}
