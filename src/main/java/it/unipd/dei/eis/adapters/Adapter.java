package it.unipd.dei.eis.adapters;

import java.util.ArrayList;
import it.unipd.dei.eis.Article;

/**
 * La classe Adapter individua una cartella contenente diversi articoli
 * di giornale e crea un'Arraylist di oggetti di tipo {@link Article}
 * nei quali inserisce "Titolo" e "Contenuto" di ogni articolo.
 * La classe è astratta quindi funziona per articoli
 * che derivano da testate giornalistiche differenti.
 */
public abstract class Adapter {

   /**
    * Arraylist in cui sono contenuti gli articoli sottoforma
    * di oggetti {@link Article}.
    */
   protected ArrayList<Article> articlesList = new ArrayList<>();

   /**
    * Nome del percorso in cui sono presenti i file degli articoli.
    */
   protected String filePath;

   /**
    * Costruttore protected perchè non è possibile creare oggetti
    * di tipo Adapter al di fuori delle sue sottoclassi.
    */
   protected Adapter() {
      filePath = null;
   }

   /** Ritorna L'arraylist contenente gli articoli nel formato Article.
    * @return {@link Adapter#articlesList}.
    */
   public ArrayList<Article> getArrayList() { return articlesList; }

   /**
    * Ritorna il percorso in cui si trova la cartella contenente gli articoli
    * nei formati definiti dalle relative testate giornalistiche.
    * @return {@link Adapter#filePath}.
    */
   public String getFilePath() { return filePath; }

   /**
    * Modifica il percorso in cui si trovano gli articoli.
    * @param path nome del nuovo percorso.
    */
   public void setFilePath(String path) { filePath = path; }

   /**
    * Carica nell'ArrayList gli articoli presenti nella cartella filePath.
    */
   public abstract void loadArrayList();
}
