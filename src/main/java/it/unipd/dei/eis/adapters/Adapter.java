package it.unipd.dei.eis.adapters;

import it.unipd.dei.eis.Article;
import java.util.ArrayList;

/**
 * La classe Adapter individua una cartella contenente diversi articoli
 * di giornale e crea un'Arraylist di oggetti di tipo Article
 * nei quali inserisce "Titolo" e "Contenuto" di ogni articolo.
 * La classe è astratta quindi funziona per articoli
 * che derivano da testate giornalistiche differenti.
 */
public abstract class Adapter {

   protected ArrayList<Article> articlesList = new ArrayList<>();
   protected String filePath;

   public Adapter() {
      filePath = null;
   }

   /**
    * @return L'Arraylist contenente gli articoli nel formato Article.
    */
   public ArrayList<Article> getArrayList() { return articlesList; }

   /**
    * @return Il percorso in cui si trova la cartella contenente gli articoli
    * nei formati definiti dalle relative testate giornalistiche.
    */
   public String getFilePath() { return filePath; }

   /**
    * @param path Nome del percorso in cui si trovano gli articoli.
    */
   public void setFilePath(String path) { filePath = path; }

   /**
    * Carica nell'ArrayList gli articoli presenti nella cartella filePath.
    */
   public abstract void loadArrayList();
}
