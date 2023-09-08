
package it.unipd.dei.eis.serialization;
//a file .JSON

import java.util.ArrayList;
import it.unipd.dei.eis.Article;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

/**
 * La classe Deserialization fornisce metodi per deserializzare un file txt in un ArrayList di oggetti Article
 */
public class Deserialization {


   /**
    * Questo metodo deserializza un file txt in un ArrayList di oggetti Article
    * @param filePath Il percorso del file da deserializzare
    * @return L'ArrayList di Article che risulta dopo la deserializzazione
    */
   public static ArrayList<Article> deserializeFileToArticle(String filePath) {
      ArrayList<Article> articles = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String title = null;
         String bodyArticle = null;
         String line;
         //Viene letto il file txt riga per riga e vengono estratti Titolo e Contenuto dell'articolo
         while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
               if (line.startsWith("Titolo: ")) {
                  //Estrazione del titolo
                  title = line.substring("Titolo: ".length());
               } else if (line.startsWith("Contenuto: ")) {
                  //Estrazione del Contenuto
                  bodyArticle = line.substring("Contenuto: ".length());
               }
            }
            //Riempimento dell'ArrayList
            else
               articles.add(new Article(title, bodyArticle));
         }

         System.out.println("Deserializzato il file txt");

      } catch (IOException e) {
         e.printStackTrace();
      }
      return articles;
   }
}