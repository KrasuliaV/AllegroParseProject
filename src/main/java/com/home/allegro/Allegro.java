package com.home.allegro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Allegro {
    public static void main(String[] args) {
        Document doc;
        String title = " ";
        try {
            doc = Jsoup.connect("http://google.com/").get();
            title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("After parsing, Title : " + title);
    }


}
