$(document).ready(function() {

    $("#blogback").click(function() {

        $(location).prop("href","/Blog")
        return true;
    });

    $("#AddBlog").click(function() {

        const classlfy = $('input[name="like"]:checked').val();
        const BlogTitle = $("#Blog-title").val();
        const content = vditor.getValue();
        if (BlogTitle===""){
            return true;
        }
        const obj = {"BlogTitle": BlogTitle, "ClassLfy": classlfy,"content":content};

        $.ajax({
            url:"/AddNewBlog",
            type:"POST",
            datatype:"JSON",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(obj),
            success:function (data) {
                if(data.code==="0"){
                    layer.msg("新增成功");
                    setTimeout(function (){
                        const index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);

                    },2000);
                    return true;
                }else {
                    layer.msg(data.errormsg);
                    return false;

                }

            }
        })

    })



})