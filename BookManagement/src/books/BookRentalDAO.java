package books;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRentalDAO {

	//멤버변수(필드??)
	private String url = Main.URL; 
	private String uid = Main.UID;
	private String upw = Main.UPW;

	//도서대출
	public int rental(int rentalBook_no){

		//대출 결과
		int result = 0;

		String sql = "insert into rental_info values(rental_seq.nextval, ?, ?, sysdate, null, (sysdate+14))";
		Connection conn = null;
		PreparedStatement pstmt = null;


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Main.ID);
			pstmt.setInt(2, rentalBook_no);

			result = pstmt.executeUpdate();

			return result;

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/*
	 * 반납일 알려주기
	 */
	public String checkingReturnDate() {

		String sql = "select sysdate+14 as returnDate from dual";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String returnDate = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				returnDate = rs.getString("returnDate");
			}
			return returnDate.split(" ")[0];

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return returnDate;
	}


	//도서검색
	public ArrayList<BookVO> searchBook (String keyword){
		ArrayList<BookVO> list = new ArrayList<>();
		//검색은 키워드 단위로 검색하게 만들어 보았다.
		String sql = "select * from books where title like ? or publisher like ? or auth like ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);

			//title앞뒤로 %를 붙여줬다. (키워드만 들어가면 모두 검색)
			keyword = "%"+keyword+"%";
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int book_no = rs.getInt("book_no");
				String find_title = rs.getString("title");
				String publisher = rs.getString("publisher");
				String auth = rs.getString("auth");
				String pub_date = rs.getString("pub_date");

				BookVO vo = new BookVO(book_no, find_title, publisher, auth, pub_date);
				list.add(vo);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list; 
	}

	//대출여부확인
	//가입할 때 id만 비교해주는 메소드(중복검사) -return값 0,1
	public int compareBook (int book_no) {

		//main으로 넘어갈 변수
		int result = 0;

		String id_sql = "select * from rental_info where book_no = ? and return_date is null";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(id_sql);   

			pstmt.setInt(1, book_no);
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


	/*
	 * 반납하기 - return date update 시켜주기
	 */
	public int updateReturnDate(int rentalbook_no) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String rentalInfo_sql = "select * from rental_info where id = ? and book_no = ? and return_date is null";
		String update_sql = "update rental_info set return_date = sysdate where rental_no = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(rentalInfo_sql);
			pstmt.setString(1, Main.ID);
			pstmt.setInt(2, rentalbook_no);

			rs = pstmt.executeQuery();
			int rental_no = -1;
			while(rs.next()) {
				rental_no = rs.getInt("rental_no");
			}

			if(rental_no == -1) {
				return 0;
			}

			pstmt = conn.prepareStatement(update_sql);
			pstmt.setInt(1, rental_no);

			result = pstmt.executeUpdate();
			return result;


		} catch(Exception e){
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

	/*
	 * 대출중인 책 정보만 보여주기.
	 */
	public ArrayList<BookRentalVO> usingRentalInfo(){
		ArrayList<BookRentalVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String rentalInfo_sql = "select * from rental_info where id = ? and return_date is null";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);

			pstmt = conn.prepareStatement(rentalInfo_sql);
			pstmt.setString(1, Main.ID);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int rental_no = rs.getInt("rental_no");
				String rental_id = rs.getString("id");
				int book_no = rs.getInt("book_no");
				String rental_date = rs.getString("rental_date");
				String return_date = rs.getString("return_date");
				String check_return_date = rs.getString("check_return_date");

				BookRentalVO vo = new BookRentalVO(rental_no, rental_id, book_no, rental_date, return_date, check_return_date);
				list.add(vo);
			}

		} catch(Exception e){
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
		return list;
	}

	/*
	 * 모든 대출 정보 조회 - 반납완료, 대출중
	 */
	public ArrayList<BookRentalVO> rentalInfo() {
		ArrayList<BookRentalVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from rental_info where id = ?";

		String return_date_colum = "case when return_date is null then check_return_date \n else return_date \n end as return_date";
		String state_colum = "case when return_date is null then '대출중' \n else '반납완료' \n end as state";
		String rentalInfo_sql = "select rental_no, id, book_no, rental_date, "+return_date_colum+", "+state_colum+" from rental_info where id = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Main.ID);
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				return list;
			}

			pstmt = conn.prepareStatement(rentalInfo_sql);
			pstmt.setString(1, Main.ID);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				int rental_no = rs.getInt("rental_no");
				String rental_id = rs.getString("id");
				int book_no = rs.getInt("book_no");
				String rental_date = rs.getString("rental_date");
				String return_date = rs.getString("return_date");
				String state = rs.getString("state");

				BookRentalVO vo = new BookRentalVO(rental_no, rental_id, book_no, rental_date, return_date, state);
				list.add(vo);
			}

		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
