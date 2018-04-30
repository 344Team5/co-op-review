$(document).ready(function() {
    update();
});

function update() {
    updateEmployerList();
    updateStudentList();
    updateCoopList();
}

function updateEmployerList() {
    $.get({
        url: "/api/v1/employers",
        success: function(data) {
            getById("employers").innerHTML = "";
            //console.log(data);
            $.each(data, function(i, emp) {
                console.log(emp);
                let li = document.createElement("li");
                let a = document.createElement("a");
                a.href = "/employers/" + emp.id;
                a.innerHTML = emp.name;
                li.appendChild(a);
                getById("employers").appendChild(li);
            });
        }
    });
}

function updateStudentList() {
    $.get({
        url: "/api/v1/students",
        success: function(data) {
            getById("students").innerHTML = "";
            //console.log(data);
            $.each(data, function(i, stu) {
                console.log(stu);
                let li = document.createElement("li");
                let a = document.createElement("a");
                a.href = "/students/" + stu.uid;
                a.innerHTML = stu.name;
                li.appendChild(a);
                getById("students").appendChild(li);
            });
        }
    });
}

function updateCoopList() {
    $.get({
        url: "/api/v1/coops",
        success: function(data) {
            getById("coops").innerHTML = "";
            //console.log(data);
            $.each(data, function(i, coop) {
                console.log(coop);
                let li = document.createElement("li");
                let a = document.createElement("a");
                a.href = "/coops/" + coop.id;
                a.innerHTML = "Student " + coop.student_uid + " at Employer: " + coop.employer_id;
                li.appendChild(a);
                getById("coops").appendChild(li);
            });
        }
    });
}

