<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="com.javaex.dao.GuestbookDao"%>
<%@ page import="java.util.List"%>
<!-- 이게 뭐지? -->
<%
UserVo authUser = (UserVo) session.getAttribute("authUser"); //모든 애의 오브젝트이므로 혹시 몰라서 형변환해준다
%>
<%
List<GuestbookVo> guestbookList = (List<GuestbookVo>) request.getAttribute("gList");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


	</div>

	<div id="container" class="clearfix">
		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li>일반방명록</li>
				<li>방명록</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head" class="clearfix">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
			</div>
			<!-- //content-head -->






			<div id="guestbook">
				<form action="" method="">
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th><label class="form-text" for="input-uname">이름</label>
								</td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label>
								</td>
								<td><input id="input-pass" type="password" name="password"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4" class="text-center"><button type="submit">등록</button></td>
							</tr>
						</tbody>

					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">

				</form>


				<c:foreach items=" ${ requestScope.gList } var= "vo" varstatus="status" >

					<table class="guestRead">
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 40%;">
							<col style="width: 10%;">
						</colgroup>
						<tr>
							<td>${ vo.no }</td>
							<!-- 정해진 이정재 말고 db에 있는 자료 -->
							<td>${ vo.name }</td>
							<td>${ vo.regDate }</td>
							<td><a href="/mysite/guest?action=delte?no=${ vo.no }">[삭제]</a></td>
						</tr>
						<tr>
							<td colspan=4 class="text-left">${ vo.content }</td>
						</tr>
					</table>
				</c:foreach>
				<!-- //guestRead -->


				<!-- //guestRead -->

			</div>
			<!-- //guestbook -->

		</div>
		<!-- //content  -->
	</div>
	<!-- //container  -->

	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>