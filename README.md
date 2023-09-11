## Progetto EIS

***

### Panoramica

Il programma permette di estrarre e visualizzare i termini più importanti
nell'insieme degli articoli resi disponibili da testate giornalistiche online in 
diverse sorgenti.

I file in cui sono contenuti gli articoli possono essere di formato differente
(in questo caso .json per gli articoli del The Guardian e .csv per quelli del NY Times) 
pertanto vengono passati alla classe Adapter, la quale converte ogni 
articolo in un oggetto standard di tipo Article e lo carica in un arraylist di articoli.
Questa classe è astratta quindi risulterà facile implementare una sottoclasse 
che permetta di lavorare con sorgenti differenti da quelle presentate nel progetto.

Il software permette inoltre, mediante la classe Serialization, di salvare l'insieme degli
articoli presenti nell'arraylist in un unico file testo. In questo modo tutti gli articoli 
provenienti da sorgenti differenti presenteranno la stessa estensione .txt.

L'estrazione delle parole più importanti avviene nella classe TermsExtraction,
la quale prende come input il file con i testi serializzati, lo converte in 
un arraylist di oggetti Article mediante la classe Deserialization e infine analizza 
i vari articoli contenuti al suo interno. Verrà prodotto un file testo chiamato "output.txt" 
contenente l'elenco delle 50 parole affiancato dal numero di volte in cui ognuna di
esse compare nel file.

***

### Istruzioni per la compilazione e l'esecuzione
L'intero progetto deve essere compilato, di conseguenza all'interno della zip scaricata
non è ancora presente il file jar.
Per compilare il progetto è necessario spostarsi nella directory principale
(quella in cui è presente il file pom.xml) e digitare da terminale :

```
mvn package
```

Al completamento della compilazione del progetto e dell'esecuzione dei vari test
 bisogna entrare nella cartella "target" e spostare
il file jar "progettoEis-1.0-SNAPSHOT.jar" nella directory principale.
Questa azione può essere eseguita anche da terminale con il comando:

```
mv ./target/progettoEis-1.0-SNAPSHOT.jar ./
```
Ora il file jar si trova nella directory corretta e il programma 
può essere eseguito digitando: 

```
java -jar progettoEis-1.0-SNAPSHOT.jar
```

Per visualizzare la documentazione è sufficiente spostarsi nella cartella "javadoc"
ed aprire il file "index.html".

***

### Librerie utilizzate

json


csv

junit 


