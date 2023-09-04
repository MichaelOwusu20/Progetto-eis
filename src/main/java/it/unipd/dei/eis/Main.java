package it.unipd.dei.eis;

import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;

public class Main {

    public static void main(String[] args) throws CsvValidationException, FileNotFoundException {

        //new InteractiveMenu().runMenu();
        System.out.println("Select choice\n");
        System.out.println("1. Analize Article.csv\n");
        System.out.println("2. Analize from TheGuardian\n");

        //lettura scanner di input
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        switch(num){
            case 1:
                NYTimescsv client1 = new NYTimescsv();
                System.out.println("_______________________________________________");
                client1.loadArrayList();
                System.out.println(client1.getArrayList());


                System.out.println("_______________________________________________");
                break;
            case 2:
                GuardianAPIClient client2 = new GuardianAPIClient();
                System.out.println("_______________________________________________");

                client2.loadArrayList();
                //System.out.println(client2.getArrayList());
                //Serialization.SaveArticlesToFile(client2.getArrayList(),2);

                System.out.println("_______________________________________________");
                break;
            default:
                System.out.println("Input invalido\n");
        }
        scanner.close();

    }
}



