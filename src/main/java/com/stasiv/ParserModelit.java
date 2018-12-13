package com.stasiv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
/**
 * Created by sviatosss on 24.11.2018.
 */
public class ParserModelit {
    public static void saveParsing(String parserUrl, String categoryId){
        org.jsoup.nodes.Document document = null;
        try {
            document = Jsoup.connect(parserUrl).timeout(6000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements wrapBlocks = document.select("div.container.main ul.products");
        for (Element blockProduct: wrapBlocks.select("li.product-item")) {
            String productInsert;
            String productWeight;

            Element thumbnailWrap = blockProduct.selectFirst("a.fancybox");
            String productImg = thumbnailWrap.attr("href");
            String productName = thumbnailWrap.selectFirst("span.products-article").text();

            if (productName.equals("КЕ-746")){
                productWeight = "3.8 гр";
            }else productWeight = blockProduct.selectFirst("div.products-wt").text();

            Element some_productInsert = blockProduct.selectFirst("div.products-insert ul");
            if (some_productInsert == null){
                 productInsert = "";
            }else  productInsert = some_productInsert.text();

            String productCategoryId = categoryId;

            Product product = new Product(productName,productWeight,productInsert,productImg,productCategoryId);
            System.out.printf(product.toString());

            String result = UsersManager.getInstance().saveProduct(product);
            System.out.println(result);
        }
    }
}
