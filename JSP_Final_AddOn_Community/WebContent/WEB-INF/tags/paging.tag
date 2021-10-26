<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageNum" %>
<%@ attribute name="paraName" %>
<%@ attribute name="path" %>
<%@ attribute name="pageLen" %>

<%
	String paraName = (String) jspContext.getAttribute("paraName");
	String stringPageNum =(String) jspContext.getAttribute("pageNum");
	int pageNum = Integer.parseInt(stringPageNum);
	String path = (String) jspContext.getAttribute("path");
	
	String beforePath = path+"&"+paraName+"="+String.valueOf(pageNum-1);
	String nextPath = path+"&"+paraName+"="+String.valueOf(pageNum+1);
	
	System.out.println("beforePath : "+beforePath);
	System.out.println("nextPath : "+ nextPath);
	
	jspContext.setAttribute("beforePath", beforePath);
	jspContext.setAttribute("nextPath", nextPath);
	

	int paging = 10; ///  몇 개 단위로 페이징 할 건지 정하는 변수 ★★★
	String stringPageLen = (String) jspContext.getAttribute("pageLen");
	int pageLen = Integer.parseInt(stringPageLen);
		
	int start = (pageNum/paging)*paging;
	int end = start+paging;
	int endPage = (pageLen-1)/paging+1;
	System.out.println("endPage : " + endPage);
	if(end >endPage){
		end =endPage;
		}
	
	jspContext.setAttribute("endPage" , endPage);
	
	
	
%>

							<!-- 페이징 버튼 --> <!-- 이전 버튼 -->
								<div class="text-center">
											<c:if test="${pageNum !=0}"><a href="${beforePath}"><button type="button" class="btn btn-primary btn-sm"> &lt; 이전 </button></a></c:if>
											&nbsp; 
	<% 
			
		
			for (int i=start; i<end;i++){
				%>
				
				<button type="button"
										onclick="location.href='${path}&${paraName}=<%=i%>'"
										class="label label-primary" <% if(i==pageNum){
											%>   id="nowPage"    <% // 현재 있는 페이지를 표시해 주는 id
										} %>><%= i+1%></button>
				
				<% 
				
			}
			
				if(pageNum != endPage-1){
			  %>
			   &nbsp;
			    <!--  다음 버튼 -->
				<a href="${nextPath}"><button type="button" class="btn btn-primary btn-sm">  다음 &gt; </button></a> 
		 	
		 	<% 
		 	}
	          %> 
							
							</div>
						
							<!-- 페이징 버튼 END -->