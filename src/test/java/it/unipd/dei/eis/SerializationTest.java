package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.Serialization;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Testa il funzionamento della classe {@link Serialization} e del suo metodo
 */
public class SerializationTest {

    private GuardianAPIClient jsonAdapter;
    private NYTimescsv csvAdapter;

    /**
     * Verifica il funzionamento del metodo {@link Serialization"#serializeArticlesToFile(ArrayList, String)}"
     * nel caso in cui si stia lavorando con articoli del New York Times.
     */
    @Test
    public void NYTimeSerializationTest() {

        //carica l'ArrayList della classe NYTimescsv
        csvAdapter = new NYTimescsv();
        csvAdapter.loadArrayList();

        //crea una directory temporanea dove inserire il file della serializzazione
        GuardianAPIClient.makeDirectory("./tempDir/");

        //metodo che inserisce gli articoli dell'ArrayList in un unico file .txt
        Serialization.serializeArticlesToFile(csvAdapter.getArrayList(), "./tempDir/temp_serialization.txt",false);

        //crea un array dei file presenti nella cartella temporanea creata
        File directory = new File("./tempDir/");
        File[] files = directory.listFiles();

        //verifico che il metodo serializeArticlesToFile abbia creato un file all'interno della cartella selezionata
        assertEquals(1,files.length);

        //legge la prima riga del file creato dal metodo e la converte in stringa
        String firstLine = "";
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./tempDir/temp_serialization.txt"));
            firstLine = fileReader.readLine();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace(); // gestisce le eccezioni in caso di errore di lettura del file
        }

        //verifico che la prima riga del file ottenuto sia uguale alla concatenazione di "Titolo: " e "titolo del primo articolo del file CSV".
        assertEquals("Titolo: Hard Questions on Nuclear Power" , firstLine);

        //elimino la cartella temporanea
        if(!GuardianAPIClient.deleteDirectory(directory))
            System.err.println("Non è stato possibile eliminare la directory.");
    }

    /**
     * Verifica il funzionamento del metodo {@link "Serialization#serializeArticlesToFile(ArrayList, String)}
     * nel caso in cui si stia lavorando con articoli del The Guardian.
     */
    @Test
    public void TheGuardianSerializationTest() {

        jsonAdapter = new GuardianAPIClient();

        //scarica gli articoli in formato JSON in una cartella temporanea
        jsonAdapter.setFilePath("./tempTheGuardian/");
        jsonAdapter.downloadTheGuardian();

        //riempie l'arrayList della classe GuardianAPIClient
        jsonAdapter.loadArrayList();

        //crea una cartella temporanea in cui salvare il file ottenuto con la serializzazione
        GuardianAPIClient.makeDirectory("./tempDir/");

        //chiama il metodo che crea un file .txt contenente tutti gli articoli del The Guardian
        Serialization.serializeArticlesToFile(jsonAdapter.getArrayList(), "./tempDir/temp_serialization.txt",false);

        //crea un array dei file presenti nella cartella temporanea creata
        File directory = new File("./tempDir/");
        File[] files = directory.listFiles();

        //verifico che il metodo serializeArticlesToFile abbia creato un file all'interno della cartella selezionata
        assertEquals(1,files.length);

        //legge la prima riga del file creato dal metodo e la converte in stringa
        String firstLine = "";
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./tempDir/temp_serialization.txt"));
            firstLine = fileReader.readLine();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gestione delle eccezioni in caso di errore di lettura del file.
        }

        //verifico che la prima riga del file ottenuto sia uguale alla concatenazione di "Titolo: " e "titolo del primo articolo dei file JSON".
        assertEquals("Titolo: Legal challenge against Sizewell C nuclear power plant rejected" , firstLine);

        //elimino le cartelle temporanee
        if(!GuardianAPIClient.deleteDirectory(directory))
            System.err.println("Non è stato possibile eliminare la directory.");

        File guardianDirectory = new File("./tempTheGuardian/");
        if(!GuardianAPIClient.deleteDirectory(guardianDirectory))
            System.err.println("Non è stato possibile eliminare la directory.");
    }
}
