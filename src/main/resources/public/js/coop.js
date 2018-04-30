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