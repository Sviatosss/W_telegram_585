package com.stasiv;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class UsersManager {

    private static UsersManager sInstance;

    /*
     * Database constants
     */
    private static final String DB_HOST = "ds115094.mlab.com";
    private static final int DB_PORT = 15094;
    private static final String DB_NAME = "heroku_c1w1rh5p";
    private static final String DB_USER = "Sviatosss";
    private static final String DB_PASSWORD = "Sviat0sss_pAss";

    private static final String DB_URL = "mongodb://" + DB_USER + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private MongoCollection<Document> mUsersCollection;
    private MongoCollection<Document> mProductsCollection;

    private UsersManager() {
        // Більше інформації по роботі з MongoDB: https://www.baeldung.com/java-mongodb
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase db = client.getDatabase(DB_NAME);
        mUsersCollection = db.getCollection("users");
        mProductsCollection = db.getCollection("products");
    }

    public static UsersManager getInstance() {
        if (sInstance == null) {
            sInstance = new UsersManager();
        }

        return sInstance;
    }

    public void issetUser(Update update) {
        Long id = Functions.getInstance().getId(update);
        Document query = new Document("id", id.toString());
        Document user = mUsersCollection.find(query).first();

        if (user == null) {
            Document newUser = new Document("id", update.getMessage().getChat().getId().toString())
                    .append("firstName", update.getMessage().getChat().getFirstName())
                    .append("lastName", update.getMessage().getChat().getLastName())
                    .append("username", update.getMessage().getChat().getUserName())
                    .append("current_querty", null);
            mUsersCollection.insertOne(newUser);
        }
    }
    public void updateQuery(Update update, String newQuest){
        String id = Functions.getInstance().getId(update).toString();
        mUsersCollection.updateOne(eq("id", id), new Document("$set", new Document("current_query", newQuest)));
    }
    public String getQuery(Update update){
        return getUserInfoString(update, "current_query");
    }
    public String getUserInfoString(Update update, String field){
        String id = Functions.getInstance().getId(update).toString();
        Document query = new Document("id", id);
        Document user = mUsersCollection.find(query).first();
        return user.getString(field);
    }

    public String getUserData(String id){
        Document query = new Document("id", id);
        Document user = mUsersCollection.find(query).first();
        return "user - " + "@" + user.getString("username") + "\nfirstName - " + user.getString("firstName");
    }

    public String saveProduct(Product product){
        Document query = new Document("name", product.productName);
        Document someProduct = mProductsCollection.find(query).first();

        if (someProduct == null) {
            Document newProduct = new Document("name", product.productName)
                    .append("weight", product.productWeight)
                    .append("insert", product.productInsert)
                    .append("img_name", product.productImg.replace("/catalog/", ""))
                    .append("img_url", "http://modelit.com.ua" + product.productImg)
                    .append("category_id", product.productCategoryId);
            mProductsCollection.insertOne(newProduct);
            return "OK... we add - " + product.productName;
        }
        return "False";

    }
    public String updatePrice(){
        for (Document cursor : mProductsCollection.find()) {
            String weight = cursor.getString("weight");
            weight = weight.replace(" гр", "");
            float fWeight = Float.parseFloat(weight);
            float price =  fWeight * 1000f;
            cursor.put("price", price);
            System.out.println(price);
            System.out.println(cursor.toJson() + "\n");

            Document query = new Document("name", cursor.get("name"));
            Document someProduct = mProductsCollection.find(query).first();
            mProductsCollection.deleteOne(someProduct);
            mProductsCollection.insertOne(cursor);
        }
        return "ok";
    }
    public ArrayList<Product> getProductByPrice(double price, int page){
        ArrayList<Product> arrayListProducts = new ArrayList<Product>();
        int iteration = 0;
        for (Document currentProduct : mProductsCollection.find()) {
            if(currentProduct.getDouble("price") <= price){
                if (page == iteration){
                    Product product = new Product(
                            currentProduct.getString("name"),
                            currentProduct.getString("weight"),
                            currentProduct.getString("insert"),
                            currentProduct.getString("img_url"),
                            currentProduct.getString("category_id"),
                            currentProduct.getDouble("price")
                    );
                    arrayListProducts.add(product);
                    break;
                }
                iteration++;
            }
        }
        return arrayListProducts;
    }
    public ArrayList<Product> getProductByCat(String cat, int page){
        ArrayList<Product> arrayListProducts = new ArrayList<>();
        int iteration = 0;
        for (Document currentProduct : mProductsCollection.find()) {
            if(currentProduct.getString("category_id").equals(cat)){
                if (page == iteration){
                    Product product = new Product(
                            currentProduct.getString("name"),
                            currentProduct.getString("weight"),
                            currentProduct.getString("insert"),
                            currentProduct.getString("img_url"),
                            currentProduct.getString("category_id"),
                            currentProduct.getDouble("price")
                    );
                    arrayListProducts.add(product);
                    break;
                }
                iteration++;
            }
        }
        return arrayListProducts;
    }

    public ArrayList<Product> getProduct(int page){
        ArrayList<Product> arrayListProducts = new ArrayList<>();
        int iteration = 0;
        for (Document currentProduct : mProductsCollection.find()) {
            if (iteration == page){
                Product product = new Product(
                        currentProduct.getString("name"),
                        currentProduct.getString("weight"),
                        currentProduct.getString("insert"),
                        currentProduct.getString("img_url"),
                        currentProduct.getString("category_id"),
                        currentProduct.getDouble("price")
                );
                arrayListProducts.add(product);
                break;
            }
            iteration++;
        }
        return arrayListProducts;
    }
}
