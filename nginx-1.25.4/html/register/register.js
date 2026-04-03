

let captchaId = "";

function loadNewCaptcha() {
    captchaId = guid();
    let captReqInfo = {
        request_id: captchaId
    };

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/post/captcha", true);
    xhr.setRequestHeader("content-type", "application/json; charset=UTF-8");
    xhr.responseType = "blob";
    xhr.onload = function () {
        if (this.status == 200) {
            $("#imgCaptcha").attr("src", window.URL.createObjectURL(this.response));
        }
    }
    xhr.send(JSON.stringify(captReqInfo));
}

$("#imgCaptcha")[0].onload = function(e) {
    window.URL.revokeObjectURL(e.target.src);
};

$("#imgCaptcha").on("click", loadNewCaptcha);

$("#buttonSubmit").on("click", function () {

    $("input").each(function() {
        if ($(this).val() === "") {
            myAlert("Necessary information missed");
            return;
        }
    });

    const regReqInfo = {
        username: $("#inputUsername").val(),
        email: $("#inputEmail").val(),
        phone_number: $("#inputPhoneNumber").val(),
        password: $("#inputPassword").val(),
        captcha_id: captchaId,
        captcha_text: $("#inputCaptcha").val()
    };

    console.log(JSON.stringify(regReqInfo));

    $.post({
        url: "/post/register",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(regReqInfo),
        success: function (resp) {
            console.log(resp);
            //let regResInfo = JSON.parse(resp);
            if (resp.err !== "NONE") {
                myAlert(resp.err);
                return;
            }
            location.assign("/login");
        }
    });
});

$("#buttonVerify").on("click", () => {
    const emailRegulation = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    let emailAddress = $("#inputEmail").val();
    if (!emailRegulation.test(emailAddress)) {
        myAlert("Illegal email address");
    }
    const vrfReqInfo = {
        email: emailAddress
    }
    $.post("/register/post", JSON.stringify(vrfReqInfo), () => {
        $("#buttonVerify").val("sent");
    });
});

function myAlert(message) {
    const wrapper = document.createElement('div');
    wrapper.innerHTML = [
        `<div class="alert alert-danger alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="buttonAlertClosing"></button>',
        '</div>'
    ].join('');
    $("#alertPlaceholder").html(wrapper);
}

function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}

$(() => {
    loadNewCaptcha();
});