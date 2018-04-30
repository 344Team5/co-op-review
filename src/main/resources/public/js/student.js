updateStudentInfo();

function updateStudentInfo() {
    $.get({
        url: "/api/v1/students/" + sid + "/coops",
        success: function (data) {
            getById("coops").innerHTML = "";
            console.log(data);
            try {
                $.each(data.coops, function(i, coop) {
                    //console.log(coop);
                    let li = document.createElement("li");
                    let a = document.createElement("a");
                    let span = document.createElement("span");
                    span.id
                    setEmployerName(getById("employerName"), coop.employer_id);
                    a.href = "/coops/" + coop.id;
                    a.innerHTML = "Co-op at " ;
                    a.append(span);
                    li.append(a);
                    getById("coops").append(li);
                });
            } catch (err) {}
        }
    });
}

function submitForm() {
    let data = {};
    data.start_date = getById("startDate").value;
    data.end_date = getById("startDate").value;
    data.employer_id = 1;
    data.student_uid = sid;
    $.post({
        url: "/api/v1/coops",
        data: JSON.stringify(data),
        success: function(data) {
            //console.log(data);
            if (data === "success") {
                getById("status").innerHTML = "Co-op submitted successfully."
                getById("status").style.color = "green";
            } else {
                getById("status").innerHTML = "Something went wrong."
                getById("status").style.color = "red";
            }
        }
    });
}

function setStatusMessage(msg) {
    getById("status").innerHTML = msg;
}