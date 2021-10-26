<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="ko" class="fullscreen-bg">

<head>
<title>프로필 사진</title>
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
		<div class="vertical-align-wrap">
			<div class="vertical-align-middle">
				<div class="auth-box lockscreen clearfix">
					<div class="content">
						<h1 class="sr-only">프로필 사진 수정</h1>
						<div class="logo text-center"><img src="assets/img/logo.png" alt="Klorofil Logo"></div>
						<div class="user text-center">
							<img src="images/${user.iconId}" class="img-circle profilImg" alt="유저사진">
							<h2 class="name">${user.id}</h2>
						</div>
						<form action = "imgUpload.do" method="post" name="imgUp" enctype="multipart/form-data">
							<input type="hidden" name="userNum" value="${user.userNum}" />
							<input type="file" name="filename" id="file" value="${user.iconId}" required>
							<div class="form-group">
								<div class="input-group">
											<input class="form-control" placeholder="첨부파일" readonly>
											<span class="input-group-btn"><label class="btn btn-primary" for="file" >파일 선택</label></span>
										</div>
							</div>
							<div class="form-group">
								<input class="btn btn-primary btn-block" type="submit" value="사진 수정">
								<input class="btn btn-warning btn-block" type="button" onclick="location.href='imgDelete.do?filename=${user.iconId}&userNum=${user.userNum}'" value="기본이미지">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END WRAPPER -->
</body>

<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
<script type="text/javascript">
	$("#file").on('change', function() {
		var fileName = $("#file").val();
		$(".form-control").val(fileName);
	});
	
	document.addEventListener('DOMContentLoaded', function(){
	    //이미지 객체 타입으로 이미지 확장자 밸리데이션
	    var validateType = function(img){
	        return (['image/jpeg','image/jpg','image/png'].indexOf(img.type) > -1);
	    }

	    var validateName = function(fname){
	        let extensions = ['jpeg','jpg','png'];
	        let fparts = fname.split('.');
	        let fext = '';
	    
	        if(fparts.length > 1){
	            fext = fparts[fparts.length-1];
	        }
	    
	        let validated = false;
	        
	        if(fext != ''){
	            extensions.forEach(function(ext){
	                if(ext == fext){
	                    validated = true;
	                }
	            });
	        }
	    
	        return validated;
	    }

	    // 파일 선택 필드에 이벤트 리스너 등록
	    document.getElementById('file').addEventListener('change', function(e){
	        let elem = e.target;
	        if(validateType(elem.files[0])){
	            let preview = document.querySelector('.profilImg');
	            preview.src = URL.createObjectURL(elem.files[0]); //파일 객체에서 이미지 데이터 가져옴.
	            preview.onload = function() {
	                URL.revokeObjectURL(preview.src); //URL 객체 해제
	            }
	        }else{
	        console.log('이미지 파일이 아닙니다.');
	        }
	    });
	});

</script>
</html>
