package com.theatre.movie.web.form.validator;

import com.theatre.movie.web.form.RegistrationForm;

public class RegistrationFormValidator {
   
    public static boolean validate(RegistrationForm form) {
        return validatePassword(form) 
                && validateLogin(form);
    }

    private static boolean validatePassword(RegistrationForm form) {
        String password = form.getPassword();
        String passwordConfirm = form.getPasswordConfirm();

        return password != null 
                && password.equals(passwordConfirm);
    }
    
    private static boolean validateLogin(RegistrationForm form){
        String login = form.getLogin();
        return login != null && login.length() > 2;
    }
}
