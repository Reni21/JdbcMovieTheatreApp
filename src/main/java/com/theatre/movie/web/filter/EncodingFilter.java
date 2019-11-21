package com.theatre.movie.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
    private static final String ENCODING_UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.info("Execution EncodingFilter.");
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(ENCODING_UTF_8);
        }
        response.setCharacterEncoding(ENCODING_UTF_8);
        chain.doFilter(request, response);
    }
}
