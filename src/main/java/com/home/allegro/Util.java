package com.home.allegro;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Util {
    private static List<Map<String, String>> productListWithDiscount = new ArrayList<>();
    private static Map<String, List<Map<String, String>>> productListWithDiscountFromThreeCategory = new HashMap<>();

    public static void writeToFile(){
        try(FileWriter fw= new FileWriter("NewData.csv", false)){
            for(String url: Category.getListUrl()){
                fw.write(getOneHundredProductFromOneCategory(url));
                fw.append('\n');

                fw.flush();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static String getOneHundredProductFromOneCategory(String url){
        for (int i = 1; i < 100; i++) {
            if (productListWithDiscount.size() < 100){
                getInformFromPage(String.format(url, i));
            }
        }
//        System.out.println(productListWithDiscount.size());
        return productListWithDiscount.toString();
    }

    @SneakyThrows
    public static void getInformFromPage(String url) {
        Map<String, String> productsWithDiscountInfoMap = new LinkedHashMap<>();
        Document doc = Jsoup.connect(url).get();
        StringBuilder sb = new StringBuilder();

        Elements elements = doc.body().getElementsByClass("mpof_ki myre_zn _9c44d_1Hxbq");

        for (Element element : elements) {
            if(element.getElementsByClass("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv").hasText()){
//                productsWithDiscountInfoMap.put("Name", element.getElementsByClass("mgn2_14 m9qz_yp mqu1_16 mp4t_0 m3h2_0 mryx_0 munh_0").text());
//                    System.out.println("Name: " + element.getElementsByClass("mgn2_14 m9qz_yp mqu1_16 mp4t_0 m3h2_0 mryx_0 munh_0").text());
                sb.append("Additional info: ").append(getState(element)).append("; ");
                sb.append("Old price: ").append(element.getElementsByClass("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv").text()).append("; ");
                sb.append("New price: ").append(element.getElementsByClass("_1svub _lf05o").text()).append("; ");
                sb.append("Full price with courier: ").append(element.getElementsByClass("mqu1_g3").text()).append("; ");
                sb.append("People bought: ").append(getNumberCustomer(element.getElementsByClass("mpof_ki m389_6m munh_56_l").text())).append("\n");
//                productsWithDiscountInfoMap.put("Additional info", getState(element));
////                    System.out.println("Additional info: " + getState(element));
//                productsWithDiscountInfoMap.put("Old price", element.getElementsByClass("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv").text());
////                    System.out.println("Old price: " + element.getElementsByClass("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv").text());
//                productsWithDiscountInfoMap.put("New price", element.getElementsByClass("_1svub _lf05o").text());
////                    System.out.println("New price: " + element.getElementsByClass("_1svub _lf05o").text());
//                productsWithDiscountInfoMap.put("Full price with courier", element.getElementsByClass("mqu1_g3").text());
////                    System.out.println("Full price with courier: " + element.getElementsByClass("mqu1_g3").text());
//                productsWithDiscountInfoMap.put("People bought", getNumberCustomer(element.getElementsByClass("mpof_ki m389_6m munh_56_l").text()));
////                    System.out.println("People bought: " + getNumberCustomer(element.getElementsByClass("mpof_ki m389_6m munh_56_l").text()));
//                    System.out.println("_______________");
                productsWithDiscountInfoMap.put(element.getElementsByClass("mgn2_14 m9qz_yp mqu1_16 mp4t_0 m3h2_0 mryx_0 munh_0").text(), sb.toString());
                productListWithDiscount.add(productsWithDiscountInfoMap);
                sb.setLength(0);
                if(productListWithDiscount.size() == 100) break;
            }
        }
//        System.out.println(productListWithDiscount.size());
//        return productListWithDiscount.toString();
    }

    private static String getNumberCustomer(String text){
        if(text.startsWith("hit")){
            return text.substring(3);
        }else return text;
    }

    private static String getState(Element element){
        Map<String, String> additionalInfoMap = new LinkedHashMap<>();
        Elements elementsByClass = element.getElementsByClass("mpof_uk mgmw_ag mp4t_0 m3h2_0 mryx_0 munh_0 mg9e_0 mvrt_0 mj7a_0 mh36_0 _9c44d_3hPFO");
        for (Element elem: elementsByClass){
            if(elem.hasClass("mpof_uk mgmw_ag mp4t_0 m3h2_0 mryx_0 munh_0 mg9e_0 mvrt_0 mj7a_0 mh36_0 _9c44d_3hPFO")){
                additionalInfoMap.put(elem.text(), elem.nextElementSibling().text());
            }
        }
        return additionalInfoMap.toString();
    }
}
