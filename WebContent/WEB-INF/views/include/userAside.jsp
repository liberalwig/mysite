<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<% UserVo authUser = (UserVo) session.getAttribute("authUser"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	
			<div id="userAside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
		
			<!-- //userAside -->
			
			
			