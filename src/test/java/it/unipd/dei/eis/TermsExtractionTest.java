package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TermsExtractionTest {

    /*
    private NYTimescsv adapter;

     */

    @Test
    public void extractionTest()
    {
        /*
        adapter = new NYTimescsv();
        adapter.loadArrayList();
        Serialization.serializeArticlesToFile(adapter.getArrayList(), "./Files/input.txt");

         */
        ArrayList<Article> articles = Deserialization.deserializeFileToArticle("./Files/input.txt");
        TermsExtraction.extraction(articles);
       // TermsExtraction.extraction(Deserialization.deserializeFileToArticle("./Files/input.txt"));
    }
}
