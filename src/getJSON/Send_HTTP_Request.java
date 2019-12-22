package getJSON;

import form.addBooksForm.BooksFormController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Send_HTTP_Request {
    private static String title, publisher, publishedDate, description, category;
    private static String[] authors;
    private static String language, infoLink;
    private static int pageCount;
    private static String imageLink, isbn;

    public void call_me(String isbn, BooksFormController currentBookAdder) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog ✅");
        alert.setContentText("I have a great message for you!");
        alert.getButtonTypes().clear();

        alert.showAndWait();

        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());

        boolean found = myResponse.getInt("totalItems") > 0;
        if (found) {
            JSONObject item = myResponse.getJSONArray("items").getJSONObject(0);
            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
            JSONArray authorsJSON = volumeInfo.getJSONArray("authors");
            JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
            JSONArray categoriesJSON = volumeInfo.getJSONArray("categories");

            // Get Title
            title = volumeInfo.getString("title");
            // Get Authors[]
            authors = new String[authorsJSON.length()];
            for(int i=0; i < authors.length; i++) {
                authors[i] = authorsJSON.optString(i);
            }
            // Get Publisher
            publisher = volumeInfo.getString("publisher");
            // Get Published Date
            publishedDate = volumeInfo.getString("publishedDate").substring(0, 4);
            // Get Description
            description = volumeInfo.getString("description");
            // Change ISBN to ISBN13 if possible
            for (int i = 0; i < industryIdentifiers.length(); i++) {
                JSONObject current = industryIdentifiers.getJSONObject(i);
                if (current.getString("type").equals("ISBN_13")) {
                    isbn = current.getString("identifier");
                }
            }
            // Get Page Count
            pageCount = volumeInfo.getInt("pageCount");
            // Get Categories
            category = categoriesJSON.optString(0);
            // Get Book Cover Photo
            imageLink = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            // Get Language
            language = volumeInfo.getString("language").toUpperCase();
            // Get Info Link
            infoLink = volumeInfo.getString("infoLink");

            IMGDownloader object = new IMGDownloader(currentBookAdder);
            object.start();
            System.out.println("Program finished");
            currentBookAdder.setTitle(title);
            currentBookAdder.setPublishedYear(publishedDate);
            currentBookAdder.setDescription(description);
            for (int i = 0; i < authors.length; i++) {
//                currentBookAdder.pressAdd(authors[i]);
            }
        }
        else {
            System.out.println("We couldn't find any book on your ISBN, Please Try again");
        }
    }
//
//    public static void saveImage(String imageUrl, String isbn) throws IOException {
//        URL url = new URL(imageUrl);
//
//        System.out.println("Start downloading");
//        long start = System.currentTimeMillis();
//
//        InputStream is = url.openStream();
//        OutputStream os = new FileOutputStream("./src/images/bookPhoto/" + isbn + ".jpg");
//
//        byte[] b = new byte[2048]; //Buffer
//        int length;
//
//        while ((length = is.read(b)) != -1) {
//            os.write(b, 0, length);
//        }
//
//        is.close();
//        os.close();
//
//        long finish = System.currentTimeMillis();
//        long timeElapsed = finish - start;
//        System.out.println("Finish downloading in " + timeElapsed + "ms");
//    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String[] getAuthors() {
        return authors;
    }
}