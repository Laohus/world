$(document).ready(function() {

    // 用户登录
    $("#ButtonLogin").click(function() {
        const username = $("#username").val();
        const password = $("#password").val();

        if (IsEmpty(username)===false || IsEmpty(password)===false){
            $("#message").text("");
            $("input[ type='text']").val("");
            $("#error").text("用户、密码不能为空");
            return false;
        }
        if (IsNotNull(username)===false || IsNotNull(password)===false){
            $("#message").text("");
            $("input[ type='text']").val("");
            $("#error").text("用户、密码不能为特殊字符");
            return false;
        }

        $.ajax({
            url:"/login/account",
            type:"POST",
            datatype:"JSON",
            data: $('#FormLogin').serialize(),
            success:function (data) {
                if(data.code==="0"){
                    $("#error").text("");
                    $(location).prop("href","/Welcome")
                    return true;
                }else {
                    $("input[ type='text']").val("");
                    $("input[ type='password']").val("");
                    $("#error").text(data.errormsg);
                    return false;

                }

            }
        })

    });

    $("#qqLoginBtn").click(function() {

        window.open('https://graph.qq.com/oauth2.0/authorize?client_id=101981464&amp;response_type=token&amp;scope=all&amp;redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FWelcome', 'oauth2Login_10576' ,'height=525,width=585, toolbar=no, menubar=no, scrollbars=no, status=no, location=yes, resizable=yes')

        // QC.Login({
        //         btnId:"qqLoginBtn",
        //         size:"B_M"
        //     },
        //     function (reqData,opts){
        //
        //     }
        // );
    })



});
