<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!doctype html>
<html lang="ko">

<head>
<title>Add-On</title>
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
		<mytag:navbar userName="${user.name}" userNum="${user.userNum}" iconId="${user.iconId}" />
		<!-- 왼쪽 사이드 바 -->
		<mytag:sidebar ctgr='index' />
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6 introduce">
							<div class="panel questionBox text-center">
								<div class="panel-body box">
									<h1>Question & Answer</h1>
									<br>
									<p>회원들과 질문 및 답을 주고 받을 수 있는 공간입니다.</p>
									<p>궁금한것이 있으면 Add-On회원들에게 물어보세요!</p>
								</div>
							</div>
						</div>
						<div class="col-md-6 introduce">
							<div class="panel boardBox text-center">
								<div class="panel-body box">
									<h1>Community</h1>
									<br>
									<p>회원들이 자유롭게 이용할 수 있는 공간입니다.</p>
									<p>원하는 것이 있으면 언제들 올려보아요!</p>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 introduce">
							<div class="panel testBox text-center">
								<div class="panel-body box">
									<h1>Coding Exam</h1>
									<br>
									<p>회원들과 코딩 문제를 내보고 풀어보는 공간입니다.</p>
									<p>코딩 연습이 필요할 때 이용해보세요!</p>
								</div>
							</div>
						</div>
						<div class="col-md-6 introduce">
							<div class="panel myPageBox text-center">
								<div class="panel-body box">
									<h1>My Page</h1>
									<br>
									<p>회원 정보, 활동 기록을 볼 수 있는 공간입니다.</p>
									<p>회원이 무슨 활동을 하였는지 확인할 수 있어요!</p>
								</div>
							</div>
						</div>
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
