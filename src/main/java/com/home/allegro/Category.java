package com.home.allegro;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Category {
    BEAUTY("https://allegro.pl/kategoria/uroda?string=bargain_zone&p=%d&bmatch=e2101-d3681-c3682-bea-1-3-0319"),
    SPORTS_AND_TOURISM("https://allegro.pl/kategoria/sport-i-turystyka?string=bargain_zone&p=%d&bmatch=e2101-d3681-c3682-spo-1-4-0319"),
    COLLECTIONS_AND_ART("https://allegro.pl/kategoria/kolekcje-i-sztuka?string=bargain_zone&p=%d&bmatch=nbn-e2101-d3681-c3682-80-col-1-4-0319");

    private final String url;

    Category(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static List<String> getListUrl(){
        return Stream.of(Category.values())
                .map(Category::getUrl)
                .collect(Collectors.toList());
    }
}
