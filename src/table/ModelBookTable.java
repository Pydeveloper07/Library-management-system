package table;

public class ModelBookTable {

    String isbn, category, author, descrip, title, quantity,available;

    public ModelBookTable(String isbn, String category, String author, String descrip, String title, String quantity, String available) {
        this.isbn = isbn;
        this.category = category;
        this.author = author;
        this.descrip = descrip;
        this.title = title;
        this.quantity = quantity;
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory(String category){this.category = category;}

    public String getCategory(){return this.category;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAvailable() {
        return available;
    }
}
