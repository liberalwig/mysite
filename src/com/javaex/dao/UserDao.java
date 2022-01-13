//2022.01.11(화)수업-2022.01.11(화)21:17에 guestbook위해 수정하다가 여기다 해야 할 게 아님을 알게 됨/2022.01.14(금)01:03
package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 메소드 일반
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error." + e);
		}
	}

	// 저장 메소드 (사용자 입장에선 회원 가입)_수업 중 완성
	public int insert(UserVo userVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ? ) ";

			// 문자열을=>쿼리문으로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			// 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println(count + "건이 실행되었습니다");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return count;
	}

	// 회원 정보 1명 가져와서 로그인 시키기. 로그인 용_(세션 넣기 이전 과정'?이라고 썼는데 무슨 말? 로그인은 세션넣기 이후일 텐데)
	public UserVo getUser(String id, String password) {
		UserVo userVo = null;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " select no, ";
			query += "        name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			System.out.println(query);

			// 문자열을=>쿼리문으로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, id);
			pstmt.setString(2, password);

			// 실행
			rs = pstmt.executeQuery(); // 회원가입은 count = pstmt.executeUpdate(); 였다. 로그인/수정은 db필요하므로 쿼리문 필수.

			// 4. 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");// 회원고유번호(PK격) 쿼리문입력해서 저 아이디,패스워드인 사람의 넘버와 이름을 셀렉하고 그중 넘버를 Int에 담아줘
				String name = rs.getString("name");

				userVo = new UserVo(); // 좌항에 자료형 왜 안 써?
				userVo.setNo(no);
				userVo.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return userVo;
	}

	// modify: 회원 정보 1개 가져와서 수정하기
	public UserVo getUserVo(int no) {
		UserVo userVo = null;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " select id, ";
			query += "        password, ";
			query += "        name, ";
			query += "        gender";
			query += " from users ";
			query += " where no = ? ";

			System.out.println(query);

			// 문자열을=>쿼리문으로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4. 결과처리
			while (rs.next()) {
				int number = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name"); // '정우성 님 환영합니다'를 안 보여주려면 이 행 안 쓰면 돼
				String gender = rs.getString("gender");
				// String id = rs.getString("id"); //회원정보 수정하고도 세션에 유지될 정보가 이름이 아니라 id이기 위해 위
				// 가리고 이 행 생성

				userVo = new UserVo(no, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return userVo;
	}

	// 위에서 로그인해서 수정한 정보를 디비에 업데이트(=새로 반영)해주는 일
	public void Update(UserVo userVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += "update users";
			query += " set id = ?, ";
			query += "     password = ?, ";
			query += "     name = ?,";
			query += "     gender = ? ";
			query += "where no = ?; ";

			// 쿼리문을=>문자열로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			pstmt.setInt(5, userVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println("[" + count + " 건이 업데이트 되었습니다. ]");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
	}

}
