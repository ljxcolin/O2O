/**
 * 
 */
//校验验证码
function changeVerifyCode(img){
    img.src = "../kaptcha?date=" + new Date().toString();
}

//获取请求参数
function getQueryString(name){
	var regex = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	//search表示请求参数部分，即?key=value，substr(1)则表示取?之后的全部
	var result = window.location.search.substr(1).match(regex);
	if(result != null){
		return decodeURIComponent(result[2]);
	}
	return '';
}