package books;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	//멤버변수(필드??)
	private String url = Main.URL; 
	private String uid = Main.UID;
	private String upw = Main.UPW;


	//가입할 때 id만 비교해주는 메소드(중복검사) -return값 0,1
	public int compareId (String id) {

		//main으로 넘어갈 변수
		int result = 0;

		String id_sql = "select * from book_user where id like ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(id_sql);
			//?값 세팅
			pstmt.setString(1,  id);         
			//db로 날림
			rs = pstmt.executeQuery();

			//중복검사
			if(rs.next()) {
				result = 0;
			} else {
				result = 1;
			}

		} catch(Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			}catch(Exception e) {
				System.out.println("close에러");
			}
		}

		return result;
	}


	//insert해주는 거 필요(회원가입)
	public int insertInfo(String id, String pw) {
		int result = 0;

		String insert_sql = "insert into book_user values(?,?,0)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(insert_sql);

			//?에 입력받은 아이디,비번 입력
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			result = pstmt.executeUpdate();

		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();

			}catch(Exception e) {
				System.out.println("close에러");
			}
		}
		return result;
	}


	//로그인 시, id, pw 비교하는 메소드
	public int loginCompare (String id, String pw) {
		int result = 0;

		String select_sql = "select * from book_user where id in ? and pw in ?"; 

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(select_sql);
			
			//?값
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			//id랑pw가 있으면 0 없으면 1 return 
			if(rs.next()) {
				return rs.getInt("user_level");
			} else {
				result = 1;
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch(Exception e) {
				System.out.println("close에 실패했습니다");
			}
		}
		return result;
	}
	
	//관리자 계정으로 업데이트
	public int updateAdminUser(String user) {
		int result = 0;
		String select_sql = "update book_user set user_level = 777 where Id = ?"; 

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(select_sql);
			
			pstmt.setString(1, user);

			result = pstmt.executeUpdate();
			return result;

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch(Exception e) {
				System.out.println("close에 실패했습니다");
			}
		}
		
		return result;
	}
}