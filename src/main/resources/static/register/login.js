let str = 'http://www.example.com/path/to/file.txt';
let result = str.replace(/\/[^\/]+$/, '/');
$(function () {
    $("input[type='button']").click(function (){
        $.post(window.location.href.replace(/\/[^\/]+$/, '/')+'checkp',$("#loginform").serialize(),function (data) {

            if (data>0){
                $("#gobyh").show();
                alert("密码正确");
                top.location="filefile.html"
            }else if (data===-2)
            {
                alert("密码错误");
            }
            else if (data===-1)
            {
                alert("账号不存在");
            }
        })
    })
})