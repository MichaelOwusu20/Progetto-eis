package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class TermsExtractionTest {

    /**
     * Test della funzione {@link TermsExtraction#extraction(String, String)}
     * Creazione del file serialize.txt nella sua relativa directoty
     * Controllo aspettiva con l'effettivo valore del conteggio parole della funzione
     */

    @Test
    public void extractionTest() {

        String path = "./tempDir/";
        GuardianAPIClient.makeDirectory(path);
        Serialization.serializeArticlesToFile(createArraylistOfArticles(), path + "serialize.txt", false);
        File serialize = new File("./tempDir/serialize.txt");
        assertTrue(serialize.exists());

        TermsExtraction.extraction("./tempDir/serialize.txt", "./tempDir/output.txt");

        File output = new File("./tempDir/output.txt");
        assertTrue(output.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(output))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            assertEquals("gruppo: 2", lines.get(0));
            assertEquals("di: 2", lines.get(1));
            assertEquals("e: 2", lines.get(2));
            assertEquals("bus: 1", lines.get(3));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //elimina la cartella temporanea
        if(!GuardianAPIClient.deleteDirectory(new File(path)))
            System.err.println("Non è stato possibile eliminare la directory.");
        }

    /**
     *Creazione di un Arraylist di 4 posizioni, con al suo interno delle stringhe
     * divese seguendo lo schema Titolo + Articolo
     */

    private ArrayList<Article> createArraylistOfArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        articles.add(new Article("IL GRUPPO", "Gruppo formato da studenti Vicentini, di 22 anni. "));
        articles.add(new Article("CITTA'", "Nerveza della Battaglia, Quinto Vicentino, Villaggio Montre-Grapp, Caldogno"));
        articles.add(new Article("MEZZI DI TRASPORTO", "Ferrari, Treno, Bus, Tesla, Bici e Tram"));
        articles.add(new Article("SPORT", "Calcio, Baseball, Basket, Golf e Padel"));
        return articles;
    }
}
