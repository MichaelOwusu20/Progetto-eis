package it.unipd.dei.eis.serialization;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import it.unipd.dei.eis.Article;

/**
 * La classe Seralization fornisce metodi per serializzare articoli in un file txt.
 */
public class Serialization {

   /**
    * Questo metodo serializza un ArrayList di oggetti Article in un file txt.
    * Il risultato sarà scritto in serialize.txt.
    * @param articles L'ArrayList di Article, contenente gli articoli da serializzare.
    * @param filePath Il percorso del file txt in cui si vogliono serializzare gli articoli. Se non esiste viene creato.
    * @param overwrite Una variabile di controllo che indica se il file txt debba essere sovrascritto oppure no. Se è true viene sovrascritto.
    */
   public static void serializeArticlesToFile(ArrayList<Article> articles, String filePath, boolean overwrite) {

      //Se overwrite==true si sovrascrive il file , oppure lo si crea se non è già esistente
      if(overwrite) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            //Scrittura degli articoli nel file txt
            for (Article article : articles) {
               writer.write("Titolo: " + article.getTitle() + "\n");
               writer.write("Contenuto: " + article.getBodyArticle() + "\n\n");
            }
            writer.close();

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      else
      {
         //In questo caso il file non viene sovrascritto
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            for (Article article : articles) {
               writer.write("Titolo: " + article.getTitle() + "\n");
               writer.write("Contenuto: " + article.getBodyArticle() + "\n\n");
            }
            writer.close();

         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      System.out.println("Articoli salvati nel file " + filePath);
   }
}