package com.stasiv;

import org.telegram.telegrambots.meta.api.objects.Update;

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
        }
    }

    public void switchForMainMenu(Update update){
        String call_data = update.getCallbackQuery().getData();
        if (call_data.equals("menu_main_filter")) {
            sending.editMessage(update, "menu_main_filter");
        }else if (call_data.equals("menu_main_service")) {
            Service service = new Service();
            service.sendAllService(update);
        }else if (call_data.equals("menu_main_catalog")) {
            sending.editMessage(update, "menu_main_catalog");
        }else if (call_data.equals("menu_main_contacts")) {
            sending.editMessage(update, "menu_main_contacts");
        }else if (call_data.equals("menu_main_send_mail")) {
            sending.editMessage(update, "menu_main_send_mail");
        }
    }
}
