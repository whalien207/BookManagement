package books;

public class BookVO {
	
	private int book_no;
	private String title;
	private String publisher;
	private String auth;
	private String pub_date; //이거 출판일... 날짜형인데 String으로 가져와도 될지??
	
	//기본 생성자
	public BookVO() {
	}

	public BookVO(String title, String publisher, String auth, String pub_date) {
		this.title = title;
		this.publisher = publisher;
		this.auth = auth;
		this.pub_date = pub_date;
	}
	
	//모든 멤버변수를 저장하는 생성자
	public BookVO(int book_no, String title, String publisher, String auth, String pub_date) {
		this.book_no = book_no;
		this.title = title;
		this.publisher = publisher;
		this.auth = auth;
		this.pub_date = pub_date;
	}

	//setter, getter
	public int getBook_no() {
		return book_no;
	}

	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getPub_date() {
		return pub_date;
	}

	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}

	@Override
	public String toString() {
		String processed_date = pub_date.split(" ")[0];
		return "번호 : " + book_no + " | 도서 제목 : " + title + " | 출판사 : " + publisher + " | 저자 : " + auth + " | 출판일 : " + processed_date;
	}

	

}
