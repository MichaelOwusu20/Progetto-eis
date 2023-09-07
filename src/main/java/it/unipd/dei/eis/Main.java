package it.unipd.dei.eis;

import java.util.Scanner;
import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;

import it.unipd.dei.eis.serialization.Serialization;

public class Main {

    public static void main(String[] args) {

        int num;
        Scanner scanner = new Scanner(System.in);

        do {
            //new InteractiveMenu().runMenu();
            System.out.println("Select choice\n");
            System.out.println("1. Download dei file json del TheGuardian\n");
            System.out.println("2. Serializza i file sorgente in file txt\n");
            System.out.println("3. Analizza gli articoli selezionati\n ");
            System.out.println("4. Esci\n ");

            //lettura scanner di input
            num = scanner.nextInt();
            //scanner.close();


            switch (num) {
                case 1:

                    System.out.println("_______________________________________________");
                    GuardianAPIClient client = new GuardianAPIClient();
                    client.downloadTheGuardian();
                    System.out.println("---Download eseguito con successo---");
                    System.out.println("_______________________________________________");
                    break;


                case 2:

                    System.out.println("_______________________________________________");
                    System.out.println("Quale sorgente vuoi serializzare? ");
                    int n;
                    boolean the_guardian=false;
                    boolean nyt=false;
                    do
                    {

                        System.out.println("1.The Guardian");
                        System.out.println("2.NewYorkTimes");
                        System.out.println("3.Go back");

                        n=scanner.nextInt();

                        switch(n)
                        {
                            case 1:
                                if (the_guardian){
                                    System.out.println("Hai già serializzato gli articoli del The Guardian, per favore scegli un'altra opzione.");
                                    break;
                                }
                                GuardianAPIClient client1 = new GuardianAPIClient();
                                client1.loadArrayList();
                                Serialization.serializeArticlesToFile(client1.getArrayList(), "./Files/serialize.txt");
                                System.out.println("----Gli articoli del The Guardian sono stati serializzati----");
                                break;
                            case 2:
                                if (nyt)
                                {
                                    System.out.println("Hai già serializzato gli articoli del NewYorkTimes, per favore scegli un'altra opzione.");
                                    break;
                                }
                                NYTimescsv client2= new NYTimescsv();
                                client2.loadArrayList();
                                Serialization.serializeArticlesToFile(client2.getArrayList(), "./Files/serialize.txt");
                                System.out.println("----Gli articoli del NewYorkTimes sono stati serializzati----");
                                break;
                            case 3 :
                                break;
                        }
                    }while (n!=3);


                    System.out.println("_______________________________________________");
                    break;

                case 3:

                    System.out.println("_______________________________________________");
                    TermsExtraction.extraction("./Files/serialize.txt");
                    System.out.println("Articoli analizzati ");
                    System.out.println("_______________________________________________");
                    break;
                case 4 :

                    System.out.println("Arrivederci ...");
                    break;
                default:
                    System.out.println("Input invalido\n");
            }
        //    scanner.close();
        }while (num!=4);

        scanner.close();

    }
}



