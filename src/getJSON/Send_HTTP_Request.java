package getJSON;

import form.addBooksForm.BooksFormController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import org.json.JSONArray;
import org.json.JSONException;
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
        isbn = isbn.replace("-","");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Loading...");
        alert.setHeaderText("Loading Book Details");
        alert.setContentText("");
        alert.getButtonTypes().remove(ButtonType.OK);
        Window alertWindow = alert.getDialogPane().getScene().getWindow();
        alertWindow.setOnCloseRequest(event -> alertWindow.hide());
        alert.show();
        if (isbn.length() < 10) {
            alert.setContentText("Type correct ISBN and try again!");
            return;
        }
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
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

        boolean found = responseCode < 400 && myResponse.getInt("totalItems") > 0;
        if (found) {
            JSONObject item = myResponse.getJSONArray("items").getJSONObject(0);
            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
            JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");

            // Get Title
            title = volumeInfo.getString("title");
            if (title.length() > 30) title = title.substring(0, 27).concat("...");
            // Get Authors[]
            String author = "";
            try {
                JSONArray authorsJSON = volumeInfo.getJSONArray("authors");
                authors = new String[authorsJSON.length()];

                for(int i=0; i < authors.length; i++) {
                    authors[i] = authorsJSON.optString(i);
                }
                author = authors[0];
                for (int i = 1; i < authors.length; i++) author = author + (", " + authors[i]);
            }
            catch (Exception e) {
                author = "No authors";
            }
            // Get Publisher
            try{
                publisher = volumeInfo.getString("publisher");
            }
            catch(JSONException ex){
                publisher = "Unknown";
            }
            // Get Published Date
            try{
                publishedDate = volumeInfo.getString("publishedDate");
            }
            catch(JSONException ex){
                publishedDate = "2000-01-01";
            }
            // Get Description
            try{
                description = volumeInfo.getString("description");
            }
            catch(JSONException ex){
                description = "No available Description";
            }
            if (description.length() > 300) description = description.substring(0, 296).concat("...");
            // Change ISBN to ISBN13 if possible
            for (int i = 0; i < industryIdentifiers.length(); i++) {
                JSONObject current = industryIdentifiers.getJSONObject(i);
                if (current.getString("type").equals("ISBN_13")) {
                    isbn = current.getString("identifier");
                }
            }
            // Get Categories
            try {
                JSONArray categoriesJSON = volumeInfo.getJSONArray("categories");
                category = categoriesJSON.optString(0);
                if (category.length() > 30) category = category.substring(0, 26).concat("...");
            }
            catch (Exception e) {
                category = "No categories";
            }
            // Get Book Cover Photo
            IMGDownloader object = new IMGDownloader();
            try {
                imageLink = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                object = new IMGDownloader(isbn, imageLink, currentBookAdder);
                object.start();
            }
            catch (Exception e) {
                alert.setContentText("No Photo Avaiable");
            }
            currentBookAdder.setIsbn(isbn);
            currentBookAdder.setTitle(title);
            currentBookAdder.setSubject(category);
            currentBookAdder.setPublishedYear(publishedDate);
            currentBookAdder.addAuthor(author);
            currentBookAdder.setDescription(description);
            while (object.isAlive());
            alertWindow.hide();
        }
        else {
            alert.setContentText("Book was not found. Try to enter correct ISBN -");
        }
    }
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