

$(".my-list-item").on("click", function () {
    $(".my-list-item").removeClass("active");
    $(this).addClass("active");
    console.log(this);
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
    $(`#buttonClose${uploadCount}`).on("click", function () {
        $(this).parent().remove();
    });
    $(`#inputMediaFile${uploadCount}`).on("change", function () {
        if (!this.files.length) return;
        let index = parseInt(this.id.slice(14));
        let reader = new FileReader();
        reader.onload = function () {
            fileList[index] = this.result;
        }
        reader.readAsDataURL(this.files[0]);
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
    fileList.push();
    lyricList.push();
    uploadCount++;
});



$("#buttonUpload").on("click", function () {
    for (let i = 0;i < uploadCount;i++) {
        if ($(`#uploadTask${i}`) == null) continue;
        let songAccess = "";

        if ($(`inputAccessibility${i}`).prop("checked")) {
            songAccess = "PRIVATE";
        } else {
            songAccess = "PUBLIC";
        }
        const uploadRequest = {
            name: $(`#inputName${i}`).val(),
            lyric: lyricList[i],
            accessibility: songAccess,
            file: fileList[i]
        }
        if (!validUploadRequest(uploadRequest)) {
            myAlert("INVALID_UPLOAD_REQUEST");
            return;
        }
        $.post("/post/upload", JSON.stringify(uploadRequest), function(data) {
            let response = JSON.parse(data);
            if (response.err === "NONE") {
                myAlert("UPLOAD_SUCCESS");
            } else {
                myAlert(response.err);
            }
        });
    }
});

function validUploadRequest(request) {
    return  notEmptyString(request.name) && notEmptyString(request.lyric) && notEmptyString(request.file) && (request.accessibility === "PRIVATE" || request.accessibility === "PUBLIC");
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
        $.post("/post/download", JSON.stringify(req), function (data) {
            let response = JSON.parse(data);
            if (response.err === "NONE") {
                $("#audioPlayer")[0].src = response.url;
            } else {
                myAlert(response.err);
            }
        });
    });
}

function addMusicInfoToList(metadata, location) {
    let listHtml = 
    `   <div class="list-group-item list-group-item-action my-list-item" aria-current="true" id="${metadata.music_id}"> `+
    `       <div class="d-flex w-100 justify-content-between">                                                          `+
    `           <h5 class="mb-1">${metadata.name}</h5>                                                                  `+
    `           <small>${metadata.owner}</small>                                                                        `+
    `       </div>                                                                                                      `+
    `       <p class="mb-1">metadata.time</p>                                                                           `+
    `   </div>                                                                                                          `;
    if (location === "PUBLIC") $("#publicList").append(listHtml);
    else if (location === "PRIVATE") $("#privateList").append(listHtml);
    addClickEffect(metadata.music_id);
}


function requestMusicData(requestInfo) {
    $.post("/post/search", JSON.stringify(requestInfo), function (data, textStatus) {
        let responseInfo = JSON.parse(data);
        switch (responseInfo.err) {
            case "NONE":
                return responseInfo.result;
            case "INVALID_REQUEST":
                myAlert("INVALID_REQUEST");
                return null;
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
        else if (this.id === "privateList"){
            requestInfo = {
                keywords: "",
                location: "PRIVATE",
                page: privatePageNumber,
                search_id: idGetAll,
            };
        }
        
        let response = requestMusicData(requestInfo);
        if (response != null) {
            response.forEach(element => {
                addMusicInfoToList(element, requestInfo.location);
            });
            if (requestInfo.location === "PUBLIC") {
                publicPageNumber++;
            } else if (requestInfo.location === "PRIVATE") {
                privatePageNumber++;
            }
        }

        isUpdate = true;
    }
});


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