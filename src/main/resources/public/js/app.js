$(document).foundation()

function getById(elemId) {
    return $('#'+elemId)[0];
}

function setEmployerName(element, id) {
    var name = "Error retrieving company name."
    $.get({
        url: "/api/v1/employers/" + id,
        success: function(data) {
            //console.log(data);
            name = data[0].name;
            setInnerHTML(element, name);
        }
    });

    return name;
}

function setStudentName(element, uid) {
    var name = "Error retrieving student name."
    $.get({
        url: "/api/v1/students/" + uid,
        success: function(data) {
            //console.log(data);
            name = data[0].name;
            setInnerHTML(element, name);
        }
    });

    return name;
}

function setInnerHTML(element, html) {
    element.innerHTML = html;
}