//2022.01.11(화)13:52수업-17:47
package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		else if ("loginForm".equals(action)) {
			System.out.println("user > loginForm");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}

		else if ("login".equals(action)) {
			System.out.println("user > login");

			String id = request.getParameter("id"); // 주소 뒤에 붙는 이름은-이 경우엔 괄호 안-큰따옴표를 쳐야 함. 파라미터를 뽑아 먹음.
			String password = request.getParameter("password");

			System.out.println(id);
			System.out.println(password);

			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);// authVo: 인증성공한 유저
			// 데이터 있으면 =>어트리뷰트 session authVo에 넣고: 로그인 성공!
			// 데이터 null이면 => userVo에 그대로. 아무 것도 안 한다: 로그인 실패

			if (authVo == null) {// 로그인 실패
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite/user?action=loginForm");

			} else {
				System.out.println("로그인 성공");

				HttpSession session = request.getSession(); // 세션값을 메모리에 넣어줘
				session.setAttribute("authUser", authVo); // 만일 여기서 rqst.setAttribute라 하면 세션메모리를 위에서 만들어 놓고 안 쓰는 격

				WebUtil.redirect(request, response, "/mysite/main");

				// 로그인 성공해서 값을 넣기
				// System.out.println(authVo); // authVo=0x123이런 식으로 주소 형태니까. 이 두 항을 로그인 성공 뒤에
				// 붙여 놨더니 다시 toString빈페이지 나옴
				// System.out.println(authVo.toString()); // 둘 행의 효과는 동일함. 원래println이 변수 안에 있는
				// toString()을 보여달라는 명령이므로

			}
		}

		else if ("logout".equals(action)) {
			System.out.println("user > logout");

			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
		}

	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
