package books;

import java.util.Scanner;

public class Admin {
	
	public static void adminMain() {
		Scanner scan = new Scanner(System.in);
		
		loop:while(true) {
			System.out.println("╔════════════════ ∘◦ ✾ ◦∘ ════════════════╗");
			System.out.println(" 1.관리자 도서관리 페이지 2. 대출/반납 페이지 3. 나가기");
			System.out.println("╚════════════════ ∘◦ ❈ ◦∘ ════════════════╝");
			System.out.print("페이지를 선택> ");
			int input = scan.nextInt();
			scan.nextLine();
			
			switch(input) {
			case 1:
				bookManagementPage();
				break;
			case 2:
				Human.memberPage();
				break;
			case 3:
				System.out.println("관리자 모드를 종료합니다.");
				break loop;
			}
		}
	}

	public static void bookManagementPage() {
		//스캐너 생성
		Scanner scan = new Scanner(System.in);
		BookDAO bookDAO = new BookDAO();

		loop:while(true) {
		
			try {
				//관리자 초기 화면 보여주기
				System.out.println("╔═════════ ∘◦ ✾ ◦∘ ═════════╗");
				System.out.println(" 1. 도서추가 2. 도서삭제 3. 나가기");
				System.out.println("╚═════════ ∘◦ ❈ ◦∘ ═════════╝");

				//입력번호받기
				System.out.print("입력> ");
				int menu = scan.nextInt();
				scan.nextLine();

				switch(menu) {
				case 1:
					System.out.println("추가할 책 정보를 입력해주세요.");
					
					System.out.print("제목 >");
					String title = scan.nextLine();
					
					System.out.print("출판사 >");
					String publisher = scan.nextLine();
					
					System.out.print("저자 >");
					String auth = scan.nextLine();
					
					System.out.print("출판일 >");
					String pub_date = scan.nextLine();
					
					int result_insert = bookDAO.insertBook(title, publisher, auth, pub_date);
					if(result_insert == 1) {
						System.out.println("도서가 추가되었습니다.");
					}else {
						System.out.println("도서 추가에 실패하였습니다.");
					}

					break;
					
				case 2:
					System.out.println("삭제할 책 이름을 입력해주세요.");
					
					System.out.print(">");
					String del_title = scan.nextLine();
					
					int result_delete = bookDAO.deleteBook(del_title);
					if(result_delete == 1) {
						System.out.println("도서가 삭제되었습니다.");
					}else {
						System.out.println("도서 삭제에 실패하였습니다.");
					}
					
					break;
					
				case 3:
					System.out.println("관리자 시스템을 종료합니다.");
					break loop;
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
