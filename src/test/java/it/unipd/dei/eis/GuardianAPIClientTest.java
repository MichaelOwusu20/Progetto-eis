package it.unipd.dei.eis;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import it.unipd.dei.eis.adapters.GuardianAPIClient;

/**
 * Classe che testa il funzionamento della classe {@link GuardianAPIClient} e dei suoi metodi.
 */
public class GuardianAPIClientTest {

   /**
    * Oggetto della classe {@link GuardianAPIClient} utilizzato per eseguire i test.
    */
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

      //elimina la cartella temporanea
      if(!GuardianAPIClient.deleteDirectory(new File(adapter.getFilePath())))
         System.err.println("Non è stato possibile eliminare la directory.");
   }
}