$.get({
    url: "/api/v1/students/" + sid + "/coops",
    success: function(data) {
        getById("coops").innerHTML = "";
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