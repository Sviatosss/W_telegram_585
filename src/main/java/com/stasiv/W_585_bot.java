package com.stasiv;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Created by Sviat on 25.10.2018.
 */
public class W_585_bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        Sending sending = new com.stasiv.Sending();

//        Service service = new Service();
//        service.sendAllService(update);

//        com.stasiv.ServicesManager servicesManager = new  com.stasiv.ServicesManager();
//        servicesManager.addService(update.getMessage().getText());

        if (update.hasMessage() && update.getMessage().hasText()) {
            String s = update.getMessage().getText();
            UsersManager.getInstance().issetUser(update);
            if (s.equals("/start")){
                sending.sendMainMenu(update);
            }else if (UsersManager.getInstance().getQuery(update) != null){
                QuertySwichs quertySwichs = new QuertySwichs();
                quertySwichs.quertyMainSwichs(update);
            }
        } else if (update.hasCallbackQuery()) {
            System.out.println(update.getCallbackQuery().getData());
            MyCallBackQuerty.getInstance().switchByMenus(update);
        }



//        if (update.getMessage().getText().equals("country")){
//            CountryManager.getInstance().printAllCountries();
//        }else {
//            com.stasiv.Product.viewProduct(update, com.stasiv.UsersManager.getInstance().getProductByPrice(Float.parseFloat(update.getMessage().getText())));
//        }
//        if (update.hasMessage()){
//            String result = com.stasiv.UsersManager.getInstance().getNameByChat(update.getMessage().getChat());
//            System.out.printf(result);
//            messages.sendMsg(update, "Hi, I can help you");
//        }
    }
    public String getBotUsername() {
        return "com.stasiv.W_585_bot";
    }
    public String getBotToken() {
        return "766701035:AAE0xQEZR_eCG_oUdjig678AWHKmcbOPrLE";
    }
}
