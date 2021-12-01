$(document).ready(function() {

    $("#blogback").click(function() {
        $(location).prop("href","/Blog")
        return true;
    });



    const BlogName = decodeURIComponent((window.location.hash.substring(1)));

    $.ajax({
            url:"/blog/content",
            type:"POST",
            datatype:"JSON",
            data: "BlogName="+BlogName,
            success:function (data) {
                if(data.code==="0"){
                    const time = data.data[0].createtime;
                    $(".title").append(BlogName)
                    $(".time").append(time.replace("T"," "))
                    $(".category").append(data.data[0].category)
                    $(".user").append(data.data[0].user)
                    // $("#content").append(data.data[0].content)
                    const markdown = data.data[0].content;

                    const initOutline = () => {
                        const headingElements = []
                        Array.from(document.getElementById('preview').children).forEach((item) => {
                            if (item.tagName.length === 2 && item.tagName !== 'HR' && item.tagName.indexOf('H') === 0) {
                                headingElements.push(item)
                            }
                        })

                        let toc = []
                        window.addEventListener('scroll', () => {
                            const scrollTop = window.scrollY
                            toc = []
                            headingElements.forEach((item) => {
                                toc.push({
                                    id: item.id,
                                    offsetTop: item.offsetTop,
                                })
                            })

                            const currentElement = document.querySelector('.vditor-outline__item--current')
                            for (let i = 0, iMax = toc.length; i < iMax; i++) {
                                if (scrollTop < toc[i].offsetTop - 30) {
                                    if (currentElement) {
                                        currentElement.classList.remove('vditor-outline__item--current')
                                    }
                                    let index = i > 0 ? i - 1 : 0
                                    if (document.querySelector('div[data-id="' + toc[index].id + '"]')) {
                                        document.querySelector('div[data-id="' + toc[index].id + '"]').classList.add('vditor-outline__item--current')
                                    }

                                    break
                                }
                            }



                        })
                    }
                    Vditor.preview(document.getElementById('preview'),
                        markdown, {
                            speech: {
                                enable: false,
                            },
                            anchor: 1,
                            after() {
                                if (window.innerWidth <= 768) {
                                    return
                                }
                                const outlineElement = document.getElementById('outline')
                                Vditor.outlineRender(document.getElementById('preview'), outlineElement)
                                if (outlineElement.innerText.trim() !== '') {
                                    outlineElement.style.display = 'none'
                                    initOutline()
                                }
                                const content = document.getElementById('preview')
                                const comments = document.createElement("div");
                                comments.setAttribute("class","comments");
                                content.append(comments);

                                const Comment_photo = document.createElement("img");
                                Comment_photo.setAttribute("class","Comment_photo");
                                Getuser()
                                comments.append(Comment_photo)

                                const Comment_box = document.createElement("input");
                                Comment_box.setAttribute("class","Comment_box");
                                Comment_box.setAttribute("placeholder","请发表有价值的评论， 博客评论不欢迎灌水，良好的社区氛围需大家一起维护。");
                                comments.append(Comment_box)

                                const Comment_emoji = document.createElement("img");
                                Comment_emoji.setAttribute("class","Comment_emoji");
                                comments.append(Comment_emoji)

                                const Comment_add = document.createElement("input");
                                Comment_add.setAttribute("class","Comment_add");
                                Comment_add.setAttribute("type","button");
                                Comment_add.setAttribute("value","评论");
                                comments.append(Comment_add)

                                const Comment_reply = document.createElement("div");
                                Comment_reply.setAttribute("class","Comment_reply");
                                comments.append(Comment_reply)

                                const Comment_replyList = document.createElement("div");
                                Comment_replyList.setAttribute("class","Comment_replyList");
                                Comment_reply.append(Comment_replyList)

                                const Comment_replyPage = document.createElement("div");
                                Comment_replyPage.setAttribute("class","Comment_replyPage");
                                Comment_replyPage.setAttribute("id","demo0");
                                Comment_reply.append(Comment_replyPage)

                                let flag = true;
                                $(".Comment_add").click(function() {

                                    // let flag;
                                    if ($(".Comment_box").val() === "" || $(".Comment_box").val() == null) {
                                        layer.msg("评论不能为空！");
                                    } else {
                                        if (flag) {
                                            Addcomment(BlogName);
                                            flag = false;
                                            setTimeout('flag = true', 60000);
                                        } else {
                                            layer.msg("一分钟内请勿频繁评论！");
                                        }

                                    }

                                })
                                UpPadge();
                                Querycomment(BlogName);



                            },
                        });

                    return true;
                }else {
                    layer.msg(data.errormsg);
                    return false;

                }

            }
        });




})

function Getuser(){

    $(document).ready(function() {
        $.ajax({
            url: "/UserInfo",
            type: "POST",
            datatype: "JSON",
            success: function (data) {
                if (data.code === "0") {
                    $(".Comment_photo").attr("src",data.data[0].Head);

                    return true;
                } else {
                    layer.msg("获取用户信息失败！");
                    return false;

                }

            }
        });
    });

}
function Addcomment(BlogName){
    const comment = $(".Comment_box").val();
    $.ajax({
        url:"/blog/AddComment",
        type:"POST",
        datatype:"JSON",
        data: "comment="+comment+"&BlogName="+BlogName,
        success:function (data) {
            if(data.code==="0"){
                $("#error").text("");
                layer.msg("添加评论成功！");
                return true;
            }else {
                $("input[ type='text']").val("");
                $("#error").text(data.errormsg);
                return false;

            }

        }
    })

}

function Querycomment(BlogName){
    // const comment = $(".Comment_box").val();
    $.ajax({
        url:"/blog/QueryComment",
        type:"POST",
        datatype:"JSON",
        data: "BlogName="+BlogName,
        success:function (data) {
            if(data.code==="0"){
                $("#error").text("");
                // layer.msg("查询评论成功！");
                return true;
            }else {
                $("input[ type='text']").val("");
                $("#error").text(data.errormsg);
                return false;

            }

        }
    })

}

function UpPadge(){
    layui.use('laypage', function(){
        const laypage = layui.laypage;

        laypage.render({
            elem: 'demo0'
            ,count: 50
        });
    });
}