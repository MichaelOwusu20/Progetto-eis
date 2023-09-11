package it.unipd.dei.eis;

/**
 * Classe che definisce oggetti di tipo Article, contenenti il titolo e il contenuto di un articolo.
 */
public class Article{

    /**
     * Titolo dell'articolo.
     */
    private String Title="";

    /**
     * Corpo dell'articolo.
     */
    private String bodyArticle="";

    /**
     * Costruttore vuoto.
     */
    public Article(){
    }

    /**
     * Costruttore per un oggetto di tipo Article.
     * @param title il titolo dell'articolo.
     * @param body il corpo dell'articolo.
     */
    public Article(String title, String body) {
        Title = title;
        bodyArticle = body;
    }

    /**
     * Accesso al titolo dell'articolo.
     * @return il titolo.
     */
    public String getTitle(){
        return Title;
    }

    /**
     * Modifica il titolo dell'oggetto chiamante.
     * @param Title il nuovo titolo.
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * Modifica il corpo dell'oggetto chiamante.
     * @param bodyArticle il nuovo corpo.
     */
    public void setBodyArticle(String bodyArticle) {
        this.bodyArticle = bodyArticle;
    }
    
    /**
     * Accesso al body dell'articolo.
     * @return il body dell'articolo.
     */
    public String getBodyArticle(){
        return bodyArticle;
    }

    /**
     * Override della funzioone toString. Viene scritto l'articolo in formato xml.
     * @return una stringa contenete l'articolo in formato xml.
     */
    public String toString(){
        return "<article>\n"+ "\t<Title> \n" + "\t" + Title + "\n" +
                "\t </Title>\n" + "\t<bodyArticle> \n" + "\t" + bodyArticle + "\n" +"\t </bodyArticle>\n" +
                "</article>";
    }

    /**
     * Metodo che permette di creare una stringa unica dell'articolo per poi poterne analizzare i termini.
     * @return una stringa con titolo e corpo dell'articolo separati da uno spazio.
     */
    public String stringToAnalyze()
    {
        return Title+" "+bodyArticle;
    }
}

