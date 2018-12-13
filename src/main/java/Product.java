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
    public static void viewProduct(Update update, ArrayList<Product> products){
        Sending sending = new Sending();
        for (Product product: products){
            sending.sendThumbnail(update, product.productImg, product.productName);
            sending.sendMsg(update, "⚙\tВага: " + product.productWeight + "\n" +
                    "\uD83D\uDC8D\tКатегорія: " + changeCategory(product.productCategoryId) + "\n" +
                    "\uD83D\uDC8E\tВставки: " + product.productInsert + "\n" +
                    "\uD83D\uDCB0\tЦіна: " + product.productPrice);
        }
    }
    public static String changeCategory(String productCategoryId){
        int categoryId = Integer.parseInt(productCategoryId);
        String categoryName;
        switch (categoryId){
            case 1: categoryName = "Перстні з великими вставками"; break;
            case 2: categoryName = "Перстні діамантової групи"; break;
            case 3: categoryName = "Перстні європейської групи"; break;
            case 4: categoryName = "Перстні цирконієвої групи"; break;
            case 5: categoryName = "Перстні перлинової групи"; break;
            case 6: categoryName = "Перстні чоловічої групи"; break;
            case 7: categoryName = "Сережки діамантової групи"; break;
            case 8: categoryName = "Сережки цирконієвої групи"; break;
            case 9: categoryName = "Ексклюзивні вироби"; break;
            case 10: categoryName = "Перстні обручальної групи"; break;
            case 11: categoryName = "Сережки перлинової групи"; break;
            case 12: categoryName = "Каталог попопулярних моделей"; break;
            case 13: categoryName = "Каталох моделей браслетів"; break;
            case 14: categoryName = "Каталог моделей ладанок"; break;
            default: categoryName = "";
        }
        return categoryName;
    }
    public static String getSmallImg(String productImg){
        return productImg.replace("catalog", "res");
    }
}
