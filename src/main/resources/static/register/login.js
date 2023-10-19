
$(function () {
    document.cookie;    let str = window.location.href;
    let result = str.replace(/\/[^\/]+$/, '/');
    if (document.cookie.includes("user"))
    {
        window.location.href =result+"file.html"
    }
    $("#a_register").attr("href",result+"register.html");
    $("#a_file").attr("href",result+"file.html");
    $("input[type='button']").click(function (){
        $.post(result+'checkp',$("#loginform").serialize(),function (data) {

            if (data>0){
                $("#gobyh").show();
                alert("密码正确,如未跳轉請刷新，還不行清一下緩存");
                top.location=result+"file.html"
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