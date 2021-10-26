<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="ctgr" %>

<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
					
						<c:if test="${ctgr == 'index'}">
						<li><a href="index.jsp" class="active"><i
								class="lnr lnr-home"></i> <span>메인 페이지</span></a></li>
						<li><a href="question.do" class=""><i
								class="lnr lnr-question-circle"></i> <span>Q & A</span></a></li>
						<li><a href="board.do" class=""><i class="lnr lnr-dice"></i>
								<span>자유 게시판</span></a></li>
						<li><a href="test.do" class=""><i class="lnr lnr-code"></i>
								<span>코딩 테스트</span></a></li>
						<li><a href="inquery.jsp" class=""><i class="lnr lnr-bubble"></i>
								<span>문의하기</span></a></li>
						</c:if>
						
						<c:if test="${ctgr=='question'}">
						<li><a href="index.jsp" class=""><i
								class="lnr lnr-home"></i> <span>메인 페이지</span></a></li>
						<li><a href="question.do" class="active"><i
								class="lnr lnr-question-circle"></i> <span>Q & A</span></a></li>
						<li><a href="board.do" class=""><i class="lnr lnr-dice"></i>
								<span>자유 게시판</span></a></li>
						<li><a href="test.do" class=""><i class="lnr lnr-code"></i>
								<span>코딩 테스트</span></a></li>
						<li><a href="inquery.jsp" class=""><i class="lnr lnr-bubble"></i>
								<span>문의하기</span></a></li>
						</c:if>
						
						<c:if test="${ctgr=='board'}">
						<li><a href="index.jsp" class=""><i
								class="lnr lnr-home"></i> <span>메인 페이지</span></a></li>
						<li><a href="question.do" class=""><i
								class="lnr lnr-question-circle"></i> <span>Q & A</span></a></li>
						<li><a href="board.do" class="active"><i class="lnr lnr-dice"></i>
								<span>자유 게시판</span></a></li>
						<li><a href="test.do" class=""><i class="lnr lnr-code"></i>
								<span>코딩 테스트</span></a></li>
						<li><a href="inquery.jsp" class=""><i class="lnr lnr-bubble"></i>
								<span>문의하기</span></a></li>
						</c:if>
						
						<c:if test="${ctgr=='test'}">
						<li><a href="index.jsp" class=""><i
								class="lnr lnr-home"></i> <span>메인 페이지</span></a></li>
						<li><a href="question.do" class=""><i
								class="lnr lnr-question-circle"></i> <span>Q & A</span></a></li>
						<li><a href="board.do" class=""><i class="lnr lnr-dice"></i>
								<span>자유 게시판</span></a></li>
						<li><a href="test.do" class="active"><i class="lnr lnr-code"></i>
								<span>코딩 테스트</span></a></li>
						<li><a href="inquery.jsp" class=""><i class="lnr lnr-bubble"></i>
								<span>문의하기</span></a></li>
						</c:if>
						
						<c:if test="${empty ctgr or ctgr=='announce'}">
						<li><a href="index.jsp" class=""><i
								class="lnr lnr-home"></i> <span>메인 페이지</span></a></li>
						<li><a href="question.do" class=""><i
								class="lnr lnr-question-circle"></i> <span>Q & A</span></a></li>
						<li><a href="board.do" class=""><i class="lnr lnr-dice"></i>
								<span>자유 게시판</span></a></li>
						<li><a href="test.do" class=""><i class="lnr lnr-code"></i>
								<span>코딩 테스트</span></a></li>
						<li><a href="inquery.jsp" class=""><i class="lnr lnr-bubble"></i>
								<span>문의하기</span></a></li>
						</c:if>
						
					</ul>
				</nav>
			</div>
		</div>