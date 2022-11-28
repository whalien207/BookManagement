package books;

public class BookRentalVO {

	private int rental_no;
	private String id;
	private int book_no;
	private String rental_date;
	private String check_return_date;
	private String return_date;
	private String state;


	public BookRentalVO() {
	}

	public BookRentalVO(int rental_no, String id, int book_no, String rental_date, String return_date, String check_return_date) {
		this.rental_no = rental_no;
		this.id = id;
		this.book_no = book_no;
		this.rental_date = rental_date;
		this.return_date = return_date;
		this.check_return_date = check_return_date;
	}

	public int getRental_no() {
		return rental_no;
	}

	public void setRental_no(int rental_no) {
		this.rental_no = rental_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBook_no() {
		return book_no;
	}

	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}

	public String getRental_date() {
		return rental_date;
	}

	public void setRental_date(String rental_date) {
		this.rental_date = rental_date;
	}

	public String getCheck_return_date() {
		return check_return_date;
	}

	public void setCheck_return_date(String check_return_date) {
		this.check_return_date = check_return_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	@Override
	public String toString() {
		return "BookRentalVO [rental_no=" + rental_no + ", id=" + id + ", book_no=" + book_no + ", rental_date="
				+ rental_date + ", check_return_date=" + check_return_date + ", return_date=" + return_date + "]";
	}

	//책 title과 대출일과 반납일만 보여주고 싶음 rownum을 붙여서
	public String toString_RentalInfo() {
		String processed_rentaldate = rental_date.split(" ")[0];
		String processed_returndate = return_date.split(" ")[0];
		return "도서 번호: "+book_no+" | 대출일: "+processed_rentaldate+" | 반납일/반납예정일: "+processed_returndate+" | 상태: "+check_return_date;
	}

	//대출중인 책 정보만 보여주기 - 반납시 사용
	public String toString_UsingRentalInfo() {
		BookDAO bookdao = new BookDAO();
		String processed_checkreturndate = check_return_date.split(" ")[0];
		return "도서 번호: "+book_no+" | 책 제목: "+bookdao.findBookNumber(book_no)+" | 반납예정일: "+processed_checkreturndate;
	}

}
