// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
    console.log('hi from register')
});

async function login(){
    let data = {};

    data.email = document.getElementById('txtEmail').value;
    data.password = document.getElementById('txtPassword').value;

    const request = await fetch('api/login',{
    method:'POST',
    headers:{
   'Accept': 'application/json, text/plain, */*',
    'Content-Type': 'application/json'
    },
    //llama ala func para que agarre el elemento y lo convierta a string de json
     body: JSON.stringify(data)
    });

    const response = await request.text();
    if(response != 'fail'){
        localStorage.token = response;
        localStorage.email = data.email;
        window.location.href = 'users.html'
    }else{
        alert("check email or password are wrong , try again");
    }
}
