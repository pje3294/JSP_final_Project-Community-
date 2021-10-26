<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!doctype html>
<html lang="ko">

<head>
<title>Add-On 문의하기</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<!-- VENDOR CSS -->
<link rel="stylesheet"
	href="assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
<!-- GOOGLE FONTS -->
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
	rel="stylesheet">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	href="assets/img/apple-icon.jpg">
<link rel="icon" type="image/png" sizes="96x96"
	href="assets/img/favicon.jpg">
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- 상단 바 -->
		<mytag:navbar userName="${user.name}" userNum="${user.userNum}" iconId="${user.iconId}"/>
		<!-- 왼쪽 사이드 바 -->
		<mytag:sidebar ctgr='${param.bCtgr}' />
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<div class="panel" style="padding: 10px; width: 70%; margin : 0 auto;">
						<form action="inquery.do" method="post">
							<br>
							<br>
							<div
								style="text-align : center">항상
								발전하는  <img src="assets/img/logo.jpg" alt="Add-on Logo">  이 되겠습니다. <br><br> 자유롭게 문의 해 주세요.

							</div>
							<br>
							<br>
							
							<select class="form-control" name="subject">
										<option value="inquery">건의사항</option>
										<option value="complaint">불만사항</option>
										<option value="bug">버그발견</option>
										
									</select>
							<br>
							<br>
									
							<textarea name="inqueryText" rows="15" class="form-control"
								style="resize: none;" placeholder="문의사항을 작성해주세요." required></textarea>
								<div  style="text-align : right; padding : 10px;">
								<input  type="submit" class="btn btn-primary" value="전송하기">					
								</div>
					</div>
						</form>
						

					<!-- 수정 페이지 END -->
					<!-- 작성 페이지 -->

				</div>
			</div>
		</div>
		<!-- END MAIN CONTENT -->
	</div>
	<!-- END MAIN -->
	<div class="clearfix"></div>
	<footer>
		<div class="container-fluid">
			<p class="copyright">
				&copy; 2021 <a href="index.jsp" target="_blank">Add-On</a>. All Rights Reserved.
			</p>
		</div>
	</footer>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>

	
</body>

</html>
