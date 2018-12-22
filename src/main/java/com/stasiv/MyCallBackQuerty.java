package com.stasiv;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

/**
 * Created by sviatosss on 05.12.2018.
 */
public class MyCallBackQuerty {
    private static MyCallBackQuerty sInstance;

    public static MyCallBackQuerty getInstance() {
        if (sInstance == null) {
            sInstance = new MyCallBackQuerty();
        }
        return sInstance;
    }

    Sending sending = new Sending();

    public void switchByMenus(Update update){
        String call_data = update.getCallbackQuery().getData();
        if (call_data.contains("menu_main")){
            switchForMainMenu(update);
        }else if (call_data.contains("catalog_page=")){
            int page = Integer.parseInt(call_data.replace("catalog_page=", ""));
            ArrayList<Product> arrayList = UsersManager.getInstance().getProduct(page);
            sending.viewProducts(update, arrayList, page);
        }else if (call_data.contains("service_page=")){
            int page = Integer.parseInt(call_data.replace("service_page=", ""));
            Service service = new Service();
            String title = service.sendService(page);
            sending.servicePadination(update, page, title);
        }else if(call_data.contains("buy_product_")){
            sending.sendingMe(update, "Замовлення - " + call_data.replace("buy_product_", ""));
            sending.sendMsg(update,"Спасибі за замовлення !");
            sending.backToMainMenu(update);
        }else if(call_data.contains("product_filter_")){
            switchByFilters(update);
        }
    }
    public void switchForMainMenu(Update update){
        String call_data = update.getCallbackQuery().getData();
        if (call_data.contains("menu_main_view")){
            sending.sendMainMenu(update);
        }else if (call_data.equals("menu_main_filter")) {
            sending.sendingFilters(update);
        }else if (call_data.equals("menu_main_service")) {
            Service service = new Service();
            String title = service.sendService(0);
            sending.servicePadination(update, 0, title);
        }else if (call_data.equals("menu_main_catalog")) {
            ArrayList<Product> arrayList = UsersManager.getInstance().getProduct(0);
            sending.viewProducts(update, arrayList, 0);
        }else if (call_data.equals("menu_main_contacts")) {
            sending.sendingContacts(update);
        }else if (call_data.equals("menu_main_send_mail")) {
            UsersManager.getInstance().updateQuery(update, "SENDING_EMAIL");
            sending.sendMsg(update, "Введіть повідомлення");
        }
    }
    public void switchByFilters(Update update){
        String call_data = update.getCallbackQuery().getData();
        if (call_data.equals("product_filter_cat")){
            sending.sendingCatFilters(update);
        }else if (call_data.equals("product_filter_price")){
            UsersManager.getInstance().updateQuery(update, "input_price");
            sending.editMessage(update, "Введіть ціну до якої показувати вироби");
        } else if(call_data.contains("product_filter_cat=")){
            String[] catANDpage = call_data.split("&");
            String cat = catANDpage[0].split("=")[1];
            int page = Integer.parseInt(catANDpage[1].replace("page=", ""));
            ArrayList<Product> arrayList = UsersManager.getInstance().getProductByCat(cat, page);
            sending.viewProducts(update, arrayList, cat, page);
        }else if (call_data.contains("product_filter_price=")){
            String[] priceANDpage = call_data.split("&");
            double price = Double.parseDouble(priceANDpage[0].split("=")[1]);
            int page = Integer.parseInt(priceANDpage[1].replace("page=", ""));
            ArrayList<Product> arrayList = UsersManager.getInstance().getProductByPrice(price, page);
            sending.viewProducts(update, arrayList, price, page);
        }
    }
}
