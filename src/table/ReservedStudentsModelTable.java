package table;

public class ReservedStudentsModelTable {

    String id, isbn, reservation_date, waiting_until;

    public ReservedStudentsModelTable(String id, String isbn, String reservation_date, String waiting_until) {
        this.id = id;
        this.isbn = isbn;
        this.reservation_date = reservation_date;
        this.waiting_until = waiting_until;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getReservation_date() {return reservation_date;}

    public String getWaiting_until() {
        return waiting_until;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public void setWaiting_until(String waiting_until) {
        this.waiting_until = waiting_until;
    }
}
