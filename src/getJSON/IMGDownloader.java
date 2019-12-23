package getJSON;

import form.addBooksForm.BooksFormController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class IMGDownloader extends Thread {
    private String isbn;
    private String imageLink;
    private BooksFormController bookForm;
    IMGDownloader () {}
    IMGDownloader (String isbn, String imageLink, BooksFormController currentBookAdder) {
        this.isbn = isbn;
        this.imageLink = imageLink;
        this.bookForm = currentBookAdder;
    }
    public void run() {
        try {
            URL url = new URL(imageLink);
            String destination = "./src/images/bookPhoto/" + isbn + ".jpg";

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destination);

            byte[] b = new byte[2048]; //Buffer
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (Exception e) {
            bookForm.setPhoto("images/demoBook.png");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Downloading Error");
            alert.setHeaderText("Failed to download");
            alert.setContentText("Please try again or insert manually");
            alert.show();
        }
        try {
            bookForm.setPhoto("images/bookPhoto/" + isbn + ".jpg");
        }
        catch (Exception e) {
            bookForm.setPhoto("images/demoBook.png");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Downloading Error");
            alert.setHeaderText("Failed to set Photo");
            alert.setContentText("Please try again.");
            alert.show();
        }
    }
}