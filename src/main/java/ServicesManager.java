import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by sviatosss on 01.12.2018.
 */
public class ServicesManager {
    private MongoCollection<Document> mServicesCollection = DataBaseManager.getInstance().getmServicesCollection();

    public Service getService(long id){
        Document query = new Document("id", id);
        Document someService = mServicesCollection.find(query).first();
        Service service = new Service();
        if (someService.getString("priceGold") != null){
             service = new Service(
                    someService.getString("serviceDescription"),
                    someService.getString("priceGold"),
                    someService.getString("priceSilver")
            );
        }else {
             service = new Service(
                    someService.getString("serviceDescription"),
                    someService.getString("priceInfo")
            );
        }
        return service;
    }

    public ArrayList<Service> getServiceArrayList(){
        ArrayList<Service> serviceArrayList = new ArrayList<>();
        for (Document currentQuest : mServicesCollection.find()) {
            serviceArrayList.add(getService(currentQuest.getLong("id")));
        }
        return serviceArrayList;
    }
    public void addService(String s){
        final String REGEX = "\n";
        String[] strings = s.split(REGEX);

        int index = 0;
        for (String serviseInfo: strings){
            strings[index] = serviseInfo.trim();
            index++;
        }

        long id;
        if (mServicesCollection.count() == 0){
            id = 0;
        }else id = mServicesCollection.count();

        if (strings.length == 2){
            Document newService = new Document("id", id)
                    .append("serviceDescription", strings[0])
                    .append("priceGold", null)
                    .append("priceSilver", null)
                    .append("priceInfo", strings[1]);
            mServicesCollection.insertOne(newService);
        }else {
            Document newService = new Document("id", id)
                    .append("serviceDescription", strings[0])
                    .append("priceGold", strings[1]+"грн.")
                    .append("priceSilver", strings[2]+"грн.")
                    .append("priceInfo", null);
            mServicesCollection.insertOne(newService);
        }
    }
}
