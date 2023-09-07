package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.*;
import it.unipd.dei.eis.serialization.Deserialization;
import it.unipd.dei.eis.serialization.Serialization;
import org.junit.Test;

import java.util.ArrayList;


public class AppTest 
{


    @Test
    public void desTest() {
        ArrayList<Article> articles = Deserialization.deserializeFileToArticle("./Files/input.txt");
        System.out.println(articles.get(0).stringToAnalyze());
    }
}
