<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<% UserVo authUser = (UserVo) session.getAttribute("authUser"); %><!--모든 애의 오브젝트이므로 혹시 몰라서 형변환해준다
-->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	if (authUser == null) {//로그인 이전 or 로그인 실패
	%>
	<ul>
		<li><a href="/mysite/user?action=loginForm" class="btn_s">로그인</a></li>
		<li><a href="/mysite/user?action=joinForm" class="btn_s">회원가입</a></li>
	</ul>
	<%
	} else { //로그인 이후 성공 시
	%>
	<ul>
		<li><%=authUser.getName()%> 님 안녕하세요^^</li>
		<li><a href="/mysite/user/" class="btn_s">로그아웃</a></li>
		<li><a href="/mysite/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
	</ul>
	<%
	}
	%>

</body>
</html>