//2022.01.11(화)수업-2022.01.11(화)21:17에 guestbook위해 수정하다가 여기다 해야 할 게 아님을 알게 됨
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

			// 쿼리문
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

	// 회원 정보 1명 가져오기: 로그인 용_(세션 넣기 이전 과정'?이라고 썼는데 무슨 말?)
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
			
			
			// 쿼리문
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, id);
			pstmt.setString(2, password);

			// 실행
			rs = pstmt.executeQuery();

			// 4. 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name"); //'정우성 님 환영합니다'를 안 보여주려면 이 행 안 쓰면 돼
				//String id = rs.getString("id"); //회원정보 수정하고도 세션에 유지될 정보가 이름이 아니라 id이기 위해 위 가리고 이 행 생성
				
				userVo = new UserVo();
				userVo.setNo(no);  //두 행의 세터 통해 정보 넣어줘
				userVo.setName(name); //여기 불확실. password가 필요한 정보였어서 이렇게 썼는데 Dao는 위 query받을 거니까 name인가봐
				//userVo.setId(id);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return userVo;
	}

	//modify: 회원 정보 1개 가져와서 수정하기
	public UserVo getUser(int no) {

		UserVo userVo = null;
		
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " select no, ";
			query += "        name ";
			query += " from users ";
			query += " where no = ? ";			
			
			//문자열을 쿼리문으로
			System.out.println(query);
						
			// 쿼리문
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeUpdate();

			// 4. 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name"); //'정우성 님 환영합니다'를 안 보여주려면 이 행 안 쓰면 돼
				//String id = rs.getString("id"); //회원정보 수정하고도 세션에 유지될 정보가 이름이 아니라 id이기 위해 위 가리고 이 행 생성
				
				userVo = new UserVo();
				userVo.setPassword(password);  //두 행의 세터 통해 정보 넣어줘
				userVo.setName(); //여기 불확실. password가 필요한 정보였어서 이렇게 썼는데 Dao는 위 query받을 거니까 name인가봐
				//userVo.setId(id);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		return userVo;
	}
	
	
}
