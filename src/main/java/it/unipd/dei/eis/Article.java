package it.unipd.dei.eis;
import java.util.Map;
import java.util.TreeMap;

public class Article{

    //var. TestoArticolo
    private String Title="";

    //var. corpoArticolo
    private String bodyArticle="";


    //Articolo vuoto
    public Article(){
    }

    public Article(String title, String body) {
        Title = title;
        bodyArticle = body;
    }

    //set dell'articolo
    //@title -> titolo articolo --> get + set
    //@body -> corpo articolo --> get + set
    public String getTitle(){
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setBodyArticle(String bodyArticle) {
        this.bodyArticle = bodyArticle;
    }

    public String getBodyArticle(){
        return bodyArticle;
    }

    //@Override
    public String toString(){
        return "<article>\n"+ "\t<Title> \n" + "\t" + Title + "\n" +
                "\t <Title>\n" + "\t<bodyArticle> \n" + "\t" + bodyArticle + "\n" +"\t <bodyArticle>\n" +
                "</article>";
    }

    //Funzione che permette di creare una stringa unica dell'articolo per poi poterne analizzare i termini
    public String stringToAnalyze()
    {
        return Title+" "+bodyArticle;
    }
}

