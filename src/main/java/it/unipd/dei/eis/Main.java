package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;
import it.unipd.dei.eis.serialization.Deserialization;
import it.unipd.dei.eis.serialization.Serialization;

public class
   Main {

    public static void main(String[] args) throws CsvValidationException, FileNotFoundException {

        //new InteractiveMenu().runMenu();
        System.out.println("Select choice\n");
        System.out.println("1. Download dei file json del TheGuardian\n");
        System.out.println("2. Serializza i file sorgente in file txt\n");
        System.out.println("3. Analizza gli articoli selezionati\n ");
        System.out.println("4. Esci\n ");

        //lettura scanner di input
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        switch(num){
            case 1:
                //Funzione che fa il download dei file json

            case 2:
                NYTimescsv client1 = new NYTimescsv();
                System.out.println("_______________________________________________");
                client1.loadArrayList();
                //System.out.println(client1.getArrayList());
               // Serialization.serializeArticlesToFile(client1.getArrayList(), "./files/serialize.txt");

               // ArrayList<Article> articles = Deserialization.deserializeFileToArticle("./files/serialize.txt");

               //System.out.println(articles.get(1998));
                System.out.println("_______________________________________________");
                break;
            case 3:
                GuardianAPIClient client2 = new GuardianAPIClient();
                System.out.println("_______________________________________________");

                client2.loadArrayList();
                //System.out.println(client2.getArrayList());
                Serialization.serializeArticlesToFile(client2.getArrayList(), "./files/Serialize_TheGuardian.txt");

                System.out.println("_______________________________________________");
                break;
            default:
                System.out.println("Input invalido\n");
        }
        scanner.close();

    }
}



