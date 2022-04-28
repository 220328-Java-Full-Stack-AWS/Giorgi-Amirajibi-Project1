async function viewAllUsers(){
    let username = sessionStorage.getItem("username");
    document.getElementById("MainContent").innerHTML =
        "<table>" +
        "<tr>" +
        "    <th>User Id</th>" +
        "    <th>Username</th>" +
        "    <th>First Name</th>" +
        "    <th>Last Name</th>" +
        "    <th>Email</th>" +
        "    <th>Privileges</th>" +
        "</tr>" +
        "    <tbody id='content'></tbody>"
        "</table>";

    let response = await fetch(
        "../selectUsers",
        {
            method: "GET",
            headers:
            {
                "Content-Type" : "application/json",
            }
        }
    );
    let json = JSON.parse(response.headers.get("json"));
    for (let i = 0; i < json.length; i++) {
        let tableRow =
            '<tr id="' + json[i].ers_user_id + '">' +
            "<td>" + json[i].ers_user_id + "</td>" +
            "<td>" + json[i].username + "</td>" +
            "<td>" + json[i].firstname + "</td>" +
            "<td>" + json[i].lastname + "</td>" +
            "<td>" + json[i].email + "</td>" +
            "<td>" + json[i].userRoleAsString + "</td>" +
            "<td>" + "<input type='button' id='" + json[i].ers_user_id + "' name='edit' value='Edit' onclick='userEdit(this.id)'> " + "</td>" +
            "</tr>";
        document.getElementById("content").innerHTML += tableRow;
    }

}
async function userEdit(ersUserId){
    let data = document.getElementById(ersUserId).innerText.toString();
    data = data.split("\t");
    document.getElementById("MainContent").innerHTML =
        "<table>" +
        "<tr>" +
        "    <th>User Id</th>" +
        "    <th>Username</th>" +
        "    <th>First Name</th>" +
        "    <th>Last Name</th>" +
        "    <th>Email</th>" +
        "    <th>Privileges</th>" +
        "</tr>" +
        "    <tbody id='content'></tbody>"
    "</table>";
    let tableRow =
        '<tr id="' + ersUserId + '">' +
        "<td>" + data[0] + "</td>" +
        "<td>" + data[1] + "</td>" +
        "<td>" + data[2] + "</td>" +
        "<td>" + data[3] + "</td>" +
        "<td>" + data[4] + "</td>" +
        "<td>" +
            '<select class="' + "editData" + '" name="userRole" id="userRole">' +
                "<option value='Employee'>Employee</option>" +
                "<option value='Financial Manager'>Financial Manager</option>" +
                "<option value='Admin'>Admin</option>" +
            '</select>' +
        "</td>" +
        "<td>" + "<input type='button' id='" + ersUserId + "' name='submit' value='Submit' onclick='submitUserEdit(this.id)'> " + "</td>" +
        "</tr>";
    document.getElementById("content").innerHTML += tableRow;
}
async function submitUserEdit(ersUserId){
    let data = new Object();
    data.userId = ersUserId;
    data.userRole = document.getElementsByClassName("editData")[0].value;
    data = JSON.stringify(data);
    console.log(data);
    let response = await fetch(
        "../selectUsers",
        {
            method: "PUT",
            headers:{
                "Content-Type": "application/json"
            },
            body: data
        }
    );
    if (response.status == 200){
        viewAllUsers();
    }

}

async function viewReimb(){
    document.getElementById("MainContent").innerHTML =
        "<table>" +
        "<tr>" +
        "    <th>Author</th>" +
        "    <th>Description</th>" +
        "    <th>Amount</th>" +
        "    <th>Type</th>" +
        "    <th>Status</th>" +
        "    <th>Submitted</th>" +
        "</tr>" +
        "    <tbody id='content'></tbody>"
        "</table>";

    let username = sessionStorage.getItem("username");
    let response = await fetch(
        "../reimbursementAdmin",
        {
            method: "GET",
            headers:
                {
                    "Content-Type" : "application/json",
                    "username" : username
                }
        }
    );

    let json = JSON.parse(response.headers.get("json"));
    for (let i = 0; i < json.length; i++) {
        if (json[i].reimbAuthorUserName != username){
            let tableRow =
                '<tr id="' + json[i].reimbId + '">' +
                "<td>" + json[i].reimbAuthorUserName + "</td>" +
                "<td>" + json[i].reimbDescription + "</td>" +
                "<td>" + json[i].reimbAmount + "$" + "</td>" +
                "<td>" + json[i].reimbType + "</td>" +
                "<td>" + json[i].reimbStatus + "</td>" +
                "<td>" + json[i].reimbSubmitted.slice(0,19) + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='delete' value='Delete' onclick='reimbDelete(this.id)'>" + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='edit' value='Edit' onclick='reimbEdit(this.id)'> " + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='approved' value='Approve' onclick='reimbApproved(this.id)'>" + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='denied' value='Deny' onclick='reimbDenied(this.id)'> " + "</td>" +
                "</tr>";
            document.getElementById("content").innerHTML += tableRow;
        }
        else{
            let tableRow =
                '<tr id="' + json[i].reimbId + '">' +
                "<td>" + json[i].reimbAuthorUserName + "</td>" +
                "<td>" + json[i].reimbDescription + "</td>" +
                "<td>" + json[i].reimbAmount + "$" + "</td>" +
                "<td>" + json[i].reimbType + "</td>" +
                "<td>" + json[i].reimbStatus + "</td>" +
                "<td>" + json[i].reimbSubmitted.slice(0,19) + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='delete' value='Delete' onclick='reimbDelete(this.id)'>" + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='edit' value='Edit' onclick='reimbEdit(this.id)'> " + "</td>" +
                "</tr>";
            document.getElementById("content").innerHTML += tableRow;
        }

    }


}
async function reimbApproved(reimbId){
    console.log(reimbId);
    let jsonObject = new Object();
    jsonObject.reimbId = reimbId;
    jsonObject.reimbResolver = sessionStorage.getItem("username");
    jsonObject.reimbResolved = new Date().toISOString().slice(0,19).replace('T', ' ');
    jsonObject.reimbStatusId = 3;
    let data = JSON.stringify(jsonObject);
    console.log(jsonObject)

    let response = await fetch(
        "../reimbursementAdmin",
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: data
        }
    );
    if (response.headers.get("status") == "success"){
        viewReimb();
    }

}
async function reimbDenied(reimbId){
    console.log(reimbId);
    let jsonObject = new Object();
    jsonObject.reimbId = reimbId;
    jsonObject.reimbResolver = sessionStorage.getItem("username");
    jsonObject.reimbResolved = new Date().toISOString().slice(0,19).replace('T', ' ');
    jsonObject.reimbStatusId = 2;
    let data = JSON.stringify(jsonObject);
    console.log(jsonObject)

    let response = await fetch(
        "../reimbursementAdmin",
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: data
        }
    );
    if (response.headers.get("status") == "success"){
        viewReimb();
    }

}
function logout(){
    sessionStorage.clear();
    window.location.reload();
}