//String token for reading and counting token
package it.unipd.dei.eis;

import java.util.ArrayList;
import java.util.Map;
//import java.util.StringTokenizer;
//import java.util.TreeMap;
import it.unipd.dei.eis.serialization.Deserialization;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class TermsExtraction{

   //Funzione che ordina le parole in base alle occorenze e conserva solo i primi  50 termini
   public static Map<String,Integer> sortWords(Map<String,Integer> words )
   {

      List<Map.Entry<String, Integer>> entryList = new ArrayList<>(words.entrySet());

      // Inverti il comparatore per l'ordinamento decrescente
      Comparator<Map.Entry<String, Integer>> valueComparator = (entry1, entry2) ->
              entry2.getValue().compareTo(entry1.getValue());

      Collections.sort(entryList, valueComparator);

      Map<String, Integer> sortedMap = new LinkedHashMap<>();
      for (Map.Entry<String, Integer> entry : entryList) {
         sortedMap.put(entry.getKey(), entry.getValue());
      }


      // Tronca la mappa dopo il 50° elemento
      int limite = 50;
      int conteggio = 0;
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

      //Scrive in un file txt le occorrenze delle parole negli articoli
   public  static void extraction(String filePath){

      try
      {
         //Deserializzo gli articoli del file txt
         ArrayList<Article> articles= Deserialization.deserializeFileToArticle(filePath);

         //Creo la mappa di coppie (parola, numero di occorenze)
         Map<String , Integer >unsortedWords= TermsExtraction.countWords(articles);
         //Ordino la Mappa e lascio solo i primi 50 elementi
          Map<String , Integer >wordCount=TermsExtraction.sortWords(unsortedWords);


         BufferedWriter writer = new BufferedWriter(new FileWriter("./Files/output.txt"));
         for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            //Set<Entry<String, Integer>> entrySet = wordCount.entrySet();

            String word = entry.getKey();
            int count = entry.getValue();
            writer.write(word + ": " + count);
            writer.newLine();
         }
         writer.close();
      System.out.println("OK");

      }catch (IOException e) {
         e.printStackTrace();
      }

   }

   //Funzione che riceve un ArrayList e ritorna una Map di coppie (parola, numero di occorrenze)
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

/*
public class TermsExtraction {

   private Map<String, Integer> map = new TreeMap<>();

   public void extraction() {

      ArrayList<Article> articles = Deserialization.deserializeFileToArticle("./files/serialize.txt");
      for (Article article : articles) {
         String title = article.getTitle();
         String bodyArticle = article.getBodyArticle();
         tokenizer(title, bodyArticle);
      }
}

   public void tokenizer(String Title, String bodyArticle) {
      StringTokenizer strToken = new StringTokenizer(Title+bodyArticle);
     // StringTokenizer strBodyArticle = new StringTokenizer(bodyArticle);
      ArrayList<String> list = new ArrayList<>();
      //Map<String, Integer> map = new TreeMap<>();
      int count = 0;
      while (strToken.hasMoreTokens()) {
         list.add(strToken.nextToken());
      }

      for (int i = 0; i < list.size(); i++) {

         for (int j = 0; j < list.size(); j++) {
            if (list.get(i).equals(list.get(j))) {
               count++;
           }
         }
         map.put(list.get(i), count);
         System.out.println(map + "\n");
         count=0;
        break;
      }
   }
   public void sortedMap(Map map){


   }
}
*/
