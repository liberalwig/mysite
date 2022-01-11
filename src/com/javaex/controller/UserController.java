//2022.01.11(화)13:52수업-17:47 과제하기 전 우선 쭉 훑기
package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("/user");

		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {
			System.out.println("/user > joinForm");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			System.out.println("user > join");

			// 파라미터값 가져오기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// 파라미터 ==>Vo로 만들기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);

			// UserDao의 insert()로 저장하기 (회원에겐 가입하기)
			UserDao userDao = new UserDao();
			int count = userDao.insert(userVo);

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
