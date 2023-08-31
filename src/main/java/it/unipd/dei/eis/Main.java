package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;
import it.unipd.dei.eis.serialization.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws CsvValidationException, FileNotFoundException {

        //new InteractiveMenu().runMenu();
        System.out.println("Select choice\n");
        System.out.println("1. Analize Article.csv\n");
        System.out.println("2. Analize from TheGuardian\n");

        //lettura scanner di input
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        String CSV_FILE_PATH = "/Users/A31542R2Y8/Desktop/beppe/Ny Times Article/nytimes_articles_v2.csv";
        switch(num){
            case 1:
                NYTimescsv client1 = new NYTimescsv();
                System.out.println("_______________________________________________");
                client1.ListofArticle(CSV_FILE_PATH);

                //System.out.println(client1.getArrayList());
                Deserialization.SaveArticlesToFile(client1.getArrayList(),1);

                System.out.println("_______________________________________________");
                break;
            case 2:
                GuardianAPIClient client2 = new GuardianAPIClient();
                System.out.println("_______________________________________________");

                client2.fetchAndPrintArticles();
                //System.out.println(client2.getArrayList());
                Deserialization.SaveArticlesToFile(client2.getArrayList(),2);

                System.out.println("_______________________________________________");
                break;
            default:
                System.out.println("Input invalido\n");
        }
        scanner.close();
    }
}



