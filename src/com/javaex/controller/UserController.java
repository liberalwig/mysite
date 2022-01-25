package com.javaex.controller;

import java.io.IOException;
import java.sql.SQLException;

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
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// 유저_1>회원가입폼
		if ("joinForm".equals(action)) {
			System.out.println("/user > joinForm");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

			// 유저_2>회원가입
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

			userDao.insert(userVo);

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}

		// 유저_3>로그인폼
		else if ("loginForm".equals(action)) {
			System.out.println("user > loginForm");

			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}

		// 유저_4>로그인 후 성공시 메인으로
		else if ("login".equals(action)) {
			System.out.println("user > login");

			String id = request.getParameter("id"); // 주소 뒤에 붙는 이름은-이 경우엔 괄호 안-큰따옴표를 쳐야 함. 파라미터를 뽑아 먹음.
			String password = request.getParameter("password");

			System.out.println(id);
			System.out.println(password);

			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(id, password);// authVo: 인증성공한 유저
			// 데이터 있으면 =>어트리뷰트 session authVo이라는 장소에 로그인 정보값 넣어져 있으므로: 로그인 성공!
			// 데이터 null이면 => userVo에 그대로. 아무 것도 안 한다: 로그인 실패

			if (authUser == null) {// 로그인 실패
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");

			} else {// 로그인 성공
				System.out.println("로그인 성공");

				HttpSession session = request.getSession(); // 세션값을 메모리에 넣어줘
				session.setAttribute("authUser", authUser); // 호출할 이름과 넣을 변수

				WebUtil.redirect(request, response, "/mysite/main");

				// 로그인 성공해서 값을 넣기
				// System.out.println(authVo); // authVo=0x123이런 식으로 주소 형태니까. 이 두 항을 로그인 성공 뒤에
				// 붙여 놨더니 다시 toString빈페이지 나옴
				// System.out.println(authVo.toString()); // 둘 행의 효과는 동일함. 원래println이 변수 안에 있는
				// toString()을 보여달라는 명령이므로

			}
		}

		// 유저_5>로그아웃
		else if ("logout".equals(action)) {
			System.out.println("user > logout");

			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite/main");
		}

		// 유저_6>수정폼
		else if ("modifyForm".equals(action)) {
			System.out.println("user > modifyForm");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			int no = authUser.getNo(); // 세션에 있는 authUser에 No저장해서 정수형 no에 담아

			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);

			request.setAttribute("userVo", userVo); // UserVo에 들어가서 request. setAttribute("Uservo" , userVo)

			// 현재로그인된 넘버를 파라미터로 정보뽑아오세요
			// no에 묶여있는 모든 정보 가져와 => dao에 넘버만 넣어서 걔 데이터를 가져와주는 메소드를 만들어야 해
			// UserVo에 넣는다

			WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp"); // modifyForm화면을 띄워준다

			// 유저_7>수정
		} else if ("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no")); // 밑은 String에 담으니까 형변환 불필요함
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(no, id, password, name, gender);
			UserDao userDao = new UserDao();

			System.out.println(userVo);

			userDao.Update(userVo);
			UserVo authVo = new UserVo();
			authVo.setNo(userVo.getNo());
			authVo.setName(userVo.getName());

			HttpSession session = request.getSession(); // 세션값을 메모리에 넣어줘
			session.setAttribute("authUser", authVo);// 호출할 이름과 넣을 변수
			System.out.println(authVo);

			WebUtil.redirect(request, response, "/mysite/main");

		}

		// 이외 오류
		else {
			System.out.println("파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
