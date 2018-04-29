$.get({
    url: "/api/v1/students/" + sid + "/coops",
    success: function(data) {
        getById("coops").innerHTML = "";
        console.log(data);
        $.each(data.coops, function(i, coop) {
            //console.log(item);
            let li = document.createElement("li");
            let a = document.createElement("a");
            a.style.display = "none";
            a.href = "/coops/" + coop.id;
            setEmployerName(a, coop.employer_id);
            li.append(a);
            getById("coops").append(li);
        });
    }
});

function setEmployerName(a, id) {
    var name = "Error retrieving company name."
    $.get({
        url: "/api/v1/employers/" + id,
        success: function(data) {
            //console.log(data);
            name = data[0].name;
            setInnerHTML(a,"Coop at " + data[0].name);
        }
    });

    return name;
}

function setInnerHTML(element, html) {
    element.innerHTML = html;
    element.style.display = "";
}