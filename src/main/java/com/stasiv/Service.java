package com.stasiv;

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
    public String sendService(int page){
        ServicesManager servicesManager = new ServicesManager();
        ArrayList<Service> serviceArrayList = servicesManager.getServiceArrayList();
        int iteration = 0;
        for (Service service : serviceArrayList){
            if (iteration == page){
                return sendService(service);
            }
            iteration++;
        }
        return "";
    }
    public String sendService(Service service){
        if (isNull(service.priceInfo)){
            return service.serviceDescription + "\n" + "Золото - " + service.priceGold + "\n" + "Срібло - " + service.priceSilver;
        }else {
            return service.serviceDescription + "\n" + service.priceInfo;
        }
    }
}