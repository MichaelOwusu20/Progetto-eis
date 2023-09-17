package it.unipd.dei.eis.adapters;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import it.unipd.dei.eis.Article;

/**
 * Permette di eseguire il download di diversi articoli utilizzando
 * The Guardian API e di creare un arraylist di oggetti {@link Article} contenente tutti gli
 * articoli scaricati.
 */
public class GuardianAPIClient extends Adapter {

    /**
     * Chiave personale ottenuta dal sito del The Guardian mediante autenticazione.
     */
    private static final String API_KEY = "f03b204b-b551-42cd-a09e-324f14a31a59";

    /**
     * Link in cui vengono visualizzate le risposte.
     */
    private static final String API_URL = "https://content.guardianapis.com/search?api-key=" + API_KEY;

    /**
     * Vengono scelti solamente gli articoli che contengono le parole indicate nella query.
     */
    private static final String query = "nuclear-power";

    /**
     * Per scaricare 1000 articoli è necessario fare una richiesta al The Guardian di 20 pagine,
     * ogni response può contenere al massimo 50 articoli.
     */
    private static final int totalPages = 20;

    /**
     * Array in cui sono contenute le 20 pagine di responses.
     */
    private final String[] responseArray = new String[20];

    /**
     * Costruttore che setta il percorso in cui si vogliono scaricare i files.
     */
    public GuardianAPIClient() {
        filePath = "./Files/The Guardian/";
    }

    /**
     * Ritorna una response contenuta nell'array di responses.
     * @param i posizione nell'array.
     * @return response in formato stringa.
     */
    public String getResponseArray(int i){ return responseArray[i]; }

    /**
     * Individua il numero di pagine della risposta.
     * @return numero totali di pagine.
     */
    public int getTotalPages() { return totalPages; }

    /**
     * Esegue il download delle responses nella cartella indicata dal costruttore.
     * Viene creato un file .json per ogni pagina.
     */
    public void downloadTheGuardian(){

        //riempe l'array di risposte
        loadResponseArray();

        //crea la directory in cui scaricare i files
        makeDirectory(filePath);

        //per ogni elemento dell'array crea un file .json
        for(int i=1; i<= responseArray.length; i++)
        {
            //salva i file e distingue i loro nomi con il numero di pagina
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "theguardian_article_" + i/10 + i%10 + ".json"))) {
                //scrive il contenuto della risposta all'interno del file
                writer.write(responseArray[i-1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Analizza le risposte .json contenute nella variabile
     * {@link GuardianAPIClient#responseArray}. Per ognuna di esse
     * estrae gli articoli e li converte in oggetti {@link Article},
     * i quali verranno inseriti in {@link Adapter#articlesList}
     *
     */
    public void loadArrayList() {

        //crea un array con gli elementi presenti nella cartella indicata
        File folder = new File(filePath);
        File[] files = folder.listFiles();

        //ordina i file nella cartella in ordine alfabetico
        Arrays.sort(files);

        //per ogni file legge il suo contenuto e lo trasforma in una stringa
        for(File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line+=line;
                    try {
                        // Creazione di un oggetto JSON dalla risposta completa
                        JSONObject jsonResponse = new JSONObject(line);

                        // Estrazione dell'oggetto "response" dall'oggetto JSON
                        JSONObject responseJson = jsonResponse.getJSONObject("response");

                        // Estrazione dell'array "results" dall'oggetto "response"
                        JSONArray articles = responseJson.getJSONArray("results");

                        // Iterazione attraverso gli articoli nell'array "results"
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject article = articles.getJSONObject(i);

                            // Estrazione del titolo e del corpo dell'articolo
                            String title = article.getString("webTitle");
                            String body = article.getJSONObject("fields").getString("bodyText");

                            //aggiunge titolo e body all'arrayList
                            articlesList.add(new Article(title, body));
                        }

                        //cattura eventuali eccezioni dovute ai file .json
                    } catch (JSONException e) {
                        e.printStackTrace();

                        //esci dal loop in caso di eccezione
                        break;
                    }
                }

            }
            catch (IOException e) {
                System.err.println("Errore durante la lettura del file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    /**
     * Esegue la richiesta al The Guardian utilizzando l'API Key.
     * @param currentPage numero di pagina della response.
     * @return stringa contenente la response ottenuta dal The Guardian
     * la quale contiene 50 articoli.
     */
    private String makeApiRequest(int currentPage) {

        String apiResponse = "";
        //URL aggiornato con i valori della pagina corrente e con la query
        String requestUrl = API_URL + "&page=" + currentPage + "&show-fields=bodyText&page-size=50&q=" + query;

        try {
            //Esegue la richiesta al sito
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //Se la funzione ritorna 200 significa che la richiesta è andata a buon fine
            if (conn.getResponseCode() == 200) {
                // Lettura della risposta
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;

                //Analizza riga per riga la risposta e la converte in una stringa
                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                }
                apiResponse = responseBuilder.toString();

            } else {
                //La richiesta non è andata a buon fine
                System.err.println("Failed to fetch articles. HTTP Status: " + conn.getResponseCode());
                System.out.println(requestUrl);
            }

            //chiude la connessione al server HTTP
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiResponse;
    }

    /**
     * Dopo aver chiamato {@link GuardianAPIClient#makeApiRequest(int)} la variabile
     * {@link GuardianAPIClient#responseArray} viene riempita con le risposte ottenute
     */
    private void loadResponseArray()
    {
        //viene eseguita una richiesta per ogni pagina
        for(int i=1; i<=totalPages; i++)
            responseArray[i-1] = makeApiRequest(i);
    }

    /**
     * Crea una cartella all'interno di un percorso specifico
     * nel caso in cui questa non sia già esistente.
     * @param path percorso della cartella che si vuole creare.
     */
    public static void makeDirectory(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) {
                System.err.println("Impossibile creare la cartella.");
            }
        }
    }

    /**
     * Elimina la cartella indicata.
     * @param directory percorso della cartella che si vuole eliminare.
     * @return false se non è stato possibile eliminare la directory,
     * true altrimenti.
     */
    public static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            //array di tutti gli elementi presenti nella cartella
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Chiamata ricorsiva per eliminare i file/sottodirectory nella directory corrente
                    deleteDirectory(file);
                }
            }
        }
        // Elimina la directory vuota o il file
        return directory.delete();
    }
}