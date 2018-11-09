/**
 * 
 */
//校验验证码
function changeVerifyCode(img){
    img.src = "/o2o/kaptcha?date=" + new Date().getTime();
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

//解析日期
Date.prototype.Format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)){
    	fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o){
        if (new RegExp("(" + k + ")").test(fmt)){
        	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}