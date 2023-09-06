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
    * Testa il metodo {@link GuardianAPIClient#downloadTheGuardian()} Verificando che le responses
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
      boolean success =  deleteDirectory(new File(adapter.getFilePath()));
      if(!success)
         System.err.println("Non è stato possibile eliminare la directory.");
   }
/*
   @Test
   public void loadArrayListTest() {
      adapter = new GuardianAPIClient();

      //cartella temporanea dove scaricare la risposta
      adapter.setFilePath("./tempDir/");
      adapter.loadArrayList();
      assertEquals(1000, adapter.getArrayList().size());

      //elimino la cartella temporanea
      if(!deleteDirectory(new File(adapter.getFilePath())))
         System.err.println("Non è stato possibile eliminare la directory.");

      //verifico se il titolo del primo articolo dell'arrayList corrisponde con quello del primo aricolo scaricato
      assertEquals("Legal challenge against Sizewell C nuclear power plant rejected",adapter.getArrayList().get(0).getTitle());

   }

 */



   public static boolean deleteDirectory(File directory) {
      if (directory.isDirectory()) {
         File[] files = directory.listFiles();
         if (files != null) {
            for (File file : files) {
               // Chiamata ricorsiva per eliminare i file/sottodirectory nella directory corrente
               deleteDirectory(file);
            }
         }
      }
      // Elimina la directory vuota o il file
      return directory.delete();
   }
}