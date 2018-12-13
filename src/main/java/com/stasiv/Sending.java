package com.stasiv;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by Sviat on 26.10.2018.
 */
public class Sending extends W_585_bot {
    public void sendThumbnail(Update update, String url, String title){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(title);
        sendPhoto.setPhoto(url);
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public  void sendMsg(Update update, String s){
        if (update.hasCallbackQuery()){
            editMessage(update,s);
        }else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(s);
            sendMessage.disableWebPagePreview();
            try {
                execute(sendMessage);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }
    public void keyboards(Update update, ArrayList<String> comandList){
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboar = new ArrayList<>();

        int i = 0;
        KeyboardRow row = new KeyboardRow();
        for (String comand:  comandList) {
            row.add(comand);
            i++;
            if (i % 2 == 0){
                keyboar.add(row);
                row = new KeyboardRow();
            }
        }
        replyKeyboardMarkup.setKeyboard(keyboar);
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage.setText("Выберите действие из меню");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.disableWebPagePreview();
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void sendMainMenu(Update update){

        SendMessage message = InlineKeyboardBuilder.create(update.getMessage().getChat().getId())
                .setText("Головне меню:")
                .row()
                .button("Підібрати виріб", "menu_main_filter")
                .button("Послуги", "menu_main_service")
                .button("Увесь каталог", "menu_main_catalog")
                .endRow()
                .row()
                .button("Контакти", "menu_main_contacts")
                .button("Написа нам !", "menu_main_send_mail")
                .endRow()
                .build();

        try {
            // Send the message
            sendApiMethod(message);
            //execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void editMessage(Update update, String answer){
        EditMessageText new_message = new EditMessageText()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(toIntExact(update.getCallbackQuery().getMessage().getMessageId()))
                .setText(answer);
        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
