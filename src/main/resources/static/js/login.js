$(document).ready(function() {

    $(".registers").click(function() {
        $(".form-content").attr("style","display:none");
        $("#Registers").attr("style","display:block");
        return true
    })
    $(".return").click(function() {
        $(".form-content").attr("style","display:block");
        $("#Registers").attr("style","display:none");
        return true
    })

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

    // 用户注册
    $("#ButtonRegisters").click(function() {
        const username = $("#username2").val();
        const password = $("#password2").val();
        const age = $("#age").val();

        if (IsEmpty(username)===false || IsEmpty(password)===false || IsEmpty(age)===false){
            $("#message").text("");
            $("input[ type='text']").val("");
            $("#error2").text("注册个人信息不能为空！");
            return false;
        }
        if (IsNotNull(username)===false || IsNotNull(password)===false || IsNotNull(age)===false){
            $("#message").text("");
            $("input[ type='text']").val("");
            $("#error2").text("注册个人信息不能为特殊字符");
            return false;
        }
        $.ajax({
            url:"/login/Registers",
            type:"POST",
            datatype:"JSON",
            data: $('#FromRegisters').serialize(),
            success:function (data) {
                if(data.code==="0"){
                    $("#error2").text("注册成功");
                    return true;
                }else {
                    $("#error2").text(data.errormsg);
                    return false;
                }
            }
        })
    });
});

