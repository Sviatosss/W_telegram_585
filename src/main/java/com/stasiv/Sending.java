package com.stasiv;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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
        sendPhoto.setChatId(Functions.getInstance().getId(update).toString());
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public  void sendMsg(Update update, String s){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(Functions.getInstance().getId(update).toString());
        sendMessage.setText(s);
        sendMessage.disableWebPagePreview();
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
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
        if ((update.hasMessage() && update.getMessage().getText().equals("/start")) || update.getCallbackQuery().getData().equals("menu_main_view_send")){
            Long id = Functions.getInstance().getId(update);
            SendMessage message = InlineKeyboardBuilder.create(id)
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
        }else {
            int massageId = Functions.getInstance().getMassageId(update);
            Long id = Functions.getInstance().getId(update);
            EditMessageText message = InlineKeyboardBuilderUpdata.create(id, massageId)
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
                execute(message);
                //execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void backToMainMenu(Update update){
        Long id = Functions.getInstance().getId(update);
        SendMessage message = InlineKeyboardBuilder.create(id)
                .setText("Повернутись до меню:")
                .row()
                .button("Назад до меню", "menu_main_view")
                .endRow()
                .build();
        try {
            sendApiMethod(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void backToMainMenu(Update update, String title){
        int massageId = Functions.getInstance().getMassageId(update);
        Long id = Functions.getInstance().getId(update);
        EditMessageText message = InlineKeyboardBuilderUpdata.create(id, massageId)
                .setText(title + "\n\nПовернутись до меню:")
                .row()
                .button("Назад до меню", "menu_main_view")
                .endRow()
                .build();
        try {
            sendApiMethod(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendingContacts(Update update){
        String contact =  "Адресса:\n" +
                "м.Львів, вул. Ніжинська, 5.\n" +
                "\n" +
                "Контактний телефон:\n" +
                "(067) 679-23-74\n" +
                "\n" +
                "Електронна пошта:\n" +
                "workshop585@gmail.com ";
        backToMainMenu(update, contact);
    }

    public void sendingMe(Update update, String info){
        String from = UsersManager.getInstance().getUserData(Functions.getInstance().getId(update).toString());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId("623547574");
        sendMessage.setText(from + "\n\n" + info);
        sendMessage.disableWebPagePreview();
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void viewProducts(Update update, ArrayList<Product> products, int page){
        Long id = Functions.getInstance().getId(update);
        for (Product product: products){

            int nextPage = page + 1;
            int prevPage = page - 1;
            if (page == 0){
                prevPage = page;
            }
            SendPhoto sendPhoto = InlineKeyboardBuilderPhoto.create(id, product.productImg)
                    .setText("\uD83C\uDFF7 " + product.productName +
                                    "\n⚙\tВага: " + product.productWeight + "\n" +
                                    "\uD83D\uDC8D\tКатегорія: " + changeCategory(product.productCategoryId) + "\n" +
                                    "\uD83D\uDC8E\tВставки: " + product.productInsert + "\n" +
                                    "\uD83D\uDCB0\tЦіна: " + product.productPrice + "\n\n")
                    .row()
                    .button("Змаовити виріб - " +  product.productName, "buy_product_" + product.productName)
                    .endRow()
                    .row()
                    .button("Попередній виріб", "catalog_page=" + prevPage)
                    .button("Настуний виріб", "catalog_page=" + nextPage)
                    .endRow()
                    .row()
                    .button("Назад до меню", "menu_main_view_send")
                    .endRow()
                    .build();
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void viewProducts(Update update, ArrayList<Product> products, String cat, int page){
        Long id = Functions.getInstance().getId(update);
        for (Product product: products){

            int nextPage = page + 1;
            int prevPage = page - 1;
            if (page == 0){
                prevPage = page;
            }
            SendPhoto sendPhoto = InlineKeyboardBuilderPhoto.create(id, product.productImg)
                    .setText("\uD83C\uDFF7 " + product.productName +
                            "\n⚙\tВага: " + product.productWeight + "\n" +
                            "\uD83D\uDC8D\tКатегорія: " + changeCategory(product.productCategoryId) + "\n" +
                            "\uD83D\uDC8E\tВставки: " + product.productInsert + "\n" +
                            "\uD83D\uDCB0\tЦіна: " + product.productPrice + "\n\n")
                    .row()
                    .button("Змаовити виріб - " +  product.productName, "buy_product_" + product.productName)
                    .endRow()
                    .row()
                    .button("Попередній виріб", "product_filter_cat="+ cat +"&page=" + prevPage)
                    .button("Настуний виріб", "product_filter_cat="+ cat +"&page=" + nextPage)
                    .endRow()
                    .row()
                    .button("Назад до меню", "menu_main_view_send")
                    .endRow()
                    .build();
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void viewProducts(Update update, ArrayList<Product> products, double price, int page){
        Long id = Functions.getInstance().getId(update);
        for (Product product: products){

            int nextPage = page + 1;
            int prevPage = page - 1;
            if (page == 0){
                prevPage = page;
            }
            SendPhoto sendPhoto = InlineKeyboardBuilderPhoto.create(id, product.productImg)
                    .setText("\uD83C\uDFF7 " + product.productName +
                            "\n⚙\tВага: " + product.productWeight + "\n" +
                            "\uD83D\uDC8D\tКатегорія: " + changeCategory(product.productCategoryId) + "\n" +
                            "\uD83D\uDC8E\tВставки: " + product.productInsert + "\n" +
                            "\uD83D\uDCB0\tЦіна: " + product.productPrice + "\n\n")
                    .row()
                    .button("Змаовити виріб - " +  product.productName, "buy_product_" + product.productName)
                    .endRow()
                    .row()
                    .button("Попередній виріб", "product_filter_price="+ price +"&page=" + prevPage)
                    .button("Настуний виріб", "product_filter_price="+ price +"&page=" + nextPage)
                    .endRow()
                    .row()
                    .button("Назад до меню", "menu_main_view_send")
                    .endRow()
                    .build();
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public static String changeCategory(String productCategoryId){
        int categoryId = Integer.parseInt(productCategoryId);
        String categoryName;
        switch (categoryId){
            case 1: categoryName = "Перстні з великими вставками"; break;
            case 2: categoryName = "Перстні діамантової групи"; break;
            case 3: categoryName = "Перстні європейської групи"; break;
            case 4: categoryName = "Перстні цирконієвої групи"; break;
            case 5: categoryName = "Перстні перлинової групи"; break;
            case 6: categoryName = "Перстні чоловічої групи"; break;
            case 7: categoryName = "Сережки діамантової групи"; break;
            case 8: categoryName = "Сережки цирконієвої групи"; break;
            case 9: categoryName = "Ексклюзивні вироби"; break;
            case 10: categoryName = "Перстні обручальної групи"; break;
            case 11: categoryName = "Сережки перлинової групи"; break;
            case 12: categoryName = "Каталог попопулярних моделей"; break;
            case 13: categoryName = "Каталох моделей браслетів"; break;
            case 14: categoryName = "Каталог моделей ладанок"; break;
            default: categoryName = "";
        }
        return categoryName;
    }
    public void servicePadination(Update update, int page, String title){
        Long id = Functions.getInstance().getId(update);
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        int nextPage = page + 1;
        int prevPage = page - 1;
        if (page == 0){
            prevPage = page;
        }
        EditMessageText message = InlineKeyboardBuilderUpdata.create(id, messageId)
                .setText(title + "\n\nНавігація:")
                .row()
                .button("Попередня послуга", "service_page=" + prevPage)
                .button("Настуна паслуга", "service_page=" + nextPage)
                .endRow()
                .row()
                .button("Назад до меню", "menu_main_view")
                .endRow()
                .build();
        try {
            execute(message);
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
    public void sendingFilters(Update update){
        Long id = Functions.getInstance().getId(update);
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageText message = InlineKeyboardBuilderUpdata.create(id, messageId)
                .setText("Фільтир:")
                .row()
                .button("За ціною", "product_filter_price")
                .button("За категорією", "product_filter_cat")
                .endRow()
                .row()
                .button("Назад до меню", "menu_main_view")
                .endRow()
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendingCatFilters(Update update){
        Long id = Functions.getInstance().getId(update);
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageText message = InlineKeyboardBuilderUpdata.create(id, messageId)
                .setText("Категорії:")
                .row()
                .button(changeCategory("1"), "product_filter_cat=1&page=0")
                .button(changeCategory("2"), "product_filter_cat=2&page=0")
                .endRow()
                .row()
                .button(changeCategory("3"), "product_filter_cat=3&page=0")
                .button(changeCategory("4"), "product_filter_cat=4&page=0")
                .endRow()
                .row()
                .button(changeCategory("5"), "product_filter_cat=5&page=0")
                .button(changeCategory("6"), "product_filter_cat=6&page=0")
                .endRow()
                .row()
                .button(changeCategory("7"), "product_filter_cat=7&page=0")
                .button(changeCategory("8"), "product_filter_cat=8&page=0")
                .endRow()
                .row()
                .button(changeCategory("9"), "product_filter_cat=9&page=0")
                .button(changeCategory("10"), "product_filter_cat=10&page=0")
                .endRow()
                .row()
                .button(changeCategory("11"), "product_filter_cat=11&page=0")
                .button(changeCategory("12"), "product_filter_cat=12&page=0")
                .endRow()
                .row()
                .button(changeCategory("13"), "product_filter_cat=13&page=0")
                .button(changeCategory("14"), "product_filter_cat=14&page=0")
                .endRow()
                .row()
                .button("Назад до меню", "menu_main_view")
                .endRow()
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
