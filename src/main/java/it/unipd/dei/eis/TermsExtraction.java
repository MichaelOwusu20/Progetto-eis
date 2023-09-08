//String token for reading and counting token
package it.unipd.dei.eis;

import java.util.ArrayList;
import java.util.Map;
import it.unipd.dei.eis.serialization.Deserialization;

import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.List;
import java.util.Comparator;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * La classe TermsExtraction contiene metodi per l'analisi degli articoli serializzati
 */
public class TermsExtraction{

   /**
    * Metodo che ordina gli elementi di una Map<String,Integer> per valori di Integer decrescenti.
    * Poi tronca la mappa fino al 50°esimo elemento.
    * @param words La mappa non ordinata
    * @return La mappa ordinata con solo i primi 50 elementi
    */

   public static Map<String,Integer> sortWords(Map<String,Integer> words )
   {

      //Creo una lista di entry della mappa words per usarne le coppie chiave-valore
      List<Map.Entry<String, Integer>> entryList = new ArrayList<>(words.entrySet());

      // Inverti il comparatore per l'ordinamento decrescente
      Comparator<Map.Entry<String, Integer>> valueComparator = (entry1, entry2) ->
              entry2.getValue().compareTo(entry1.getValue());

      //Si ordina la entryList in base al valueComparator , quindi per Integer decrescenti
      Collections.sort(entryList, valueComparator);

      //Creo una nuova mappa e la riempio   con i valori ordinati di entryList
      Map<String, Integer> sortedMap = new LinkedHashMap<>();
      for (Map.Entry<String, Integer> entry : entryList) {
         sortedMap.put(entry.getKey(), entry.getValue());
      }


      // Tronca la mappa dopo il 50° elemento
      int limite = 50;
      int conteggio = 0;

      //Uso un iteratore per muovermi all'interno della mappa
      Iterator<Map.Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();

      while (iterator.hasNext()) {
         iterator.next();
         conteggio++;
         if (conteggio > limite) {
            iterator.remove(); // Rimuovi l'elemento corrente
         }
      }


      return sortedMap;
   }


   /**
    * Metodo che analizza gli articoli serializzati ( contenuti nel file serialize.txt) e mette il risultato dell'analisi in un altro file txt
    * @param fileToDeserialize il percorso del file txt in cui sono presenti gli articoli serializzati
    * @param fileOutput il percorso del file txt in cui verrà scritto il risultato dell'analisi
    */
   public  static void extraction(String fileToDeserialize, String fileOutput){

      try
      {
         //Deserializzo gli articoli del file txt
         ArrayList<Article> articles= Deserialization.deserializeFileToArticle(fileToDeserialize);

         //Creo la mappa di coppie (parola, numero di occorenze)
         Map<String , Integer >unsortedWords= TermsExtraction.countWords(articles);
         //Ordino la Mappa e lascio solo i primi 50 elementi
          Map<String , Integer >wordCount=TermsExtraction.sortWords(unsortedWords);


         BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput));
         //Scrivo le parole e le rispettive occorrenze nel file di output
         for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            writer.write(word + ": " + count);
            writer.newLine();
         }
         writer.close();
      System.out.println("Analisi degli articoli scritta nel file "+fileOutput);

      }catch (IOException e) {
         e.printStackTrace();
      }

   }

   /**
    * Metodo che riceve un ArrayList di oggetti Article e ritorna una mappa con le parole presenti in ogni articolo, insieme al numero di occorrenze di ogni parola.
    * @param articles L'ArrayList contenente tutti gli articoli.
    * @return Una Map<String, Integer> in cui la String indica la parola , mentre l'Integer il numero di occorenze negli articoli.
    */
   
   public static Map<String,Integer> countWords(ArrayList<Article> articles){

      String allTheArticles= "";

      //Formo un stringa contenente i titoli e i corpi di tutti gli articoli della lista
      for (Article article :articles)
         allTheArticles += article.stringToAnalyze();

      //divido la stringa in parole e tolgo i segni di punteggiatura
      String[] words = allTheArticles.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");


      // Crea un Map per conteggiare le parole
      Map<String, Integer> wordCount = new HashMap<>();

      // Conta le parole
      for (String word : words) {
         wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
      }

      return wordCount;

   }
}
