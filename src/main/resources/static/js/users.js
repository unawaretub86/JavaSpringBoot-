// Call the dataTables jQuery plugin
$(document).ready(function() {
    loadUsers()
  $('#users').DataTable();
});


async function loadUsers(){
    const request = await fetch('api/users',{
    method:'GET',
    headers: getHeaders(),
    //body:JSON.stringify({a:1, b:'Textual content'})
    });
    const users = await request.json();

    console.log(users);

    let listHtml = '';

    for (let user of users){
    let telephonetxt = user.telephone == null ? '-': user.telephone;
    let buttonDelete = '<a href="#" onClick="deleteUser( '+user.id+' )" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let userHtml =
    '<tr><td>' + user.id + '</td><td>' + user.name + ' '+user.lastName + '</td><td>' + user.email + '</td><td>' + telephonetxt + '</td><td> ' +buttonDelete+ ' </td><td></td></tr>'

    listHtml += userHtml;
    }
    document.querySelector('#users tbody').outerHTML = listHtml;


}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}


async function deleteUser(id){

    if(!confirm('Do you want delete this user?')){
        return;
    }

const request = await fetch('api/users/' + id,{

    method:'DELETE',
    headers: getHeaders(),
    //body:JSON.stringify({a:1, b:'Textual content'})
    });

    location.reload()
}
