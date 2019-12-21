package table;

public class ModelIssuedBookTable {

    String isbn, student_id, issued_date, due_date;

    public ModelIssuedBookTable(String isbn, String student_id, String issued_date, String due_date) {
        this.isbn = isbn;
        this.student_id = student_id;
        this.issued_date = issued_date;
        this.due_date = due_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setStudent_id(String student_id){this.student_id = student_id;}

    public String getStudent_id(){return this.student_id;}

    public String getIssued_date() {
        return issued_date;
    }

    public void setIssued_date(String issued_date) {this.issued_date = issued_date;}

    public void setDue_date(String due_date) {this.due_date = due_date;}

    public String getDue_date() {
        return due_date;
    }
}
