package it.unipd.dei.eis.serialization;

import java.io.File;
import java.util.ArrayList;
import it.unipd.dei.eis.Article;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


public class Serialization {

   public static void serializeArticlesToFile(ArrayList<Article> articles, String filePath) {
      for (int i = 0; i < articles.size(); i++) {

         Article article = articles.get(i);
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Titolo: " + article.getTitle() + "\n");
            writer.write("Contenuto:\n" + article.getBodyArticle());
            writer.close();


         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      System.out.println("Articoli salvati nel file " +filePath);

   }

}

/*
* public static void  SaveArticlesToFile(ArrayList<Article> articles, int choice) {

        String directory = "/Users/Giuseppe/Desktop/Article";
        File folder = new File(directory);
        folder.mkdirs();

        if(choice==1) {

            String directoryNYTimes = directory+"/NY Times";
            File folderNYTimes = new File(directoryNYTimes);
            folderNYTimes.mkdirs();

            // Salvataggio degli articoli in file di testo
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                String fileName = "article n-" + (i + 1);

                try {
                    FileWriter fileWriter = new FileWriter(directoryNYTimes + "/" + fileName);
                    fileWriter.write("Titolo: " + article.getTitle() + "\n");
                    fileWriter.write("Contenuto:\n" + article.getBodyArticle());
                    fileWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Articoli salvati nel percorso: "+directoryNYTimes);
        }

        if(choice==2) {
            String directoryTheGuardian = directory+"/The Guardian";
            File folderTheGuardian = new File(directoryTheGuardian);
            folderTheGuardian.mkdirs();

            // Salvataggio degli articoli in file di testo
            for (int i = 0; i < articles.size(); i++) {

                 Article article = articles.get(i);
                String fileName = "article n-" + (i + 1);

                try {
                    FileWriter fileWriter = new FileWriter(directoryTheGuardian + "/" + fileName);
                    fileWriter.write("Titolo: " + article.getTitle() + "\n");
                    fileWriter.write("Contenuto:\n" + article.getBodyArticle());
                    fileWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Articoli salvati nel percorso: "+directoryTheGuardian);
        }
    }*/