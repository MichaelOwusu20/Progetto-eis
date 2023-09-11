package it.unipd.dei.eis;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.*;

/**
 * Testa il corretto funzionamento della classe {@link Deserialization}.
 */
public class DeserializationTest {

    /**
     * Oggetto della classe {@link GuardianAPIClient} utilizzato per eseguire i test.
     */
    private GuardianAPIClient jsonAdapter;

    /**
     * Oggetto della classe {@link NYTimescsv} utilizzato per eseguire i test.
     */
    private NYTimescsv csvAdapter;

    /**
     * Testa il corretto funzionamento del metodo {@link Deserialization#deserializeFileToArticle(String)} per gli articoli NY Times.
     * Carica gli articoli in un'arraylist e li serializza in un file txt.
     * Deserializza il file appena creato e confronta gli oggetti
     * dell'array ottenuto con quello contenuto nell'arraylist della classe {@link NYTimescsv}.
     */
    @Test
    public void NYTimeDerializationTest() {
        csvAdapter = new NYTimescsv();

        //carica l'ArrayList della classe NYTimescsv
        csvAdapter = new NYTimescsv();
        csvAdapter.loadArrayList();

        //crea una directory temporanea dove inserire il file della serializzazione
        GuardianAPIClient.makeDirectory("./tempDir/");

        //metodo che inserisce gli articoli dell'ArrayList in un unico file .txt
        Serialization.serializeArticlesToFile(csvAdapter.getArrayList(), "./tempDir/temp_serialization.txt",false);

        //deserializzazione del file appena creato
        ArrayList<Article> deserializedArray = Deserialization.deserializeFileToArticle("./tempDir/temp_serialization.txt");

        //verifica che la lunghezza dell'arrayList creato sia pari al numero totale di articoli
        assertEquals(1000, deserializedArray.size());

        //confronta ogni oggetto della deserializzazione con ogni elemento presente nella variabile articlesList della classe NYTimescsv
        int i = 0;
        for(Article article : deserializedArray)
        {
            //sia titolo che body devono combaciare
            assertEquals(article.getTitle() , csvAdapter.getArrayList().get(i).getTitle());
            assertEquals(article.getBodyArticle() , csvAdapter.getArrayList().get(i).getBodyArticle());
            i++;
        }

        //elimino la cartelle temporanea
        File directory = new File("./tempDir/");
        if(!GuardianAPIClient.deleteDirectory(directory))
            System.err.println("Non è stato possibile eliminare la directory.");
    }

    /**
     * Testa il corretto funzionamento del metodo {@link Deserialization#deserializeFileToArticle(String)} per gli articoli The Guardian.
     * Carica gli articoli in un'arraylist e li serializza in un file txt.
     * Deserializza il file appena creato e confronta gli oggetti
     * dell'array ottenuto con quello contenuto nell'arraylist della classe {@link GuardianAPIClient}.
     */
    @Test
    public void TheGuardianDeserializationTest() {

        jsonAdapter = new GuardianAPIClient();

        //scarica gli articoli in formato JSON in una cartella temporanea
        jsonAdapter.setFilePath("./tempTheGuardian/");
        jsonAdapter.downloadTheGuardian();

        //riempie l'arrayList della classe GuardianAPIClient
        jsonAdapter.loadArrayList();

        //elimina la directory TheGuardian
        File guardianDirectory = new File("./tempTheGuardian/");
        if(!GuardianAPIClient.deleteDirectory(guardianDirectory))
            System.err.println("Non è stato possibile eliminare la directory.");

        //crea una directory temporanea dove inserire il file della serializzazione
        GuardianAPIClient.makeDirectory("./tempDir/");

        //metodo che inserisce gli articoli dell'ArrayList in un unico file .txt
        Serialization.serializeArticlesToFile(jsonAdapter.getArrayList(), "./tempDir/temp_serialization.txt", false);

        //deserializzazione del file appena creato
        ArrayList<Article> deserializedArray = Deserialization.deserializeFileToArticle("./tempDir/temp_serialization.txt");

        //verifica che la lunghezza dell'arrayList creato sia pari al numero totale di articoli
        assertEquals(1000, deserializedArray.size());

        int i = 0;
        for(Article article : deserializedArray)
        {
            assertEquals(article.getTitle() , jsonAdapter.getArrayList().get(i).getTitle());
            assertEquals(article.getBodyArticle() , jsonAdapter.getArrayList().get(i).getBodyArticle());
            i++;
        }

        //elimino la cartelle temporanea
        File directory = new File("./tempDir/");
        if(!GuardianAPIClient.deleteDirectory(directory))
            System.err.println("Non è stato possibile eliminare la directory.");
    }
}
