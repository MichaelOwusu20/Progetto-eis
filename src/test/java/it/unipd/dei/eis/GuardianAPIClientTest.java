package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.GuardianAPIClient;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class GuardianAPIClientTest {

   private GuardianAPIClient adapter;
   //private File file;

   @Test
   public void makeApiRequestTest(){
      adapter = new GuardianAPIClient();

      //cartella temporanea dove scaricare la risposta
      adapter.setFilePath("./tempDir/");

      adapter.makeApiRequest(1);
      File directory = new File(adapter.getFilePath());
      File[] files = directory.listFiles();

      assertEquals(1,files.length);

      //elimino la cartella temporanea
      if(!deleteDirectory(new File(adapter.getFilePath())))
         System.err.println("Non è stato possibile eliminare la directory.");
   }

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