updateStudentInfo();
updateEmployerList();

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
                    setEmployerName(span, coop.employer_id);
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

function updateEmployerList() {
    $.get({
        url: "/api/v1/employers",
        success: function(data) {
            getById("employer").innerHTML = "";
            //console.log(data);
            $.each(data, function(i, emp) {
                //console.log(emp);
                let opt = document.createElement("option");
                opt.value = emp.id;
                opt.innerHTML = emp.name;
                getById("employer").append(opt);
            });
        }
    });
}

function submitForm() {
    let data = {};
    data.start_date = getById("startDate").value;
    data.end_date = getById("startDate").value;
    data.employer_id = getById("employer").value;
    data.student_uid = sid;
    console.log(data);
    $.post({
        url: "/api/v1/coops",
        data: JSON.stringify(data),
        success: function(data) {
            //console.log(data);
            if (data === "success") {
                getById("status").innerHTML = "Co-op submitted successfully."
                getById("status").style.color = "green";
                updateStudentInfo();
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