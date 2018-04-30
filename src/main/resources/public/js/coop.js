updateCoopInfo()

function updateCoopInfo() {
    $.get({
        url: "/api/v1/coops/" + cid,
        success: function(data) {

            //console.log(data);
            let coop = data[0];

            setEmployerName(getById("employerName"), coop.employer_id);
            getById("startDate").innerHTML = coop.start_date;
            getById("endDate").innerHTML = coop.start_date;
            getById("evalLink").href = cid + "/eval/" + coop.eval_token;

            if (coop.student_eval) {
                getById("eval").innerHTML = coop.student_eval;
            }

            if (coop.work_report) {
                getById("workReportDiv").innerHTML = coop.work_report;
            }

            setStudentName(getById("studentName"),coop.student_uid);
        }
    });
}

function submitForm() {
    let data = {};
    data.work_report = getById("report").value;
    //console.log(data);
    $.ajax({
        url: "/api/v1/coops/" + cid,
        method: "PATCH",
        data: JSON.stringify(data),
        success: function(data) {
            //console.log(data);
            if (data === "success") {
                getById("status").innerHTML = "Work report submitted successfully.";
                getById("status").style.color = "green";
                updateCoopInfo();
            } else {
                getById("status").innerHTML = "Something went wrong."
                getById("status").style.color = "red";
            }
        }
    });
}