package com.stasiv;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

/**
 * Created by sviatosss on 20.12.2018.
 */
public class QuertySwichs {
    public void quertyMainSwichs(Update update){
        Sending sending = new Sending();
        String querty = UsersManager.getInstance().getQuery(update);
        if (querty.equals("SENDING_EMAIL")){
            sending.sendingMe(update, update.getMessage().getText());
            sending.sendMsg(update, "Ваше повідомлення надіслано !");
            sending.backToMainMenu(update);
            UsersManager.getInstance().updateQuery(update, "");
        }if (querty.equals("input_price")){
            if (!update.getMessage().getText().matches("[0-9]+")){
                sending.sendMsg(update, "Введіть коректне число !!!\nСпробуйте щераз !");
            }else {
                sending.sendMsg(update, "Фільтир успішно доданий");
                ArrayList<Product> arrayList = UsersManager.getInstance().getProductByPrice(Double.parseDouble(update.getMessage().getText()), 0);
                sending.viewProducts(update, arrayList, Double.parseDouble(update.getMessage().getText()), 0);
                UsersManager.getInstance().updateQuery(update, "");
            }
        }

    }
}
