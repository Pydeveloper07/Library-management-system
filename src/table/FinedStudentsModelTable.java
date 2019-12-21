package table;

public class FinedStudentsModelTable {

    String id, due_date, book_id, charge_amount;

    public FinedStudentsModelTable(String id, String book_id, String due_date, String charge_amount) {
        this.id = id;
        this.book_id = book_id;
        this.due_date = due_date;
        this.charge_amount = charge_amount;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return book_id;
    }

    public String getDue_date() {return due_date;}

    public String getCharge_amount() {
        return charge_amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public void setCharge_amount(String charge_amount) {
        this.charge_amount = charge_amount;
    }
}
