$(function () {
    let submit=$("input[type='button']");

    let usena=$("input[name='username']")
    let i2=true;    let i=true;
    let password1 = $("input[name='password']")
    let repassword1=$("input[name='repassword']")
    //let the submit button disabled
    submit.attr('disabled',true);
    //clear the line
    usena.focus(function () {
        if (i2===true){usena.val("");i2=false;}
    })
    password1.focus(function () {
        if (i===true){password1.val("");repassword1.val("");i=false;}
    })
    password1.focus(function () {
        if (i===true){password1.val("");repassword1.val("");i=false;}
    })
    //check if the password equal the repassword
    repassword1.blur(function () {
        let password=password1.val()
        let repassword=repassword1.val()
        if (password===repassword){
            $("input[type='button']").attr('disabled',false);
        }
    })
    //check if the username had been registed
    usena.blur(function () {
        $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"checkp",$("form[method='post']").serialize(),function (data) {
            if(data!==-1){alert("該用戶名已被註冊")}
        })
    })
    //submit the register form
    submit.click(function () {
        let password=password1.val()
        let repassword=repassword1.val()
        if (password===repassword && usena.val()!==""&& password!==""){register();}
        else if(usena.val()===""){alert("用戶名不能為空");return false;}
        else if(password===""){alert("密碼不能為空");return  false}
        else {alert("密碼前後不一致");return false;}
    })
})
function register() {
    $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"text3",$("form[method='post']").serialize(),function (data) {
        alert(data);
    })

}