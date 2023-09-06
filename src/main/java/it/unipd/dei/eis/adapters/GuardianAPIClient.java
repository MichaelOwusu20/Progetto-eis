package it.unipd.dei.eis.adapters;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import it.unipd.dei.eis.Article;

public class GuardianAPIClient extends Adapter {

    private static final String API_KEY = "87ec1552-3962-48d7-9f7a-3b22f366781c";
    private static final String API_URL = "https://content.guardianapis.com/search?api-key=" + API_KEY;
    private static final String query = "nuclear-power";
    private static final int totalPages = 20;
    private final String[] responseArray = new String[20];

    public String getResponseArray(int i){ return responseArray[i]; }
    public int getTotalPages() { return totalPages; }

    public GuardianAPIClient() {
        filePath = "./Files/The Guardian/";
    }

    public void downloadTheGuardian(){

        loadResponseArray();
        makeDirectory();

        for(int i=1; i<= responseArray.length; i++)
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "theguardian_article_" + i + ".json"))) {
                writer.write(responseArray[i-1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadArrayList() {

        for (String response : responseArray) {

            try {

                // Creazione di un oggetto JSON dalla risposta completa
                JSONObject jsonResponse = new JSONObject(response);
                // Estrazione dell'oggetto "response" dall'oggetto JSON
                JSONObject responseJson = jsonResponse.getJSONObject("response");
                // Estrazione dell'array "results" dall'oggetto "response"
                JSONArray articles = responseJson.getJSONArray("results");

                // Iterazione attraverso gli articoli nell'array "results"
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);

                    // Estrazione del titolo e del corpo dell'articolo e stampa
                    String title = article.getString("webTitle");
                    String body = article.getJSONObject("fields").getString("bodyText");

                    //aggiunge titolo e body alla arraylist
                    articlesList.add(new Article(title, body));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                break; // Esci dal loop in caso di eccezione
            }
        }
    }

    private String makeApiRequest(int currentPage) {

        String apiResponse = "";
        String requestUrl = API_URL + "&page=" + currentPage + "&show-fields=bodyText&page-size=50&q=" + query;

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                // Lettura della risposta
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                }

                apiResponse = responseBuilder.toString();
            } else {
                System.err.println("Failed to fetch articles. HTTP Status: " + conn.getResponseCode());
                System.out.println(requestUrl);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiResponse;
    }


    private void loadResponseArray()
    {
        for(int i=1; i<=totalPages; i++)
            responseArray[i-1] = makeApiRequest(i);
    }

    private void makeDirectory() {
        File folder = new File(filePath);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                System.out.println("Impossibile creare la cartella.");
            }
        }
    }
}