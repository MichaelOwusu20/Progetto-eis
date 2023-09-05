//String token for reading and counting token
package it.unipd.dei.eis;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import it.unipd.dei.eis.serialization.Deserialization;

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