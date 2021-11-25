// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
    console.log('hi from register')
});

async function registerUsers(){
    let data = {};

    data.name = document.getElementById('txtName').value;
    data.lastName = document.getElementById('txtLastName').value;
    data.email = document.getElementById('txtEmail').value;
    data.password = document.getElementById('txtPassword').value;

    let passwordRepeat = document.getElementById('txtRepeatPassword').value;

    if(passwordRepeat != data.password){
        alert('Your password is different check it');
        return; //para que corte la func
    }

    const request = await fetch('api/users',{
    method:'POST',
    headers:{
   'Accept': 'application/json, text/plain, */*',
    'Content-Type': 'application/json'
    },
    //llama ala func para que agarre el elemento y lo convierta a string de json
     body: JSON.stringify(data)
    });
    alert("your account was created :) ")
    window.location.href = "login.html"
}
