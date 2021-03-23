package com.home.allegro;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.home.allegro.HTMLClassName.*;

public class Util {

    private Util() {
    }

    private static final Map<String, Map<String, String>> productMapWithDiscount = new HashMap<>();

    public static void writeToFile() {
        try (FileWriter fw = new FileWriter("NewData.csv", false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String url : Category.getListUrl()) {
                productMapWithDiscount.put(getCategoryNameFromURLString(url), getOneHundredProductFromOneCategory(url));
            }
            bw.write(productMapWithDiscount.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, String> getOneHundredProductFromOneCategory(String url) {
        Map<String, String> productsWithDiscountInfoMap = new LinkedHashMap<>();
        for (int i = 1; i < 100; i++) {
            if (productsWithDiscountInfoMap.size() < 100) {
                addingInfoAboutDiscountProductToProductList(String.format(url, i), productsWithDiscountInfoMap);
            } else break;
        }
        return productsWithDiscountInfoMap;
    }

    public static void addingInfoAboutDiscountProductToProductList(String url, Map<String, String> productsWithDiscountInfoMap) {
        StringBuilder sb = new StringBuilder();

        for (Element element : getProductElementsFromPage(url)) {
            if (element.getElementsByClass(CROSSED_OUT_STRING.getClassName()).hasText()) {
                sb.append("Additional info: ").append(getAdditionalInfoAboutOneProduct(element)).append("; ");
                sb.append("Old price: ").append(element.getElementsByClass(CROSSED_OUT_STRING.getClassName()).text()).append("; ");
                sb.append("New price: ").append(element.getElementsByClass(RIGHT_PRICE.getClassName()).text()).append("; ");
                sb.append("Full price with delivery: ").append(element.getElementsByClass(FULL_PRICE_WITH_DELIVERY.getClassName()).text()).append("; ");
                sb.append("People bought: ").append(getRightStringWithNumberCustomer(element.getElementsByClass(NUMBER_CUSTOMERS.getClassName()).text())).append("; ");
                sb.append("Product reference: ").append(element.getElementsByClass(REFERENCE_TO_PRODUCT_PAGE.getClassName()).attr("href")).append("\n");
                productsWithDiscountInfoMap.put(element.getElementsByClass(PRODUCT_NAME.getClassName()).text(), sb.toString());
                sb.setLength(0);
                if (productsWithDiscountInfoMap.size() == 100) break;
            }
        }
    }

    private static String getRightStringWithNumberCustomer(String text) {
        if (text.startsWith("hit")) {
            return text.substring(3);
        } else return text;
    }

    private static String getAdditionalInfoAboutOneProduct(Element element) {
        Map<String, String> additionalInfoMap = new LinkedHashMap<>();
        Elements elementsByClass = element.getElementsByClass(ADDITIONAL_INFO.getClassName());
        for (Element elem : elementsByClass) {
            if (elem.hasClass(ADDITIONAL_INFO.getClassName())) {
                additionalInfoMap.put(elem.text(), elem.nextElementSibling().text());
            }
        }
        return additionalInfoMap.toString();
    }

    @SneakyThrows
    private static Elements getProductElementsFromPage(String url) {
        return Jsoup.connect(url).get().body().getElementsByClass("mpof_ki myre_zn _9c44d_1Hxbq");
    }

    private static String getCategoryNameFromURLString(String url) {
        String[] arraySplittingByDownHill = url.split("/");
        String[] arraySplittingByQuestionMark = arraySplittingByDownHill[arraySplittingByDownHill.length - 1].split("\\?");
        return arraySplittingByQuestionMark[0].toUpperCase();
    }

}
