package books;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static final String URL = "jdbc:oracle:thin:@172.30.1.9:1521:xe";
	public static final String UID = "newbook";
	public static final String UPW = "newbook";
	public static String ID;

	public static void main(String[] args) {
		//스캐너 생성
		Scanner scan = new Scanner(System.in);
		UserDAO userDAO = new UserDAO();

		loop:while(true) {

			try {
				//초기 화면 보여주기
				System.out.println("╔═════════════ ∘◦ ✾ ◦∘ ═════════════╗");
				System.out.println(" 1. 로그인 2. 회원가입 3. 도서검색 4. 나가기");
				System.out.println("╚═════════════ ∘◦ ❈ ◦∘ ═════════════╝");

				//입력번호받기
				System.out.print("입력> ");
				int menu = scan.nextInt();
				scan.nextLine();

				switch(menu) {
				case 1:
					//로그인
					System.out.println("아이디와 비번을 입력하시오.");
					System.out.print("아이디 >");
					String loginId = scan.nextLine();
					System.out.print("비번 >");
					String loginPw = scan.nextLine();

					int login = userDAO.loginCompare(loginId, loginPw);

					if(login == 0) {
						//일반회원
						ID = loginId;
						System.out.println( loginId + "님 환영합니다^*^.");
						Human.memberPage();
						
					} else if(login == 777){
						//관리자모드
						ID = loginId;
						System.out.println(loginId + "님 환영합니다^*^.");
						Admin.adminMain();
					} else {
						System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
						continue;
					}
					
					break;
				
				case 2:
					//회원가입 
					System.out.println("아이디와 비번을 입력하시오.");
					System.out.print("아이디 >");
					String id = scan.nextLine();

					//아이디 중복 확인 메서드 실행
					int compareId = userDAO.compareId(id);
					if(compareId == 0) {
						System.out.println("이미 존재하는 아이디입니다");
						continue;
					} 

					//중복검사 통과(회원가입)
					System.out.print("비번 >");
					String pw = scan.nextLine();

					int sign_up = userDAO.insertInfo(id, pw);

					if(sign_up == 1) {
						System.out.println("회원가입되었습니다");

					} else {
						System.out.println("회원가입에 실패했습니다");
					}
					break;
				
				case 3:
					System.out.println("검색할 키워드를 입력해주세요");
					System.out.print(">");
					String keyword = scan.nextLine();

					BookDAO bookDAO = new BookDAO();
					ArrayList<BookVO> find_booklist = bookDAO.findBook(keyword);
					if(find_booklist.size() == 0) {
						System.out.println("검색된 도서가 없습니다.");
					}

					for(BookVO vo : find_booklist) {
						System.out.println(vo.toString());
					}

					break;
				
				case 4:
					break;
					
				case 5:
					System.out.println("시스템을 종료합니다.");
					break loop;
				
				case 777:
					System.out.println("시스템 계정 입장 - 관리자로 변경할 ID를 입력하세요.");
					System.out.print(">");
					String admin_id = scan.nextLine();
					
					int result = userDAO.updateAdminUser(admin_id);
					if(result == 1) {
						System.out.println(admin_id + "님이 관리자로 변경되었습니다.");
					}else {
						System.out.println("관리자 변경에 실패하였습니다.");
					}
				}

			}catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
