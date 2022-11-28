package books;

import java.util.ArrayList;
import java.util.Scanner;

public class Human {

	public static void memberPage() {
		//스캐너 생성
		Scanner scan = new Scanner(System.in);
		BookDAO bookDAO = new BookDAO();
		BookRentalDAO rentalDAO = new BookRentalDAO();

		loop:while(true) {

			try {
				//관리자 초기 화면 보여주기
				System.out.println("╔══════════════ ∘◦ ✾ ◦∘ ══════════════╗");
				System.out.println(" 1. 도서대출 2. 도서반납 3. 대출기록조회 4.나가기");
				System.out.println("╚══════════════ ∘◦ ❈ ◦∘ ══════════════╝");

				//입력번호받기
				System.out.print("입력> ");
				int menu = scan.nextInt();
				scan.nextLine();

				switch(menu) {
				case 1:
					System.out.println("대출할 도서명을 찾으세요>");
					String ren_title = scan.nextLine();
					ArrayList<BookVO> search_booklist = rentalDAO.searchBook(ren_title);
					if(search_booklist.size() == 0) {
						System.out.println("검색된 도서가 없습니다.");
						break;
					}
					//도서정보 출력
					for(BookVO list : search_booklist) {
						System.out.println(list.toString());
					}

					System.out.print("대출할 도서번호를 입력하세요>");
					int rentalBook_no = scan.nextInt();
					scan.nextLine(); 

					int check_compare = rentalDAO.compareBook(rentalBook_no);

					//대출가능 도서 체크
					if(check_compare == 0) {
						System.out.println("선택하신 도서는 대출중인 도서입니다.");
						System.out.println("다른 도서를 선택해주세요ㅠㅜ");
						break;
					}
					int result = rentalDAO.rental(rentalBook_no);

					if(result == 1) {
						System.out.println("선택하신 도서를 대출하였습니다.");
						System.out.println("반납일은 "+ rentalDAO.checkingReturnDate() +"입니다.");
					}else {
						System.out.println("도서 대출에 실패하였습니다.");
					}

					break;

				case 2:
					ArrayList<BookRentalVO> usingRentalList = rentalDAO.usingRentalInfo();
					if(usingRentalList.size() == 0) {
						System.out.println("반납할 도서가 없습니다.");
						break;
					}
					
					for(BookRentalVO vo : usingRentalList) {
						System.out.println(vo.toString_UsingRentalInfo());
					}
					
					System.out.println("반납할 도서의 번호를 선택해 주세요.");
					System.out.print("> ");

					int return_book = scan.nextInt();
					scan.nextLine();

					int update_result = rentalDAO.updateReturnDate(return_book);
					if(update_result == 1) {
						System.out.println("반납되었습니다.");
					}else {
						System.out.println("반납에 실패하였습니다. 다시 시도해 주세요.");
					}

					break;

				case 3:
					System.out.println(Main.ID+"님의 대출기록 정보입니다.");
					ArrayList<BookRentalVO> rentalInfo_list = rentalDAO.rentalInfo();
					if(rentalInfo_list.size() == 0) {
						System.out.println("대출 기록 정보가 없습니다.");
						break;
					}

					for(BookRentalVO vo : rentalInfo_list) {
						System.out.println(vo.toString_RentalInfo());
					}

					break;
				case 4:
					break loop;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}