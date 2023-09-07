package it.unipd.dei.eis.adapters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.Article;

public class NYTimescsv extends Adapter {

    public NYTimescsv(){
        filePath = "./Files/NY Times/";
    }

    // public void loadArrayList() throws CsvValidationException, FileNotFoundException

    /**
     * Controllo del formato del file e del nome del file nella directory presente all'interno
     * della variabile filePath
     */
    /**
     * Controllo la correttezza della composizione del file, ovvero divisione in 7 colonne,
     * con colonna 2 => Titolo e colonna 3 => Corpo dell'Articolo
     */
    public void loadArrayList() {

        CSVReader csvReader;

        try {
            csvReader = new CSVReader(new FileReader(filePath+"nytimes_articles_v2.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] - Check the file name and path");
            throw new RuntimeException(e);
        }

        try {
            csvReader.readNext(); //skip line 1
            String[] line;
            while((line = csvReader.readNext()) != null){ //lettura riga x riga file, finché non è nulla
                if(line.length != 7){ //file .csv deve avere 7 colonne/campi
                    throw new CsvValidationException("File con campi sbagliati");
                }
                String title=line[2];
                String bodyArticle=line[3];
                articlesList.add(new Article(title, bodyArticle)); //creazione oggetto Article, con title e bodyArticle
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("Errore nella lettura");
        }
        catch (CsvValidationException e){
            System.out.println("Errore - CSV non valido");
        }
    }//chiude funzione

}