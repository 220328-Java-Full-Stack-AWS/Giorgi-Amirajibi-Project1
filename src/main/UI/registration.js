async function submitForm(){
    let data = new Object();
    data.username = document.getElementById("username").value;
    data.password = document.getElementById("password").value;
    data.firstname = document.getElementById("firstname").value;
    data.lastname = document.getElementById("lastname").value;
    data.email = document.getElementById("email").value;
    let json = JSON.stringify(data);
    let response = await fetch(
    "../registration", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        "json" : json
        },
        }
    );
    if (response.redirected){
    window.location.href = (response.url);
    }
}
$(document).ready(function(){
    $('#username').keyup(function(){
        var username = $('#username').val();
        $.ajax({
            type: 'POST',
            data: {username:username},
            url:'../UsernameValidator',
            success: function(result){
                if (result == 1){
                    //$('#result').css('border-top', '3px solid red');
                    $('#username').css('border', '3px solid red');
                    $('#username').css('border-radius', '1.5px');

                }
                else{
                    //$('#result').css('border-top', '3px solid green');
                    $('#username').css('border', '3px solid green');
                    $('#username').css('border-radius', '1.5px');

                }

            }
        });
    });
});

$(document).ready(function(){
    $('#email').keyup(function(){
        var email = $('#email').val();
        $.ajax({
            type: 'POST',
            data: {email:email},
            url:'../EmailValidator',
            success: function(result1){
                if (result1 == 1){
                    $('#email').css('border', '2.5px solid red');
                    $('#email').css('border-radius', '1.5px');

                }
                else{
                    $('#email').css('border', '2.5px solid green');
                    $('#email').css('border-radius', '1.5px');
                }

            }
        });
    });
});