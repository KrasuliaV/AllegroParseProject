package com.home.allegro;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.home.allegro.HTMLClassName.*;

public class Util {
    private static List<Map<String, String>> productListWithDiscount = new ArrayList<>();

    public static void writeToFile() {
        try (FileWriter fw = new FileWriter("NewData.csv", false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String url : Category.getListUrl()) {
                bw.write(getOneHundredProductFromOneCategory(url));
                bw.append('\n');
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getOneHundredProductFromOneCategory(String url) {
        for (int i = 1; i < 100; i++) {
            if (productListWithDiscount.size() < 100) {
                addingInfoAboutDiscountProductToProductList(String.format(url, i));
            } else break;
        }
        return productListWithDiscount.toString();
    }

    public static void addingInfoAboutDiscountProductToProductList(String url) {
        StringBuilder sb = new StringBuilder();

        for (Element element : getProductElementsFromPage(url)) {
            if (element.getElementsByClass(CROSSED_OUT_STRING.getClassName()).hasText()) {
                sb.append("Additional info: ").append(getAdditionalInfoAboutOneProduct(element)).append("; ");
                sb.append("Old price: ").append(element.getElementsByClass(CROSSED_OUT_STRING.getClassName()).text()).append("; ");
                sb.append("New price: ").append(element.getElementsByClass(RIGHT_PRICE.getClassName()).text()).append("; ");
                sb.append("Full price with delivery: ").append(element.getElementsByClass(FULL_PRICE_WITH_DELIVERY.getClassName()).text()).append("; ");
                sb.append("People bought: ").append(getRightStringWithNumberCustomer(element.getElementsByClass(NUMBER_CUSTOMERS.getClassName()).text())).append("\n");
                Map<String, String> productsWithDiscountInfoMap = new LinkedHashMap<>();
                productsWithDiscountInfoMap.put(element.getElementsByClass(PRODUCT_NAME.getClassName()).text(), sb.toString());
                productListWithDiscount.add(productsWithDiscountInfoMap);
                sb.setLength(0);
                if (productListWithDiscount.size() == 100) break;
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
}
