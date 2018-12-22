package com.stasiv;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

/**
 * Created by sviatosss on 24.11.2018.
 */
public class Product {
    public String productName;
    public String productWeight;
    public String productInsert;
    public String productImg;
    public String productCategoryId;
    public double productPrice;

    public Product(String productName, String productWeight, String productInsert, String productImg, String productCategoryId) {
        this.productName = productName;
        this.productWeight = productWeight;
        this.productInsert = productInsert;
        this.productImg = productImg;
        this.productCategoryId = productCategoryId;
    }
    public Product(String productName, String productWeight, String productInsert, String productImg, String productCategoryId, double productPrice) {
        this.productName = productName;
        this.productWeight = productWeight;
        this.productInsert = productInsert;
        this.productImg = productImg;
        this.productCategoryId = productCategoryId;
        this.productPrice = productPrice;
    }
    @Override
    public String toString() {
        return "Імя продукту: " + productName + "\n" +
                "Вага: " + productWeight + "\n" +
                "Вставки: " + productInsert + "\n" +
                "Картинка: " + getSmallImg(productImg) + "\n" +
                "Категорія: " + productCategoryId + "\n" +
                "Ціна: " + productPrice + "\n*******************************\n";
    }
    public static String getSmallImg(String productImg){
        return productImg.replace("catalog", "res");
    }
}
