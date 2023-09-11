package it.unipd.dei.eis.adapters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.Article;

/**
 * Crea un arraylist di oggetti {@link Article} a partire
 * dai file csv forniti dal New York Times.
 */
public class NYTimescsv extends Adapter {

    /**
     * Costruttore che setta il percorso in cui sono presenti i files.
     */
    public NYTimescsv(){
        filePath = "./Files/NY Times/";
    }

    /**
     * Controlla che il file csv sia presente all'interno della directory indicata nella
     * variabile {@link Adapter#filePath}, converte gli articoli in oggetti Article e li
     * inserisce nella variabile {@link Adapter#articlesList}.
     */
    public void loadArrayList() {

        CSVReader csvReader;
        //Controlla che il file sia presente nella cartella indicata
        try {
            csvReader = new CSVReader(new FileReader(filePath+"nytimes_articles_v2.csv"));
        } catch (FileNotFoundException e) {
            System.err.println("Errore, controlla il nome del file o il percorso.");
            throw new RuntimeException(e);
        }

        //Controlla la correttezza della composizione del file, ovvero divisione in 7 colonne,
        // colonna 2 => Titolo e colonna 3 => Corpo dell'Articolo
        try {
            csvReader.readNext(); //salta la prima riga
            String[] line;
            while((line = csvReader.readNext()) != null){ //lettura riga per riga file, finché non è nulla
                if(line.length != 7){ //file .csv deve avere 7 colonne/campi
                    throw new CsvValidationException("File con campi sbagliati");
                }
                String title=line[2]; //titolo
                String bodyArticle=line[3]; //corpo
                articlesList.add(new Article(title, bodyArticle)); //creazione oggetto Article, con title e bodyArticle
            }
            csvReader.close();
        } catch (IOException e) {
            System.err.println("Errore nella lettura");
        }
        catch (CsvValidationException e){
            System.err.println("Errore, CSV non valido");
        }
    }
}