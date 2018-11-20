/*
 * 用户登录或注册JS脚本 
 */
$(function() {
	//debugger;
    var loginUrl = '/o2o/user/localauth/login';
    var loginCount = 0;

    $('#login').click(function() {
    	//debugger;
        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_kaptcha').val();
        var needVerify = false;
        if (loginCount >= 3) { //连续登录失败3次后需要验证码
            if (!verifyCodeActual) {
                $.toast('请输入验证码');
                return false;
            } else {
                needVerify = true;
            }
        }
        $.ajax({
            url: loginUrl,
            async: true,
            cache: false,
            type:'POST',
            data: {
                "userName": userName,
                "password": password,
                "verifyCodeActual": verifyCodeActual,
                "needVerify": needVerify
            },
            dataType: 'json',          
            success: function(data) {
            	//debugger;
                if (data.success) {
                    $.toast(data.sucMsg);
                    window.location.href = '/o2o/frontend/index';
                } else {
                    $.toast(data.errMsg);
                    loginCount++;
                    if (loginCount == 3) {
                        $('#verifyBlock').show();
                    }else if(loginCount > 3){
                    	$('#kaptcha_img').click();
                    }
                }
            }
        });
        return false;
    });

    $('#register').click(function() {
        window.location.href = '/o2o/useradmin/localauth/register';
        return false;
    });
});