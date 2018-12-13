import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Returns user name + nickname based on chat
     * @param chat {@link Chat} - contains current chat with user
     * @return first name + last name + nickname
     */
    public String getNameByChat(Chat chat) {
        Document query = new Document("id", chat.getId().toString());

        Document user = mUsersCollection.find(query).first();

        if (user == null) {
            Document newUser = new Document("id", chat.getId().toString())
                    .append("firstName", chat.getFirstName())
                    .append("lastName", chat.getLastName())
                    .append("username", chat.getUserName());
            mUsersCollection.insertOne(newUser);
            return "Вас не було в базі користувачів, але ми вас добавили ;-)\n";
        }

        String firstName = user.getString("firstName");
        String lastName = user.getString("lastName");
        String username = user.getString("username");

        // Більше інформації по стилізації тексту в Телеграмі: https://core.telegram.org/bots/api#markdown-style
        return "*" + firstName + " " + lastName + "*, нікнейм: *" + username + "*\n";
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
    public ArrayList<Product> getProductByPrice(double price){
        ArrayList<Product> arrayListProducts = new ArrayList<Product>();
        for (Document currentProduct : mProductsCollection.find()) {
            if(currentProduct.getDouble("price") < price){
                Product product = new Product(
                        currentProduct.getString("name"),
                        currentProduct.getString("weight"),
                        currentProduct.getString("insert"),
                        currentProduct.getString("img_url"),
                        currentProduct.getString("category_id"),
                        currentProduct.getDouble("price")
                );
                arrayListProducts.add(product);
                System.out.println(product);
            }
        }
        return arrayListProducts;
    }
}
