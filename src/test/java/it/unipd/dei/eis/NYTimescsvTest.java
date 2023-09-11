package it.unipd.dei.eis;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import it.unipd.dei.eis.adapters.NYTimescsv;

/**
 * Classe che testa il funzionamento della classe {@link NYTimescsv} e dei suoi metodi.
 */
public class NYTimescsvTest {

    /**
     * Oggetto della classe {@link NYTimescsv} utilizzato per eseguire i test.
     */
    private final NYTimescsv adapter = new NYTimescsv();

    /**
     * Verifica che la cartella "./File/NY Times" esista e che contenga il file CSV.
     */
    @Test
    public void DirectoryContainsCSVFile() {

        File directory = new File("./Files/NY Times");
        if(directory.exists()) {
            File[] files = directory.listFiles();
            assertEquals(1, files.length);
        }else
            System.err.println("La directory specificata non esiste");
    }

    /**
     *Testa il metodo {@link NYTimescsv#loadArrayList()} verificando che l'ArrayList di
     *oggetti Article contenga tutti i 1000 articoli prelevati dal file CSV.
     */
    @Test
    public void sizeOfArrayList() {
        adapter.loadArrayList();
        assertEquals(1000, adapter.getArrayList().size());
    }

    /**
     * Prendendo come esempio il primo e il nono articolo dell'ArrayList verifica che il loro titolo
     * corrisponda a quello presente nel file CSV.
     */
    @Test
    public void checkArticleTitle() {
        adapter.loadArrayList();
        assertEquals("Hard Questions on Nuclear Power", adapter.getArrayList().get(0).getTitle());
        assertEquals("China Slows Development Of Nuclear Power Plants", adapter.getArrayList().get(8).getTitle());
    }
}