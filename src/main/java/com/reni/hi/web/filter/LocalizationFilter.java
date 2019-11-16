package com.reni.hi.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocalizationFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LocalizationFilter.class);
    private static final String LOCALE = "locale";
    private static final String BUNDLE = "bundle";

    private String defaultLocale;
    private String defaultBundle;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.defaultLocale = filterConfig.getInitParameter(LOCALE);
        this.defaultBundle = filterConfig.getInitParameter(BUNDLE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        setLocale(req.getSession());
        setBundle(req.getSession());

        chain.doFilter(request, response);
    }

    private void setLocale(HttpSession session) {
        String existingLocale = (String) session.getAttribute(LOCALE);
        if (existingLocale == null) {
            LOG.info("Set new locale to session");
            session.setAttribute(LOCALE, defaultLocale);
        }
    }

    private void setBundle(HttpSession session) {
        String bundle = (String) session.getAttribute(BUNDLE);
        if (bundle == null) {
            LOG.info("Set bundle to session");
            session.setAttribute(BUNDLE, defaultBundle);
        }
    }

    @Override
    public void destroy() {

    }
}