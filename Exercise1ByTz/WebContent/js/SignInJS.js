function changeCode() {
	var codeImg = document.getElementById("verifyCode");
	codeImg.src = "controller/CreateImageController?t="
			+ Math.random();
}


var xmlHttp;
// 创建XMLHttpRequest对象
function createXmlHttp() {
	if(window.XMLHttpRequest)
	{
		xmlHttp = new XMLHttpRequest();
	}else{
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
}

// 使用原生js实现ajax登录
function ajaxCheckLogin() {
	var userName = document.getElementById("userName").value;
	var password = document.getElementById("password").value;
	var VerifyCode= document.getElementById("userCode").value;
	var setFreeLogin= document.getElementById("setFreeLogin").value;
	var data = "userName=" + userName + "&password=" + password + "&VerifyCode=" + VerifyCode+"&setFreeLogin="+setFreeLogin;
	if (document.getElementById("setFreeLogin").checked)
        data = data + "&setFreeLogin="+setFreeLogin;
	else
		data = data + "&setFreeLogin=false";
	
	createXmlHttp(); //使用自定义函数创建XMLHttpReque对象
	xmlHttp.open("post","controller/AjaxLoginCheck",true);
 	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
 	xmlHttp.send(data);
 	xmlHttp.onreadystatechange = function () { //回调函数
		if(xmlHttp.readyState==4 && xmlHttp.status==200)
		{
			var response = xmlHttp.responseText;
			var json = JSON.parse(response);
			if(json.code == 0){ //登录成功
				window.location.href="main.jsp";
			}else{ //登录失败
				document.getElementById("checkError").innerText = json.info; //显示返回错误信息
			}
		}
    }
}
