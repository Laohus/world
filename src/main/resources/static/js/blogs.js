$(document).ready(function() {

    $("#homeback").click(function() {

        $(location).prop("href","/Welcome")
        return true;
    });
    $("#article").click(function() {

        $(location).prop("href","/Blog")
        return true;
    });

    $("#logout").click(function() {

        $.ajax({
            url:"/LoginOut",
            type:"POST",
            datatype:"JSON",
            success:function (data) {
                $(location).prop("href","/login")
                return true;

            }
        })

    });

    $.ajax({
        url:"/blog/timeline",
        type:"POST",
        datatype:"JSON",
        success:function (data) {
            if(data.code==="0"){
                const line = data.data;
                if(line.length!==0){
                    let n = line.length;
                    for(let i =0;i<n;i++){
                        AddLine(line[i].timeline,line[i].name);
                    }
                }
                return true;
            }else {
                return false;

            }

        }
    })



});

function AddLine(timeline,name){

    let lineName = AddName(name);

    $("#AddLine").append('        <li class="layui-timeline-item">\n' +
        '            <i class="layui-icon layui-timeline-axis"></i>\n' +
        '            <div class="layui-timeline-content layui-text">\n' +
        '                <h3 class="layui-timeline-title">'+timeline+'</h3>\n' +
        '                '+lineName+'' +
        '            </div>\n' +
        '        </li>');

    return true;
}
function AddName(name){
    let lineName = ""
    let m = name.length;
    for(let i =0;i<m;i++){
        let lineNames = "<p>"+name[i]+"</p>";

        lineName = lineNames + lineName;

    }
    return lineName;

}