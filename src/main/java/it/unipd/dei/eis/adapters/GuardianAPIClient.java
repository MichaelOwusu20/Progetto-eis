package it.unipd.dei.eis.adapters;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import it.unipd.dei.eis.Article;

public class GuardianAPIClient extends Adapter {

    public GuardianAPIClient(){
        filePath = "./Files/The Guardian/";
    }

    private static final String API_KEY = "87ec1552-3962-48d7-9f7a-3b22f366781c";
    private static final String API_URL = "https://content.guardianapis.com/search?api-key=" + API_KEY;
    private static final String query = "nuclear-power";

    public void makeDirectory(){
        File folder = new File(filePath);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                System.out.println("Impossibile creare la cartella.");
            }
        }
    }

    public void loadArrayList() {

        String response = "";
        int totalArticlesToDownload = 1000;
        int articlesPerPage = 50; // The Guardian's API default
        int page = 1;

        makeDirectory();

        while (totalArticlesToDownload > 0) {
            try {

                String requestUrl = API_URL + "&page=" + page + "&show-fields=bodyText&page-size=50&q=" + query;

                URL url = new URL(requestUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == 200) {
                    // Lettura della risposta
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        responseBuilder.append(line);
                    }

                    response = responseBuilder.toString();

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
                            //System.out.println("Article Title: " + title);
                            String body = article.getJSONObject("fields").getString("bodyText");
                            //System.out.println("Article Body: " + body);

                            //aggiunge titolo e body alla arraylist
                            articlesList.add(new Article(title, body));

                            //file.saveArticlesToFile(articlesListGuardian, choise);

                            //stampa l'articolo
                            //System.out.println(articolo);
                        }
                        //System.out.println(articlesListGuardian.size());

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "theguardian_article_"+page+".json"))) {
                        writer.write(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    totalArticlesToDownload -= articlesPerPage;
                    page++;

                } else {
                    System.err.println("Failed to fetch articles. HTTP Status: " + conn.getResponseCode());
                    System.out.println(requestUrl);
                    break; // Esci dal loop in caso di errore
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                break; // Esci dal loop in caso di eccezione
            }
        }

    }
}
