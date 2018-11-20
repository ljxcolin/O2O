/**
 * 本地用户注册脚本
 */
$(function(){
	//注册用户的URL
    var registerUserUrl = '/o2o/user/localauth/register';
    
    //绑定点击事件
    $('#commit').click(function(){
    	debugger;
    	//检查是否输入验证码
    	var verifyCodeActual = $('#j_kaptcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        var localAuth = {};
        userName = $('#username').val();
        if(userName.trim() == ''){
        	$.toast('用户名不能为空');
            return;
        }
        password = $('#psw').val();
        repassword = $('#repsw').val();
        if(password != repassword){
        	$.toast('两次密码输入不一致');
            return;
        }
        localAuth.userName = userName;
        localAuth.password = password;
        
    	var user = {};
    	user.name = $('#name').val();
    	user.gender = $('#gender').val();
    	user.userType = $('#usertype').val();
    	user.email = $('#email').val();
    	
    	//用户头像
    	var profileImg = $('#profile-img')[0].files[0];
    	
    	//组装表单数据
    	var formData = new FormData();
    	formData.append('verifyCodeActual', verifyCodeActual);
    	formData.append('userStr', JSON.stringify(user));
    	formData.append('localAuthStr', JSON.stringify(localAuth));
    	formData.append('userImg', profileImg);
    	
    	//利用ajax提交
        $.ajax({
            url: registerUserUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            dataType: 'json',
            success: function(data){
            	debugger;
                if(data.success){
                    $.toast(data.sucMsg);
                    window.location.href = "/o2o/useradmin/localauth/login";
                }else{
                    $.toast(data.errMsg);
                    //提交失败更换验证码
                    $('#kaptcha_img').click();
                }
            }
        });
        return false;
    });	
});