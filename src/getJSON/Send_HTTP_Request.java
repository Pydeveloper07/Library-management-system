import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Send_HTTP_Request {
    public static void main(String[] args) {
        try {
            Send_HTTP_Request.call_me();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call_me() throws Exception {
        Scanner input = new Scanner(System.in);

//        System.out.println("Enter ISBN");
//        String ISBN = input.nextLine();
//        String ISBN = "9781449691721";
        String ISBN = "1449691722";

        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response\n");

        boolean found = myResponse.getInt("totalItems") > 0;
//        String reqError = myResponse.getString("error");
//        System.out.println(reqError);
        if (found) {
            JSONObject item = myResponse.getJSONArray("items").getJSONObject(0);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                    JSONArray authorsJSON = volumeInfo.getJSONArray("authors");
                    JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    JSONArray categoriesJSON = volumeInfo.getJSONArray("categories");

            // Get Title
            String title = volumeInfo.getString("title");
            // Get Authors[]
            String[] authors = new String[authorsJSON.length()];
            for(int i=0; i < authors.length; i++) {
                authors[i] = authorsJSON.optString(i);
            }
            // Get Publisher
            String publisher = volumeInfo.getString("publisher");
            // Get Published Date
            String publishedDate = volumeInfo.getString("publishedDate").substring(0, 4);
            // Get Description
            String description = volumeInfo.getString("description");
            // Change ISBN to ISBN13 if possible
            for (int i = 0; i < industryIdentifiers.length(); i++) {
                JSONObject current = industryIdentifiers.getJSONObject(i);
                if (current.getString("type").equals("ISBN_13")) {
                    ISBN = current.getString("identifier");
                }
            }
            // Get Page Count
            int pageCount = volumeInfo.getInt("pageCount");
            // Get Categories
            String[] categories = new String[categoriesJSON.length()];
            for(int i=0; i < categories.length; i++) {
                categories[i] = categoriesJSON.optString(i);
            }
            // Get Book Cover Photo
            String imageLink = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            // Get Language
            String language = volumeInfo.getString("language").toUpperCase();
            // Get Info Link
            String infoLink = volumeInfo.getString("infoLink");

            System.out.println(title);
            for(int i=0; i < authors.length; i++) {
                System.out.println(authors[i]);
            }
            System.out.println(publisher);
            System.out.println(publishedDate);
            System.out.println(description);
            System.out.println(ISBN);
            System.out.println(pageCount);
            for(int i=0; i < categories.length; i++) {
                System.out.println(categories[i]);
            }
            System.out.println(imageLink);
            System.out.println(language);
            System.out.println(infoLink);

            saveImage(imageLink, ISBN);
            System.out.println("Picture was downloaded");
        }
        else {
            System.out.println("We couldn't find any book on your ISBN, Please Try again");
        }
    }
    public static void saveImage(String imageUrl, String ISBN) throws IOException {
        URL url = new URL(imageUrl);
        String destination = "./Book_Covers/" + ISBN + ".jpg";

        System.out.println("Start downloading");
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

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Finish downloading in " + timeElapsed + "ms");
    }
}