<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/userManager.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-3.5.0.min.js"></script>
<script type="text/javascript" src="js/userManagerJS.js"></script>
<script src="js/SignUpJS.js?"></script>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!-- <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script> -->
<!-- <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script> -->

<style type="text/css">
	.download{
		font-weight:bolder;
	}
	.user{
		margin-top:12px;
		margin-right:80px;
	}
	a{
		cursor:pointer;
	}
</style>

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

	<div id="pageBody">
   		<div id="search">			
	        <form id="searchForm">
				<input type="text" id="sUserName" name="userName" placeholder="输入用户名..."/>
				<input type="text" id="sChrName" name="chrName" placeholder="输入中文名..."/>
				<input type="text" id="sRole" name="role" placeholder="输入角色..."/>
			</form>
			<div id="bt">
				<a href="#" id="btSearch" class="btn btn-primary">查找</a>
				<a href="#" id="btClear" class="btn btn-success">清空</a>
				<a href="#" id="btAdd" class="btn btn-info">增加</a>
				<a href="#" id="btDelete" class="btn btn-danger">删除</a>
				<a href="#" id="btUpdate" class="btn btn-warning">修改</a>
			</div>
		</div>
		   
		<table>
			<thead>
				<tr>
					<th width="50"><input type="checkbox" id="ckAll" title="选择"/></th>
					<th width="150" id="sortByUserName" data="userName">用户名</th>
					<th width="180">中文名</th>
					<th width="150">密码</th>
					<th width="100">角色</th>
					<th width="180">操作</th>
				</tr>
		   </thead>
		   <tbody>
		   </tbody>
	   </table>

		<div id="paging">
			<div class="pageSize">每页
				<select id="pageSize">
					<option selected>3</option>
					<option>5</option>
					<option>7</option>
				</select>条数据，共
				<span id="total"></span>条数据，
				<span id="pageNumber">1</span>页/<span id="pageCount"></span>页
			</div>
			<div id="pageNav">
				<a id="first" href="#">首页</a>
				<a id="back" href="#">上一页</a>
				<a id="next" href="#">下一页</a>
				<a id="last" href="#">尾页</a>
			</div>
	   </div>
	</div>

	<!-- 遮罩层 -->
	<div id="fade" class="black_overlay" onclick="CloseDiv()"></div>
	<!-- 新增界面 -->
	<div id="MyDiv" class="white_content">
		<!-- 关闭按钮x -->
		<div style="text-align: right; margin-left: 5px; height: 20px;">
			<span id="xBtn" title="点击关闭" onclick="CloseDiv('MyDiv','fade')">x</span>
		</div>
		<!-- 内部新增界面 -->
		<div id="loginForm">
			<div class="title">
				<span class="titleSignUp">创建新的用户</span>
			</div>
	
			<form id="registerForm">
				<p>
					<input id="userName" type="text" name="userName" placeholder="Enter userName..." />
					<span class="errSpan" id="userNameError"></span>
				</p>
				<p>
					<input type="text" id="chrName" name="chrName" placeholder="Enter chrName..."/>
					<span class="errSpan" id="chrNameError"></span>
				</p>
				<p>
					<input type="text" id="mail" name="mail" placeholder="Enter mail..."/>
					<span class="errSpan" id="mailError"></span>
				</p>
				<p> 
					<select id="province" name="provinceCode">
						<option value="">select province</option>
					</select>
					<span class="errSpan" id="provinceError"></span>
				</p>
				<p> 
					<select id="city" name="cityCode">
					<option value="">select city</option>
					</select>
					<span class="errSpan" id="cityError"></span>
				</p>
				<p>
					<input id="password" name="password" type="password" placeholder="Enter password..." />
					<span class="errSpan" id="passwordError"></span>
				</p>
				<p>
					<input id="password1" name="password1" type="password" placeholder="Confirm password..." />
					<span class="errSpan" id="password1Error"></span>
				</p>
				<p>
					<input id="btLogin" type="button" value="Join&nbsp;&nbsp;Us " />
					<span class="errSpan" id="checkError"></span>
				</p>
			</form>
		</div><!--  #loginForm -->
	</div>
</body>
</html>