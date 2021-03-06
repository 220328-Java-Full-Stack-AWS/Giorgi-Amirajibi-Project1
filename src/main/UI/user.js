async function viewReimb(){
    document.getElementById("MainContent").innerHTML =
        "<table>" +
        "<tr>" +
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
        "../reimbursement",
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
        if (json[i].reimbStatus == "PENDING"){
            let tableRow =
                '<tr id="' + "trView" + json[i].reimbId + '">' +
                "<td>" + json[i].reimbDescription + "</td>" +
                "<td>" + "$" + json[i].reimbAmount + "</td>" +
                "<td>" + json[i].reimbType + "</td>" +
                "<td>" + json[i].reimbStatus + "</td>" +
                "<td>" + json[i].reimbSubmitted.slice(0,19) + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='delete' value='Delete' onclick='reimbDelete(this.id)'>" + "</td>" +
                "<td>" + "<input type='button' id='" + json[i].reimbId + "' name='edit' value='Edit' onclick='reimbEdit(this.id)'> " + "</td>" +
                "</tr>";
            document.getElementById("content").innerHTML += tableRow;
        }
        else{
            let tableRow =
                '<tr id="' + "trView" + json[i].reimbId + '">' +
                "<td>" + json[i].reimbDescription + "</td>" +
                "<td>" + "$" + json[i].reimbAmount + "</td>" +
                "<td>" + json[i].reimbType + "</td>" +
                "<td>" + json[i].reimbStatus + "</td>" +
                "<td>" + json[i].reimbSubmitted.slice(0,19) + "</td>" +
                "</tr>";
            document.getElementById("content").innerHTML += tableRow;
        }

    }
}

async function reimbEdit(reimbId){
    console.log(reimbId);
    let jsonObj = new Object();
    jsonObj.reimbId = reimbId;
    JSON.stringify(jsonObj);
    let message = document.getElementById("trView"+reimbId).innerText.toString();
    message = message.split("\t");


    document.getElementById("MainContent").innerHTML =
        "<table>" +
        "<tr>" +
        "    <th>Description</th>" +
        "    <th>Amount</th>" +
        "    <th>Type</th>" +
        "    <th>Status</th>" +
        "    <th>Submitted</th>" +
        "</tr>" +
        "    <tbody id='content'></tbody>"
    "</table>";
    let tableRow =
        '<tr id="' + "trEdit" + reimbId + '">' +
        "<td>" + '<input class="' + "editData" + '" value="' + message[0] + '">' + "</td>" +
        "<td>" + '<input class="' + "editData" + '" value="' + message[1] + '">' + "</td>" +
        "<td>" +
        '<select class="' + "editData" + '" name="reimbType" id="reimbType">' +
        "  <option value='LODGING'>Lodging</option>" +
        "  <option value='FOOD'>Food</option>" +
        "  <option value='TRAVEL'>Travel</option>" +
        '</select>' +
        "</td>" +
        "<td>" + message[3] + "</td>" +
        "<td>" + message[4] + "</td>" +
        "<td>" + "<input type='button' id='" + reimbId + "' name='edit' value='Submit' onclick='submitEdit(this.id)'> " + "</td>" +
        "</tr>";
    document.getElementById("content").innerHTML += tableRow;


}
async function submitEdit(reimbId){
    let array = [document.getElementsByClassName("editData")[0].value,
        document.getElementsByClassName("editData")[1].value,
        document.getElementsByClassName("editData")[2].value];

    if (array[1].toString().startsWith("$")){
        array[1] = array[1].toString().substring(1,array[1].toString().length);
    }

    let data = new Object();
    data.reimbId = reimbId;
    data.reimbDescription = array[0];
    data.reimbAmount = array[1];
    data.reimbType = array[2];
    data.reimbSubmitted = new Date().toISOString().slice(0, 19).replace('T', ' ');

    let jsonObject = JSON.stringify(data);

    let response = await fetch(
        "../reimbursement",
        {
            method: "PUT",
            headers:
                {
                    "Content-Type" : "application/json",
                },
            body: jsonObject
        }
    );
    if (response.headers.get("status") == "success"){
        await viewReimb();
    }

}

async function reimbDelete(reimbId){

    let response = await fetch(
        "../reimbursement",
        {
            method: "DELETE",
            headers:
                {
                    "Content-Type": "application/json",
                    "json" : reimbId
                }
        }
    );
    if (response.headers.get("status") == "success"){
        await viewReimb();
    }
}

function reimb(){

    document.getElementById("MainContent").innerHTML=
        "<div style='text-align: center;'>" +
        "<label for='reimbType'>Reimbursement Type</label>" + "<br>" +
        "<select name='reimbType' id='reimbType'>" +
        "  <option value='LODGING'>Lodging</option>" +
        "  <option value='FOOD'>Food</option>" +
        "  <option value='TRAVEL'>Travel</option>" +
        "</select><br>" +
            "<label for='reimbAmount'>Reimbursement Amount</label>" + "<br>" +
            "<input name='number' type='number' min='0' id='reimbAmount' placeholder='Reimbursement Amount'><br>" +
            "<label for='reimbDescription'> Reimbursement Description</label>" + "<br>" +
            "<input name='description'type='text' id='reimbDescription' placeholder='Reimbursement Desc'><br>" +
        "</div>" +
        "<input type='submit' id='submit' type='submit' onclick='createReimb()'>";
}

async function createReimb(){
    let data = new Object();
    data.username = sessionStorage.getItem('username');
    data.reimbType = document.getElementById("reimbType").value;
    data.reimbAmount = document.getElementById("reimbAmount").value;
    data.reimbDescription = document.getElementById("reimbDescription").value;
    data.reimbSubmitted = new Date().toISOString().slice(0, 19).replace('T', ' ');
    let json = JSON.stringify(data);

    let response = await fetch(
        "../reimbursement",
        {
            method : "POST",
            headers :
                {
                    "Content-Type": "application/json",
                },
            body : json
        }

    );
    console.log(response);
    location.reload();

}
function logout(){
    sessionStorage.clear();
    window.location.reload();
}