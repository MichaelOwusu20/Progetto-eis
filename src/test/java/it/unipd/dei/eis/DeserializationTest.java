package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.Deserialization;
import it.unipd.dei.eis.serialization.Serialization;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeserializationTest {

    private GuardianAPIClient jsonAdapter;
    private NYTimescsv csvAdapter;

    @Test
    public void NYTimeDerializationTest() {
        csvAdapter = new NYTimescsv();

        //carica l'ArrayList della classe NYTimescsv
        csvAdapter = new NYTimescsv();
        csvAdapter.loadArrayList();

        //crea una directory temporanea dove inserire il file della serializzazione
        GuardianAPIClient.makeDirectory("./tempDir/");

        //metodo che inserisce gli articoli dell'ArrayList in un unico file .txt
        Serialization.serializeArticlesToFile(csvAdapter.getArrayList(), "./tempDir/temp_serialization.txt");

        ArrayList<Article> deserializedArray = Deserialization.deserializeFileToArticle("./tempDir/temp_serialization.txt");

        //verifica che la lunghezza dell'arrayList creato sia pari al numero totale di articoli
        assertEquals(1000, deserializedArray.size());

        int i = 0;
        for(Article article : deserializedArray)
        {
            assertEquals(article.getTitle() , csvAdapter.getArrayList().get(i).getTitle());
            assertEquals(article.getBodyArticle() , csvAdapter.getArrayList().get(i).getBodyArticle());
            i++;
        }

        //elimino la cartelle temporanea
        File directory = new File("./tempDir/");
        if(!GuardianAPIClient.deleteDirectory(directory))
            System.err.println("Non è stato possibile eliminare la directory.");
    }

    @Test
    public void TheGuardianDerializationTest() {

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
        Serialization.serializeArticlesToFile(jsonAdapter.getArrayList(), "./tempDir/temp_serialization.txt");

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
