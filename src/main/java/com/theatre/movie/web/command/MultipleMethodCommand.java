package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;
import com.theatre.movie.web.form.mapper.RequestFormMapper;
import com.theatre.movie.web.form.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;

public abstract class MultipleMethodCommand implements Command {

    @Override
    public PageData execute(HttpServletRequest request) {
        String type = request.getMethod();

        return "GET".equals(type)
                ? performGet(request)
                : performPost(request);
    }

    protected abstract PageData performGet(HttpServletRequest request);

    protected abstract PageData performPost(HttpServletRequest request);
    
    protected <T> T mapForm(HttpServletRequest request, RequestFormMapper<T> mapper){
        return mapper.map(request);
    }

    protected <T> boolean validateForm(T form, FormValidator<T> formValidator){
        return formValidator.validate(form);
    }
}
