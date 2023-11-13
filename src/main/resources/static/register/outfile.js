let files;
let pathNow=0
let pathstr=""
let pathStrs=[pathstr]
let paths=[pathNow]
function PathBeforeLastSlash(url) {
    // 使用正则表达式匹配最后一个斜杠之前的内容
    var pattern = /^(.*\/)/;
    var matches = url.match(pattern);

    if (matches && matches.length > 1) {
        // 返回匹配到的内容
        return matches[1];
    } else {
        // 如果没有匹配到内容，则返回空字符串或者其他适当的默认值
        return '';
    }
}

function extractLastPathComponent(url) {
    // 使用正则表达式匹配最后一个斜杠后面的内容
    var pattern = /\/([^/]+)\/?$/;
    var matches = url.match(pattern);

    if (matches && matches.length > 1) {
        // 返回匹配到的内容
        return matches[1];
    } else {
        // 如果没有匹配到内容，则返回空字符串或者其他适当的默认值
        return url;
    }
}

function add_dir() {
    let fileMess = ["succed", "faild", "already existed"]
    if ($("#dir-name").val() === "")
    {
        return;
    }
    $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"add-dir","dir-name="+$("#dir-name").val()+'&pre='+pathNow,function (dat) {
        alert(fileMess[dat-1])
        if (pathNow>0)
        {
            let p=pathNow;
            let ps=pathstr;
            paths.pop()
            pathStrs.pop()
            pathstr=pathStrs[pathStrs.length-1]
            pathNow=paths[paths.length-1]
            into_dir(p,ps);
        }
        else
        {
            location.reload();
        }
    }).fail(function () {
        alert("failed")
    })
}
function dirback() {
    paths.pop()
    pathStrs.pop()
    pathstr=pathStrs[pathStrs.length-1]
    pathNow=paths[paths.length-1]
    $("#dir").val(pathNow);
    $("#path").val(toS(pathStrs))
    get_dir()
}
function toS(dat) {
    let a=""
    for (let i = 1; i < dat.length; i++) {
        a+=dat[i]+"/"
    }
    console.log(a)
    return a
}
function delFile(fileName){
    let rootPass=prompt("please input the root password ")
    $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"del-file","id="+fileName+"&password="+rootPass,
        function (dat) {
        alert(dat)
        }
    )
}
function deleteDir(dirid) {
    let rootPass=prompt("please input the root password ")
    $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"del-dir","dirId="+dirid+"&password="+rootPass,
        function (dat) {
            alert(dat);
        }
    ).fail(function () {
        alert("fail");
    })
}
function downloadAll(dirId) {
    $.get(window.location.href.replace(/\/[^\/]+$/, '/')+"get-file","dir="+dirId,function (dat,sta) {
        files=$.parseJSON(dat);
        let fileP=$("#file-temp-area");
        for (let i=0;i<files.length;i++)
        {
            fileP.append("<a class='all-file' style='visibility: hidden' href=\"outFile/"+files[i].id+"\" download='" +files[i].FILENAME+
        "'>" +files[i].FILENAME+
        "</a>"
            );
        }
        let allF=document.getElementsByClassName("all-file");
        for (const allFElement of allF) {
            allFElement.click();
        }
        fileP.empty()

    })
}
function get_dir() {
    $("#file_table").html("  <tr>\n" +
        "            <td >\n" +
        "                <input type=\"button\" id=\"add-dir\" value=\"addDir\" onclick='add_dir()' />\n" +
        "            </td>\n" +
        "            <td>\n" +
        "                <input type=\"text\" id=\"dir-name\">\n" +
        "            </td>\n" +
        "        </tr>\n" +
        "      <tr>\n" +
        "<td>\n" +
        "  file name\n" +
        "</td>\n" +
        "        <td>\n" +
        "          upload\n" +
        "        </td>\n" +
        "        <td>\n" +
        "          size\n" +
        "        </td>\n" +
        "      </tr>")
    if (pathNow != 0){
        $("#file_table").append(" " +
            "<tr><td> now in:/" +pathStrs[0]+"/"+toS(pathStrs)+
            "</td> <td> <input type='button' value='download all' onclick='downloadAll(" +pathNow+
            ")'> " +
            "</td></tr>" +
            "<tr>" +
            "            <td>" +
            "                <a href=\"javascript:void(0)\" onclick=\"dirback()\">../</a>" +
            "            </td>" +

            "        </tr>")
    }
    $.get(window.location.href.replace(/\/[^\/]+$/, '/')+'dir','pre='+pathNow ,function (dat) {
    fil=$.parseJSON(dat)
        for (let i = 0; i < fil.length; i++) {
            $("#file_table").append('<tr> <td> <a href="javascript:void(0)" onclick="into_dir('+fil[i].id+',\''+fil[i].name+'\')">'+fil[i].name+'</a></td> '+
                "<td>"+"<input type='button' class='del-dir-btn' value='delete' onclick=\'deleteDir("+fil[i].id
            +")\'>"+"</td>"+"<td>" +"<input type='button' value='download all' onclick='downloadAll("+fil[i].id+")'> "+
                "</td>" +"</tr>")
        }$.get(window.location.href.replace(/\/[^\/]+$/, '/')+"get-file","dir="+pathNow,function (dat,sta) {
            files=$.parseJSON(dat);
            for (let i=0;i<files.length;i++)
            {

                files[i].FILENAME=extractLastPathComponent(files[i].FILENAME);
                let fileTable=$("#file_table");
                fileTable.append("<tr >" +"<td class='filePart' ><a href=\"outFile/"+files[i].id+"\" download='" +files[i].FILENAME+
                    "'>" +files[i].FILENAME+
                    "</a></td>"+"<td>"+files[i].UPLOADTIME+"</td>"+
                    "<td>"+files[i].size+"</td>"+
                    "<td>"+"<input type='button' value='delete' class='del-btn' onclick='delFile("+files[i].id+")'>"+"</td>"+
                    "</tr>");
            }

        }).fail()
    })
    document.cookie="paths="+paths.toString();
    document.cookie="pathStrs="+pathStrs.toString();
}
function into_dir(dir ,name) {
    pathNow=dir
    pathstr=name
    pathStrs.push(name)
    paths[paths.length]=dir
    $("#dir").val(pathNow);
    $("#path").val(toS(pathStrs))
    get_dir()
}
$(function () {
    var cookies = document.cookie.split('; ');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].split('=');
        console.log(cookie[0])
        if (cookie[0] === "paths") {
            var cookieValue = cookie[1];
            paths=cookieValue.split(",")
            pathNow=paths[paths.length-1]
            console.log(paths)
            // Do something with the cookie value
        }
        if (cookie[0] === "pathStrs") {
            var cookieValue = cookie[1];
            pathStrs=cookieValue.split(",")
            pathstr=pathStrs[paths.length-1]
            console.log(pathstr)
            // Do something with the cookie value
        }
    }
    $("#dir").val(pathNow);
    $("#path").val(toS(pathStrs))
    let str = window.location.href;
    let result = str.replace(/\/[^\/]+$/, '/');
    if (document.cookie.includes("user"))
    {

    }else
    {
        alert("請先登錄")
        window.location.href =result+"index.html";
    }
    get_dir()
    let a=$("#001");
    $("#add-dir").click(function () {
        add_dir();
    })
    a.href=window.location.href.replace(/\/[^\/]+$/, '/')+"file.html";
    a.text("如有亂碼請點擊此處刷新")
    $("#upload_form").action=window.location.href.replace(/\/[^\/]+$/, '/')+"uploadfile"
    $("#upload_form").attr("action",window.location.href.replace(/\/[^\/]+$/, '/')+"uploadfile")


})