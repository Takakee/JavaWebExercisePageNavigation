<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
	img{
		width:100%;
		height:100%;
		cursor:pointer;
	}
	.download{
		font-weight:bolder;
	}
	.mainJPG{
		margin:80px auto;
	}
	.user{
		margin-top:12px;
		margin-right:80px;
	}
	a{
		cursor:pointer;
	}
</style>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome/css/font-awesome.min.css">
<script src="https://cdn.jsdelivr.net/gh/stevenjoezhang/live2d-widget@latest/autoload.js"></script>

</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">TZdownloads</a>
			</div>			
			<div>
				<ul class="nav navbar-nav">
					<li><a href="#">首页</a></li>
					<li><a href="controller/GetDownloadListController" class="download">资源下载</a></li>
					<li><a href="userManage.jsp">用户管理</a></li>
					<li><a href="resourceManage.jsp">资源管理</a></li>
					<li><a href="personal.jsp">个人中心</a></li>
				</ul>
			</div>
			
			<div class="dropdown navbar-right  user">
				<button type="button" class="btn dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">
					<kbd class="glyphicon glyphicon-user"></kbd>
					welcome! ${userName }
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="background-color:#E83E42;min-width:100px;">
					<li role="presentation" >
						<kbd class="glyphicon glyphicon-log-out"></kbd>	
						<span><a href="controller/LogOutController">注销登录</a></span>
        			</li>
				</ul>
			</div>
			
		</div>
	</nav>

	<div class="container mainJPG">
   		<div class="jumbotron">			
	        <img src="images/main.jpg" title="主页面图片">
   		</div>
	</div>
</body>
</html>