import docCookies from "/cookie.js";


$("#buttonSubmit").on("click", submitLoginInfo);


function submitLoginInfo() {
    let sndMsg = {
        identifier: $("#inputIdentifier").val(),
        password: $("#inputPassword").val()
    }
    $.post("/post/login", JSON.stringify(sndMsg), loginSuccess);
}

function loginSuccess(data, textStatus) {
    let recMsg = JSON.parse(data);
    switch (recMsg.err) {
        case "NONE":
            saveUsernameAndPassword();
            location.assign("/dashboard");
            break;
        case "NO_SUCH_USER":
            myAlert("no such user");
            break;
        case "WRONG_PASSWORD":
            myAlert("wrong password");
            break;
    }
}

function saveUsernameAndPassword() {
    docCookies.setItem("identifier", $("#inputIdentifier").val());
    docCookies.setItem("password", $("#inputPassword").val());
}


function myAlert(message) {
    const wrapper = document.createElement('div');
    wrapper.innerHTML = [
        `<div class="alert alert-danger alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        `   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="buttonAlertClosing"></button>`,
        `</div>`
    ].join('');
    $("#alertPlaceholder").html(wrapper);
}

$(() => {
    if (docCookies.hasItem("identifier") && docCookies.hasItem("password")) {
        $("#inputIdentifier").val(docCookies.getItem("identifier"));
        $("#inputPassword").val(docCookies.getItem("password"));
    }
})