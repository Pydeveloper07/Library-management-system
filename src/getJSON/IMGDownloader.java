package getJSON;

import form.addBooksForm.BooksFormController;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class IMGDownloader extends Thread {
    private String isbn;
    private String imageLink;
    private BooksFormController bookForm;
    IMGDownloader (String isbn, String imageLink, BooksFormController currentBookAdder) {
        this.isbn = isbn;
        this.imageLink = imageLink;
        this.bookForm = currentBookAdder;
    }
    public void run() {
        try {
            URL url = new URL(imageLink);
            String destination = "./Book_Covers/" + isbn + ".jpg";

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destination);

            byte[] b = new byte[2048]; //Buffer
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();

            bookForm.setPhoto("file:../../Book_Covers/" + isbn + ".jpg");
        } catch (Exception e) {
        }
    }
}