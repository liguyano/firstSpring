let files;
let pathNow=0
let pathstr=""
let pathStrs=[pathstr]
let paths=[pathNow]
let fil
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
    $.post(window.location.href.replace(/\/[^\/]+$/, '/')+"add-dir","dir-name="+$("#dir-name").val()+'&pre='+pathNow+
        "&path="+toS(pathStrs),function (dat) {
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
        $("#file_table").append(" <tr>\n" +
            "            <td>\n" +
            "                <a href=\"javascript:void(0)\" onclick=\"dirback()\">../</a>\n" +
            "            </td>\n" +
            "        </tr>")
    }
    $.get(window.location.href.replace(/\/[^\/]+$/, '/')+'dir','pre='+pathNow ,function (dat) {
    fil=$.parseJSON(dat)
        for (let i = 0; i < fil.length; i++) {
            $("#file_table").append('<tr> <td> <a href="javascript:void(0)" onclick="into_dir('+fil[i].id+',\''+fil[i].name+'\')">'+fil[i].name+'</a></td> </tr>')

        }$.get(window.location.href.replace(/\/[^\/]+$/, '/')+"get-file","dir="+pathNow,function (dat,sta) {
            files=$.parseJSON(dat);
            for (let i=0;i<files.length;i++)
            {
                let tempName=PathBeforeLastSlash(files[i].FILENAME)
                files[i].FILENAME=extractLastPathComponent(files[i].FILENAME);
                let fileTable=$("#file_table");
                fileTable.append("<tr >" +"<td class='filePart' ><a href=\"outFile/"+tempName+files[i].id+"\" download='" +files[i].FILENAME+
                    "'>" +files[i].FILENAME+
                    "</a></td>"+"<td>"+files[i].UPLOADTIME+"</td>"+
                    "<td>"+files[i].size+"</td>"+
                    "</tr>");
            }

        }).fail()
    })
}
function into_dir(dir,name) {
    pathNow=dir
    pathstr=name
    pathStrs.push(name)
    paths[paths.length]=dir
    $("#dir").val(pathNow);
    $("#path").val(toS(pathStrs))
    get_dir()
}
$(function () {

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