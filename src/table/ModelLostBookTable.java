package table;

public class ModelLostBookTable {

    String book_id, isbn, student_id, lost_date;

    public ModelLostBookTable(String book_id, String isbn, String student_id, String lost_date) {
        this.book_id = book_id;
        this.isbn = isbn;
        this.student_id = student_id;
        this.lost_date = lost_date;
    }
    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setStudent_id(String student_id){this.student_id = student_id;}

    public String getStudent_id(){return this.student_id;}

    public String getLost_date() {
        return lost_date;
    }

    public void setLost_date(String lost_date) {this.lost_date = lost_date;}
}
