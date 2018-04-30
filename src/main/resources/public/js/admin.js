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
                //console.log(emp);
                let li = document.createElement("li");
                let a = document.createElement("a");
                a.href = "/employers/" + emp.id;
                a.innerHTML = emp.name;
                li.append(a);
                getById("employers").append(li);
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
                //console.log(stu);
                let li = document.createElement("li");
                let s = document.createElement("span");
                let b = document.createElement("button");
                s.style.color = "#e15a0c";
                s.innerHTML = stu.name;
                s.style.marginRight = "20px";
                b.href = "#";
                b.classList.add("alert");
                b.classList.add("hollow");
                b.classList.add("button");
                b.classList.add("tiny")
                b.addEventListener("click", function(ev){removeStudent( stu.uid )});
                b.innerHTML = "Delete";

                li.append(s);
                li.append(b);
                getById("students").append(li);
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
                //console.log(coop);
                let li = document.createElement("li");
                let a = document.createElement("a");
                a.href = "/coops/" + coop.id;
                a.innerHTML = "Student " + coop.student_uid + " at Employer: " + coop.employer_id;
                li.append(a);
                getById("coops").append(li);
            });
        }
    });
}

function removeStudent(uid) {
    if (confirm("Are you sure?  This will permanently delete the student's data, including their coops.")) {
        $.ajax({
            type: "DELETE",
            url: "api/v1/students/" + uid,
            data: JSON.stringify(uid),
            success: function (data) {
                updateStudentList();
            },
        })
    }
}

