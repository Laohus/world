$(document).ready(function() {

    $("#homeback").click(function() {

        $(location).prop("href","/Welcome")
        return true;
    });
    $("#article").click(function() {

        $(location).prop("href","/Blog")
        return true;
    });


    $("#NovelSearch").click(function() {

        $.ajax({
            url: "/Book/Name",
            type: "POST",
            datatype: "JSON",
            data: "BookName=" + $(".search_name").val(),
            success: function (data) {
                if (data.code === "0") {
                    layer.msg("已查询查到小说文本，正在加载目录中...");

                    $.ajax({
                        url:"/Book/Novel",
                        type:"POST",
                        datatype:"JSON",
                        data: "NovelDirectory=" + data.data,
                        success:function (data) {
                            if (data.code === "0") {
                                layer.msg("加载目录成功！");
                                const listNovel = AddNovel(data.data);
                                layui.use(['tree', 'util'], function(){
                                    const tree = layui.tree
                                        , layer = layui.layer
                                        , util = layui.util
                                        ,data1 = listNovel
                                    tree.render({
                                        elem: '#NovelDirectory'
                                        ,data: data1
                                        ,click: function (obj){
                                            $.ajax({
                                                url:"/Book/Content",
                                                type:"POST",
                                                datatype:"JSON",
                                                data: "BookNovel=" + obj.data.title+"&BookName="+ $(".search_name").val(),
                                                success:function (data) {
                                                    if (data.code === "0") {
                                                        layer.msg("加载内容成功！");
                                                        $("#Novel_content").empty();
                                                        setTimeout(function (){
                                                            $("#Novel_content").append(data.data);
                                                        },1000)
                                                        return true;
                                                    }else {
                                                        layer.msg(data.errormsg);
                                                        return false;
                                                    }
                                                }
                                            })

                                        }
                                    });
                                });
                                return true;

                        }else {
                                layer.msg(data.errormsg);
                                return false;
                            }
                        }
                    })
                    return true;
                } else {
                    layer.msg(data.errormsg);
                    return false;

                }

            }
        })
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

    $("#AddLine").on('click','.article_details',function(e) {
        const BlogName = $(e.target);
        $(location).prop("href", "/BlogDetail#"+BlogName.text());
        return true;
    });



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
        let lineNames = "<p class='article_details'>"+name[i]+"</p>";

        lineName = lineNames + lineName;

    }
    return lineName;
}

function AddNovel(Novel){
    // const NovelJson = JSON.parse(Novel);
    const NovelJson = Novel;
    const List = [];
    for (let i=0; i<NovelJson.length; i++){
        const NovelName = NovelJson[i].BigChapter;
        const NovelNames = NovelJson[i].NovelName;
        const List2 = [];
        for (let j=0; j<NovelNames.length; j++){
            const NovelNames_t = NovelNames[j];
            const a = {title: NovelNames_t,id: j};
            List2[j] = a;
        }
        const b = {title:  NovelName ,id:  i ,children: List2};
        List[i] = b;
    }
    return List;
}