var userName_flag = false;
var chrName_flag = false;
var mail_flag = false;
var province_flag = false;
var city_flag = false;
var password_flag = false;
var confirmPassword_flag = false;

function fillProvince() {
    $.ajax({
        type: "post",
        url: "QueryProvinceCityServlet",
        data: {},
        dataType: "json",
        success: function(response) {
            var provinceElement = document.getElementById("province");
            //清除select的所有option
            provinceElement.options.length = 0;
            //增加一个选项
            provinceElement.add(new Option("请选择省份", ""));
            //循环增加其他所有选项
            for (index = 0; index < response.length; index++) {
                provinceElement.add(new Option(response[index].chrName,
                    response[index].id));
            }
        }
    });
}

$(document).ready(function() {
    //页面加载的时候就先填充一遍
    fillProvince(); 

    
    //省份下拉框选择发生改变事件：
    $("#province").change(function(e) {
        // 检查是否选择了省份，没有选择则给出错误提示并返回
        if ($(this).val() == "") {
            $("#provinceError").css("color", " #c00202");
            $("#provinceError").text("必须选择省份！");
            return;
        }
        province_flag = true;
        $("#provinceError").text("");
        // 清空城市下拉框
        $("#city").empty();
        // 清空城市下拉框选项，增加默认提示项
        $("#city").append($("<option>").val("").text("请选择城市"));
        // 拿到省份的ID
        var provinceCode = $("#province").val();
        //  选择了省份的情况下，清除错误提示信息，查询被选择省份对应的城市信息，增加到城市下拉框的选择列表中        
        $.ajax({
            type: "post",
            url: "QueryProvinceCityServlet",
            data: { provinceCode: provinceCode },
            dataType: "json",
            success: function(response) {
                for (index = 0; index < response.length; index++) {
                    var option = $("<option>").val(response[index].id)
                        .text(response[index].chrName);
                    $("#city").append(option);
                }
            }
        });
    });

    $("#province").blur(function(e) {
        if ($(this).val() == "") {
            $("#provinceError").css("color", " #c00202");
            $("#provinceError").text("请选择城市！");
        } else {
            $("#provinceError").text("");
            province_flag = true;
        }
    });


    // 城市下拉框选择项变化事件：检查是否选择了城市
    $("#city").blur(function(e) {
        if ($(this).val() == "") {
            $("#cityError").css("color", " #c00202");
            $("#cityError").text("请选择城市！");
        } else {
            $("#cityError").text("");
            city_flag = true;
        }
    });

    //用户名输入框离开事件
    $('#userName').blur(function(event) {
        if ($(this).val() == "") {
            $("#userNameError").css("color", " #c00202");
            $("#userNameError").text("用户名不能为空");
            return;
        }
        var usernamePattern = /^[a-zA-Z][a-zA-Z\d]{3,14}$/;//用户名正则表达式；
        if (usernamePattern.test(this.value) == false) {
            $("#userNameError").css({"color":" #c00202","font-size":"10px","text-indent":"50px"});
            $("#userNameError").text("用户名需以字母开头,包含英文字母和数字,长度为4到15个字符");
            return;
        }
        $.ajax({
            type: "post",
            url: "CheckUserExistServlet",
            data: { userName: $(this).val() },
            dataType: "json",
            success: function(response) {
                if (response.code == 0) {
                    $("#userNameError").css("color", "green");
                    $("#userNameError").text("用户名可以用来注册");
                    alert("用户名可以用来注册");
                    userName_flag = true;
                } else {
                    $("#userNameError").css("color", "#c00202");
                    $("#userNameError").text("用户名已存在");
                    alert("用户名不可以用来注册");
                }
            }
        });
    });
    /**
     * 真实姓名输入框离开事件
     * 使用正则表达式表达式检查输入是否符合要求（必须为中文，长度2-4）
     */
    $('#chrName').blur(function(event) {
        if ($(this).val() == "") {
            $("#chrNameError").css("color", " #c00202");
            $("#chrNameError").text("中文姓名不能为空");
            return;
        }
        if (/^[\u4e00-\u9fa5]{2,4}$/.test(this.value) == false) {
            $("#chrNameError").css("color", " #c00202");
            $("#chrNameError").text("中文姓名只能使用中文，长度为2到4个字符");
        } else {
            chrName_flag = true;
            $("#chrNameError").text("");
        }
    });
    /**
     * 邮箱地址输入框离开事件
     * (1)使用正则表达式表达式检查输入是否符合要求
     * (2)使用ajax检查邮箱地址是否已存在
     */
    $("#mail").blur(function(event) {
        if ($(this).val() == "") {
            $("#mailError").css("color", " #c00202");
            $("#mailError").text("邮箱不能为空");
            return;
        }
        var mailPattern=/^[a-zA-Z0-9]+([._\\]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
        if (mailPattern.test(this.value) == false) {
            $("#mailError").css("color", " #c00202");
            $("#mailError").text("邮箱格式不对");
            return;
        }
        else{
        	mail_flag = true;
        }
        
        //邮箱可以重复
    });

    //密码输入框离开事件：
    $("#password").blur(function() {
        var password_min_length = 3
        if ($("#password").val().length >= password_min_length) {
            $("#passwordError").css("color", "green");
            $("#passwordError").text("密码设置成功");
            password_flag = true;
        } else {
            $("#passwordError").css("color", "#c00202");
            $("#passwordError").text("密码长度至少为3");
        }
    });

    //确认密码离开事件
    $("#password1").blur(function() {
        var password_min_length = 3;
        if ($("#password").val() == $("#password1").val() && $("#password").val().length >= password_min_length) {
            $("#password1Error").css("color", "green");
            $("#password1Error").text("密码确认成功");
            confirmPassword_flag = true;
        } else {
            $("#password1Error").css("color", "#c00202");
            $("#password1Error").text("密码不一致或长度不够");

        }
    });

    /**
     * 注册按钮点击事件
     */
    $("#btLogin").click(function(e) {
        if (userName_flag && chrName_flag && mail_flag && province_flag && city_flag && password_flag && confirmPassword_flag) {
            $.ajax({
                type: "post",
                url: "SignUpController",
                data: $("#registerForm").serialize(), //将表单内容序列化成一个URL 编码字符串
                dataType: "json",
                success: function(response) {
                    if (response.code == 0) {
                        alert("注册成功，将自动跳转到登录页面");
                        window.location.href = "Login.html";
                    }
                }
            });
        } else {
            $("#userName").blur();
            $('#chrName').blur();
            $("#mail").blur();
            $("#password").blur();
            $("#password1").blur();
            $("#province").blur();
            $("#city").blur();
        }
    });
});