package books;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class BookDAO {
	
	//멤버변수
	private String url = Main.URL;
	private String uid = Main.UID;
	private String upw = Main.UPW;
	
	/*
	 * 도서 추가
	 */
	public int insertBook(String title, String publisher, String auth, String pub_date) {
		int result = 0;
		
		String sql = "insert into books values(book_seq.nextval, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, publisher);
			pstmt.setString(3, auth);
			pstmt.setString(4, pub_date);
			
			result = pstmt.executeUpdate();
			
			return result;
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/*
	 * 도서 삭제
	 */
	public int deleteBook(String title) {
		
		//일단 삭제에 입력한 도서명을 검색하여 보여준다.
		ArrayList<BookVO> find_booklist = findBook(title);
		if(find_booklist.size() == 0) {
			System.out.println("검색된 도서가 없습니다.");
			return 0;
		}
		
		for(BookVO list : find_booklist) {
			System.out.println(list.toString());
		}
		
		Scanner scan = new Scanner(System.in);
		System.out.println("삭제할 도서 번호를 입력해주세요> ");
		int delete_no = scan.nextInt();
		scan.nextLine();
		
		//삭제 결과
		int result = 0;
		
		String sql = "delete from books where book_no = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setInt(1, delete_no);
			
			result = pstmt.executeUpdate();
			
			return result;
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/*
	 * 도서 검색 - 키워드로 검색(타이틀, 저자, 출판사 모두 검색)
	 */
	public ArrayList<BookVO> findBook(String keyword) {
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
	
	/*
	 * 도서 번호로 검색 - 도서 제목을 return
	 */
	public String findBookNumber(int book_no) {
		
		String book_title = "";
		//검색은 키워드 단위로 검색하게 만들어 보았다.
		String sql = "select * from books where book_no = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, book_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				book_title = rs.getString("title");
			}
			return book_title;
			
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
		return book_title;
	}
	
}
