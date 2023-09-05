package it.unipd.dei.eis.adapters;

import it.unipd.dei.eis.Article;
import java.util.ArrayList;

public abstract class Adapter {

   protected ArrayList<Article> articlesList = new ArrayList<>();
   protected String filePath;

   public Adapter() {
      filePath = null;
   }

   public ArrayList<Article> getArrayList() { return articlesList; }
   public String getFilePath() { return filePath; }
   public void setFilePath(String path) { filePath = path; }

   public abstract void loadArrayList();
}
