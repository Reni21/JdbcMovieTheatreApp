function validateLoginForm() {
    var username = document.forms["loginForm"]["username"].value;
    var password = document.forms["loginForm"]["password"].value;
    if (username == null || username == "", password == null || password == "") {
        alert("Please Fill All Required Field");
        return false;
    }
}

function validateSignUpForm() {
    var username = document.forms["signUpForm"]["username"].value;
    var password = document.forms["signUpForm"]["password"].value;
    var email = document.forms["signUpForm"]["email"].value;
    if (validateField(username) || validateField(password) || validateField(email)) {
        alert("Please fill all required field");
        return false;
    }
}

function validateField(field) {
    if (field == null || field == "") {
        return true;
    }
}