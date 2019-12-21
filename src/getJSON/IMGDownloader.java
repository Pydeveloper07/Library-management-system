package getJSON;

import form.addBooksForm.BooksFormController;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class IMGDownloader extends Thread {
    private BooksFormController bookForm;
    IMGDownloader (BooksFormController currentBookAdder) {
        this.bookForm = currentBookAdder;
    }
    public void run() {
        String imageLink = "http://books.google.com/books/content?id=dpBMLwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api";
        String isbn = "9781449691721";

        try {
            URL url = new URL(imageLink);
            String destination = "./Book_Covers/" + isbn + ".jpg";

            long start = System.currentTimeMillis();

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destination);

            byte[] b = new byte[2048]; //Buffer
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
            System.out.println("Picture was downloaded");
//            bookForm.setPhoto("http://books.google.com/books/content?id=dpBMLwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
            bookForm.setPhoto("file:../../Book_Covers/" + isbn + ".jpg");
        } catch (Exception e) {

        }
    }
}