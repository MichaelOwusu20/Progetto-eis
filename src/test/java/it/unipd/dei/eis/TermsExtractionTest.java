package it.unipd.dei.eis;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.*;

/**
 * Testa il corretto funzionamento della classe {@link TermsExtraction} e dei suoi metodi.
 */
public class TermsExtractionTest {

    /**
     * Test della funzione {@link TermsExtraction#extraction(String, String)}.
     * Creazione del file serialize.txt contenente un insieme di articoli.
     * Controlla che il conteggio delle parole del file creato corrisponda a
     * quello calcolato dalla funzione
     */

    @Test
    public void extractionTest() {

        //crea una cartella dove inserire il file degli articoli serializzati
        String path = "./tempDir/";
        GuardianAPIClient.makeDirectory(path);

        //serializza gli articoli
        Serialization.serializeArticlesToFile(createArraylistOfArticles(), path + "serialize.txt", false);
        File serialize = new File("./tempDir/serialize.txt");

        //controlla che sia stato creato un nuovo file
        assertTrue(serialize.exists());

        //chiamata alla funzione che si sta testando
        TermsExtraction.extraction("./tempDir/serialize.txt", "./tempDir/output.txt");

        //controlla che il file output sia stato creato
        File output = new File("./tempDir/output.txt");
        assertTrue(output.exists());

        //legge il contenuto del file creato per eseguire i confronti
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

        //con questo esempio di arraylist risulta semplice verificare quante volte le parole compaiono all'interno di esso
        ArrayList<Article> articles = new ArrayList<>();

        articles.add(new Article("IL GRUPPO", "Gruppo formato da studenti Vicentini di 22 anni."));
        articles.add(new Article("CITTA'", "Nerveza della Battaglia, Quinto Vicentino, Villaggio Montegrappa, Caldogno"));
        articles.add(new Article("MEZZI DI TRASPORTO", "Ferrari, Treno, Bus, Tesla, Bici e Tram"));
        articles.add(new Article("SPORT", "Calcio, Baseball, Basket, Golf e Padel"));

        return articles;
    }
}
