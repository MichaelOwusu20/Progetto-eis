package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;
import org.junit.Test;

import static org.junit.Assert.*;
import java.io.File;

/**
 * Classe che testa il funzionamento della classe {@link GuardianAPIClient} e dei suoi metodi.
 */
public class GuardianAPIClientTest {

   private GuardianAPIClient adapter;

   /**
    * Testa il metodo {@link GuardianAPIClient#downloadTheGuardian()} verificando che le responses
    * del The Guardian vengano scaricate correttamente in una cartella specifica.
    */
   @Test
   public void downloadTheGuardianTest()
   {
      adapter = new GuardianAPIClient();

      //cartella temporanea dove scaricare la risposta
      adapter.setFilePath("./tempDir/");

      //verifica che le richieste all'API The Guardian siano andate a buon fine
      //e che le risposte siano state salvate nell'array a loro dedicato
      adapter.downloadTheGuardian();
      for(int i=0; i< adapter.getTotalPages(); i++) {
         assertNotNull(adapter.getResponseArray(i));
      }

      //verifica che il numero di file scaricati nella cartella sia pari a 20
      File directory = new File("./tempDir/");
      File[] files = directory.listFiles();
      assertEquals(20,files.length);

      //elimina la cartella temporanea
      if(!GuardianAPIClient.deleteDirectory(new File(adapter.getFilePath())))
         System.err.println("Non è stato possibile eliminare la directory.");
   }

   /**
    * Testa il metodo {@link GuardianAPIClient#loadArrayList()} verificando che all'interno
    * dell'ArrayList siano presenti tutti i 1000 articoli.
    */
   @Test
   public void loadArrayListTest() {
      adapter = new GuardianAPIClient();

      //scarica le risposte in una cartella temporanea
      adapter.setFilePath("./tempDir/");
      adapter.downloadTheGuardian();

      //carica gli articoli nell'ArrayList
      adapter.loadArrayList();

      //Verifica che l'ArrayList contenente gli articoli prelevati dalla cartella creata
      //abbia dimensione 1000.
      assertEquals(1000, adapter.getArrayList().size());

      //Prendendo come esempio il primo e il 51° articolo dell'ArrayList verifica che il loro titolo
      //corrisponda a quello presente nei file JSON della cartella scaricata.
      assertEquals("Legal challenge against Sizewell C nuclear power plant rejected",adapter.getArrayList().get(0).getTitle());
      assertEquals("UK response to Chinese assault on state is completely inadequate, report finds" , adapter.getArrayList().get(50).getTitle());

      //elimina la cartella temporanea
      if(!GuardianAPIClient.deleteDirectory(new File(adapter.getFilePath())))
         System.err.println("Non è stato possibile eliminare la directory.");
   }

}