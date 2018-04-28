$(document).ready(function() {
    updateEmployerList();
});

function update() {
    updateEmployerList();
}

function updateEmployerList() {
    $.get({
        url: "/api/v1/employers",
        success: function(data) {
            getById("employers").innerHTML = "";
            console.log(data);
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

