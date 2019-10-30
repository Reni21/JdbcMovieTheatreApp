function validateLoginForm() {
    var login = document.forms["loginForm"]["login"].value;
    var password = document.forms["loginForm"]["password"].value;
    if (login == null || login == "", password == null || password == "") {
        alert("Please Fill All Required Field");
        return false;
    }
}

function validateSignUpForm() {
    var login = document.forms["signUpForm"]["login"].value;
    var password = document.forms["signUpForm"]["password"].value;
    var email = document.forms["signUpForm"]["email"].value;
    if (validateField(login) || validateField(password) || validateField(email)) {
        alert("Please fill all required field");
        return false;
    }
}

function validateField(field) {
    if (field == null || field == "") {
        return true;
    }
}