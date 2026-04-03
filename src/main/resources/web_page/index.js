import docCookies from "/cookie.js"

function getStarted() {
    if (docCookies.getItem("tempKey") !== null) {
        
        location.assign("/dashboard")
    } else {
        location.assign("/login/login.html")
    }
}