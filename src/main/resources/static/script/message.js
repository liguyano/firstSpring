$(function () {
    
})
function sendMessage() {
let mes=$("#maintext").val();
$("#othermessage").append($("<p>"+mes+"</p>"));
}