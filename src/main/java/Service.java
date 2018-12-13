import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

import static java.util.Objects.isNull;

/**
 * Created by sviatosss on 01.12.2018.
 */
public class Service {
    public String serviceDescription;
    public String priceGold;
    public String priceSilver;
    public String priceInfo;

    public Service(){
        //service
    }
    public Service(String serviceDescription, String priceGold, String priceSilver) {
        this.serviceDescription = serviceDescription;
        this.priceGold = priceGold;
        this.priceSilver = priceSilver;
    }

    public Service(String serviceDescription, String priceInfo) {
        this.serviceDescription = serviceDescription;
        this.priceInfo = priceInfo;
    }
    public void sendAllService(Update update){
        ServicesManager servicesManager = new ServicesManager();
        ArrayList<Service> serviceArrayList = servicesManager.getServiceArrayList();
        for (Service service : serviceArrayList){
            sendService(update, service);
        }
    }
    public void sendService(Update update, Service service){
        Sending sending = new Sending();
        if (isNull(service.priceInfo)){
            sending.sendMsg(update, service.serviceDescription + "\n" + "Золото - " + service.priceGold + "\n" + "Срібло - " + service.priceSilver);
        }else {
            sending.sendMsg(update, service.serviceDescription + "\n" + service.priceInfo);
        }
    }
}