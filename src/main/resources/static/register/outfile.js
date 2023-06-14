let files;
$(function () {
    let a=$("#001");

    a.text("如有亂碼請點擊此處刷新")
    $.get("/firstweb/get-file","",function (dat,sta) {
        files=$.parseJSON(dat);
        for (let i=0;i<files.length;i++)
        {
            let fileTable=$("#file_table");
            fileTable.append("<tr >" +"<td class='filePart' ><a href=\"outFile/"+files[i].id+"\" download='" +files[i].FILENAME+
                "'>" +files[i].FILENAME+
                "</a></td>"+"<td>"+files[i].UPLOADTIME+"</td>"+
                "<td>"+files[i].size+"</td>"+
                "</tr>");
        }

    }).fail()
})