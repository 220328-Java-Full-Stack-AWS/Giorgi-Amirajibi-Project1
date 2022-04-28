async function submitForm(){
    let object = new Object();
    object.username = document.getElementById("username").value;
    object.password = document.getElementById("password").value;
    let json = JSON.stringify(object);
    let response = await fetch(
        "../login",
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "json" : json
            },
        }
    );
    if(response.redirected){
        sessionStorage.setItem('status','loggedIn');
        sessionStorage.setItem('username', document.getElementById('username').value);
        window.location.href = (response.url);
    }
    else {
        alert(response.headers.get("error"));
        location.reload();
    }

}