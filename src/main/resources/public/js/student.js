updateStudentInfo();

function updateStudentInfo() {
    $.get({
        url: "/api/v1/students/" + sid + "/coops",
        success: function (data) {
            getById("coops").innerHTML = "";
            console.log(data);
            try {
                let studentUid = data.student[0].uid;


            } catch (err) {}
        }
    });
}

function setStatusMessage(msg) {
    getById("status").innerHTML = msg;
}