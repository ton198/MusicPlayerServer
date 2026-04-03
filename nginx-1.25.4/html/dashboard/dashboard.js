

$(".my-list-item").on("click", function () {
    $(".my-list-item").removeClass("active");
    $(this).addClass("active");
});

let uploadCount = 0;
let lyricList = [];
let fileList = [];

$("#buttonAddUploadTask").on("click", function () {

    const taskHtml = 
    `<div id="uploadTask${uploadCount}" class="list-group-item list-group-item-action" aria-current="true">
        <label for="inputName${uploadCount}">Song Name:</label>
        <input type="text" id="inputName${uploadCount}">
        <label for="inputMediaFile${uploadCount}" style="margin-left: 30px;">Media File:</label>
        <input type="file" class="btn btn-outline-primary" style="width: 200px;" id="inputMediaFile${uploadCount}">
        <label for="inputLyricFile${uploadCount}" style="margin-left: 30px;">Lyric File:</label>
        <input type="file" class="btn btn-outline-primary" style="width: 200px;" id="inputLyricFile${uploadCount}">
        <input class="form-check-input me-1" type="checkbox" style="margin-left: 30px;" id="inputAccessibility${uploadCount}">
        <label class="form-check-label" for="inputAccessibility${uploadCount}">Set As Private</label>
        <button type="button" class="btn-close" aria-label="close" style="float: right;" id="buttonClose${uploadCount}"></button>
    </div>`
    $("#uploadFileTaskList").append(taskHtml);
    fileList.push("");
    lyricList.push("");

    $(`#buttonClose${uploadCount}`).on("click", function () {
        $(this).parent().remove();
    });
    $(`#inputMediaFile${uploadCount}`).on("change", function () {
        if (!this.files.length) return;
        let index = parseInt(this.id.slice(14));
        fileList[index] = this.files[0];
    });
    $(`#inputLyricFile${uploadCount}`).on("change", function () {
        if (!this.files.length) return;
        let index = parseInt(this.id.slice(14));
        let reader = new FileReader();
        reader.onload = function () {
            lyricList[index] = this.result;
        }
        reader.readAsText(this.files[0]);
    });

    uploadCount++;
});



$("#buttonUpload").on("click", function () {
    for (let i = 0;i < uploadCount;i++) {
        if ($(`#uploadTask${i}`) == null) continue;
        let songAccess = "";

        if ($(`#inputAccessibility${i}`).prop("checked")) {
            songAccess = "PRIVATE";
        } else {
            songAccess = "PUBLIC";
        }

        let uploadRequest = new FormData();
        uploadRequest.append("name", $(`#inputName${i}`).val());
        uploadRequest.append("lyric", lyricList[i]);
        uploadRequest.append("accessibility", songAccess);
        uploadRequest.append("file", fileList[i]);
        
        if (!validUploadRequest(uploadRequest)) {
            myAlert("INVALID_UPLOAD_REQUEST");
            return;
        }


        $.post({
            url: "/post/upload",
            data: uploadRequest,
            processData: false,
            contentType: false,
            success: function (resp) {
                if (resp.err === "NONE") {
                    myAlert("UPLOAD_SUCCESS");
                } else {
                    myAlert(resp.err);
                }
            }
        });
    }
});

function validUploadRequest(request) {
    return  notEmptyString(request.get("name")) && notEmptyString(request.get("lyric")) && request.get("file") != null && (request.get("accessibility") === "PRIVATE" || request.get("accessibility") === "PUBLIC");
}

function notEmptyString(str) {
    return str !== null && str !== "";
}

function addClickEffect(id) {
    $(`#${id}`).on("click", function () {
        $(".my-list-item").removeClass("active");
        $(this).addClass("active");

        $("#audioPlayer")[0].src = "";
        let req = {
            music_id: id
        }

        $.post({
            url: "/post/download",
            data: JSON.stringify(req),
            contentType: "application/json",
            success: function (resp) {
                if (resp.err === "NONE") {
                    $("#audioPlayer")[0].src = resp.url;
                } else {
                    myAlert(resp.err);
                }
            },
            error: function(e) {
                if (e.status == 401) {
                    location.assign("/login");
                }
            }
        });
    });
}

function addMusicInfoToList(metadata, location) {

    let listHtml = 
    `   <div class="list-group-item list-group-item-action my-list-item" aria-current="true" id="${metadata.musicId}"> `+
    `       <div class="d-flex w-100 justify-content-between">                                                          `+
    `           <h5 class="mb-1">${metadata.name}</h5>                                                                  `+
    `           <small>${metadata.owner}</small>                                                                        `+
    `       </div>                                                                                                      `+
    `       <p class="mb-1">metadata.time</p>                                                                           `+
    `   </div>                                                                                                          `;
    if (location === "PUBLIC") {
        console.log("add public")
        $("#publicList").append(listHtml);
    } else if (location === "PRIVATE") {
        console.log("add private");
        $("#privateList").append(listHtml);
    }
    addClickEffect(metadata.musicId);
}

function requestMusicData(requestInfo) {

    //console.log(JSON.stringify(requestInfo));

    $.post({
        url: "/post/search",
        data: JSON.stringify(requestInfo),
        contentType: "application/json",
        success: function (responseInfo) {
            switch (responseInfo.err) {
                case "NONE":
                    musicDataHandler(responseInfo.result, requestInfo.location);
                    break;
                case "INVALID_REQUEST":
                    myAlert("INVALID_REQUEST");
                    break;
            }
        },
        error: function(e) {
            if (e.status == 401) {
                location.assign("/login");
            }
        }
    });
}

function myAlert(message) {
    let alertHtml = 
    `   <div class="alert alert-danger alert-dismissible" role="alert">                                                 `+
    `       <div>${message}</div>                                                                                       `+
    `       <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="buttonAlertClosing"/>`+
    `   </div>                                                                                                          `;
    $("#alertPlaceholder").html(alertHtml);
}

let isUpdate = true; // update locker
let idGetAll = guid(); // search id for list showing
let publicPageNumber = 0;
let privatePageNumber = 0;
let publicEnd = false;
let privateEnd = false;


$(".my-list").on("scroll", function () {
    if (this.clientHeight + this.scrollTop > this.scrollHeight - 500 && isUpdate) {
        isUpdate = false;

        let requestInfo = {};
        if (this.id === "publicList") {
            requestInfo = {
                keywords: "",
                location: "PUBLIC",
                page: publicPageNumber,
                search_id: idGetAll,
            };
        }
        else if (this.id === "privateList") {
            requestInfo = {
                keywords: "",
                location: "PRIVATE",
                page: privatePageNumber,
                search_id: idGetAll,
            };
        }
        
        let response = requestMusicData(requestInfo);
        if (response != null && response.length != 0) {
            if (requestInfo.location === "PUBLIC") {
                publicPageNumber++;
            } else if (requestInfo.location === "PRIVATE") {
                privatePageNumber++;
            }
        } else {
            if (requestInfo.location === "PUBLIC") {
                publicEnd = true;
            } else if (requestInfo.location === "PRIVATE") {
                privateEnd = true;
            }
        }
        isUpdate = true;
    }
});


function musicDataHandler(response, location) {
    if (response == null) return;
    for (let i = 0;i < response.length;i++) {
        addMusicInfoToList(response[i], location);
    }
}


requestMusicData({
    keywords: "",
    location: "PUBLIC",
    page: publicPageNumber,
    search_id: idGetAll,
});
publicPageNumber++;

requestMusicData({
    keywords: "",
    location: "PRIVATE",
    page: privatePageNumber,
    search_id: idGetAll,
});
privatePageNumber++;

function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}