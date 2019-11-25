package com.theatre.movie.web.form.validator;

public interface FormValidator<T> {
    
     boolean validate(T form);
}
