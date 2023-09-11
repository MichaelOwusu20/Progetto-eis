package it.unipd.dei.eis;

import java.util.Scanner;
import it.unipd.dei.eis.adapters.GuardianAPIClient;
import it.unipd.dei.eis.adapters.NYTimescsv;

import it.unipd.dei.eis.serialization.Serialization;

/**
 * Esecuzione del programma.
 */
public class Main {


    public static void main(String[] args) {

        int num;
        Scanner scanner = new Scanner(System.in);

        // Variabile di controllo : serve a capire se è già stata fatta la serializzazione degli articoli del The Guardian
        //True--> è già stato serializzato
        boolean the_guardian=false;
        // Variabile di controllo : serve a capire se è già stata fatta la serializzazione degli articoli del NewYorkTimes
        //True--> è già stato serializzato
        boolean nyt=false;
        //Variabile di controllo : serve a capire se il file serialize.txt debba essere sovrascritto oppure no
        //true--> deve essere sovrascritto
        boolean overwrite=true;      

        do {

            System.out.println("Select choice\n");
            System.out.println("1. Download dei file json del TheGuardian\n");
            System.out.println("2. Serializza i file sorgente in file txt\n");
            System.out.println("3. Analizza gli articoli selezionati\n ");
            System.out.println("4. Fa il download , la serializzazione e l'analisi degli articoli del The Guardian\n ");
            System.out.println("5. Esci\n ");

            //lettura scanner di input
            num = scanner.nextInt();



            switch (num) {
                case 1:

                    System.out.println("_______________________________________________");
                    GuardianAPIClient client = new GuardianAPIClient();
                    //Metodo per fare il download degli articoli del The Guardian
                    client.downloadTheGuardian();
                    System.out.println("---Download eseguito con successo---");
                    System.out.println("_______________________________________________");
                    break;


                case 2:

                    System.out.println("_______________________________________________");
                    System.out.println("Quale sorgente vuoi serializzare? ");
                    int n;

                    do
                    {

                        System.out.println("1.The Guardian");
                        System.out.println("2.NewYorkTimes");
                        System.out.println("3.Go back");

                        n=scanner.nextInt();

                        switch(n)
                        {
                            case 1:
                                try {
                                    //Controllo se sono già stati serializzati gli articoli del The Guardian
                                    if (the_guardian) {
                                        System.out.println("Hai già serializzato gli articoli del The Guardian, per favore scegli un'altra opzione.");
                                        break;
                                    }
                                    GuardianAPIClient client1 = new GuardianAPIClient();
                                    client1.loadArrayList();
                                    //La variabile di controllo overwrite mi determina se il file verrà sovrascritto
                                    Serialization.serializeArticlesToFile(client1.getArrayList(), "./Files/serialize.txt", overwrite);
                                    System.out.println("----Gli articoli del The Guardian sono stati serializzati----");
                                    //Dopo aver scritto nel file serialize.txt non voglio che venga sovrascritto
                                    overwrite = false;
                                    //modifico la variabile the_guardian dopo la prima serializzazione degli articoli
                                    the_guardian = true;
                                } catch(NullPointerException e) {
                                    System.out.println("----E' necessario eseguire il download degli articoli prima di serializzarli----");
                                }
                                break;
                            case 2:
                                //Controllo se sono già stati serializzati gli articoli del NewYorkTimes
                                if (nyt)
                                {
                                    System.out.println("Hai già serializzato gli articoli del NewYorkTimes, per favore scegli un'altra opzione.");
                                    break;
                                }
                                NYTimescsv client2= new NYTimescsv();
                                client2.loadArrayList();
                                //La variabile di controllo overwrite mi determina se il file verrà sovrascritto
                                Serialization.serializeArticlesToFile(client2.getArrayList(), "./Files/serialize.txt",overwrite);
                                System.out.println("----Gli articoli del NewYorkTimes sono stati serializzati----");
                                //Dopo aver scritto nel file serialize.txt non voglio che venga sovrascritto
                                overwrite=false;
                                //modifico la variabile nyt dopo la prima serializzazione degli articoli
                                nyt=true;
                                break;
                            case 3 :
                                break;
                        }
                    }while (n!=3);


                    System.out.println("_______________________________________________");
                    break;

                case 3:

                    System.out.println("_______________________________________________");
                    //Faccio l'analisi dei file serializzati e contenuti nel file serialize.txt
                    //Il risulato verrà scritto in output.txt
                    TermsExtraction.extraction("./Files/serialize.txt", "./Files/output.txt");

                    System.out.println("_______________________________________________");
                    break;

                case 4 :

                    System.out.println("_______________________________________________");
                    GuardianAPIClient client4 = new GuardianAPIClient();
                    //Metodo per fare il download degli articoli del The Guardian
                    client4.downloadTheGuardian();
                    System.out.println("---Download eseguito con successo---");

                    //Controllo se sono già stati serializzati gli articoli del The Guardian
                    if (the_guardian) {
                        System.out.println("Hai già serializzato gli articoli del The Guardian, per favore scegli un'altra opzione.");
                        break;
                    }

                    client4.loadArrayList();
                    //La variabile di controllo overwrite mi determina se il file verrà sovrascritto
                    Serialization.serializeArticlesToFile(client4.getArrayList(), "./Files/serialize.txt", overwrite);
                    System.out.println("----Gli articoli del The Guardian sono stati serializzati----");
                    //Dopo aver scritto nel file serialize.txt non voglio che venga sovrascritto
                    overwrite = false;
                    //modifico la variabile the_guardian dopo la prima serializzazione degli articoli
                    the_guardian = true;

                    //Faccio l'analisi dei file serializzati e contenuti nel file serialize.txt
                    //Il risulato verrà scritto in output.txt
                    TermsExtraction.extraction("./Files/serialize.txt", "./Files/output.txt");


                    System.out.println("_______________________________________________");
                    break;

                case 5 :

                    System.out.println("Arrivederci ...");
                    break;
                default:
                    System.out.println("Input invalido\n");
            }
        }while (num!=5);

        scanner.close();
    }
}