package bookInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookInfoController {
    static String isbn;
    static String title;
    static String description;
    static String spinner;
    static String publishedYear;
    static String category;
    static String author;

    @FXML
    Label isbnF;
    @FXML
    Label titleF;
    @FXML
    TextArea descriptionF;
    @FXML
    ImageView photoF;
    @FXML
    Label spinnerF;
    @FXML
    Label publishedYearF;
    @FXML
    Label categoryF;
    @FXML
    Label authorNameF;
    public void initialize() {
        descriptionF.setText(author);
        titleF.setText(title);
        publishedYearF.setText(publishedYear);
        categoryF.setText(category);
        authorNameF.setText(author);
        spinnerF.setText(spinner);
        photoF.setImage(new Image("images/bookPhoto/" + isbn + ".jpg"));
        isbnF.setText(isbn);
    }
    public static void setIsbn(String txt) {
        isbn = txt;
    }
    public static void setTitle(String txt) {
        title = txt;
    }
    public static void setDescription(String txt) {
        description = txt;
    }
    public static void setPublishedYear(String txt) {
        publishedYear = txt;
    }
    public static void setCategory(String txt) {
        category = txt;
    }
    public static void setAuthor(String txt) {
        author = txt;
    }
    public static void setSpinner(String txt) {
        spinner = txt;
    }

//    @FXML
//    public void okButtonHandler(){
//        ((Stage)isbnF.getScene().getWindow()).close();
//    }
}
