package books;

public class UserVO {

   private String id;
   private String pw;
   private String level;
   
   //기본생성자
   public UserVO() {
      
   }

   //생성자
   public UserVO(String id, String pw, String level) {
      super();
      this.id = id;
      this.pw = pw;
      this.level = level;
   }
   
   //toString 
   @Override
   public String toString() {
      return "UserVO [id=" + id + ", =" + pw + ", 등급=" + level + "]";
   }

   
   //getter, setter
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPw() {
      return pw;
   }

   public void setPw(String pw) {
      this.pw = pw;
   }

   public String getLevel() {
      return level;
   }

   public void setLevel(String level) {
      this.level = level;
   }
   
}
