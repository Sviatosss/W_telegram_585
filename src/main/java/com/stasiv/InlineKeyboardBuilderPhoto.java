package com.stasiv;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sviatosss on 01.12.2018.
 */

public class InlineKeyboardBuilderPhoto {

    private Long chatId;
    private String img;
    private String text;

    private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> row = null;

    private InlineKeyboardBuilderPhoto() {}

    public static InlineKeyboardBuilderPhoto create() {
        InlineKeyboardBuilderPhoto builder = new InlineKeyboardBuilderPhoto();
        return builder;
    }

    public static InlineKeyboardBuilderPhoto create(Long chatId, String img) {
        InlineKeyboardBuilderPhoto builder = new InlineKeyboardBuilderPhoto();
        builder.setChatId(chatId);
        builder.setImg(img);
        return builder;
    }

    public InlineKeyboardBuilderPhoto setText(String text) {
        this.text = text;
        return this;
    }

    public InlineKeyboardBuilderPhoto setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public InlineKeyboardBuilderPhoto row() {
        this.row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardBuilderPhoto button(String text, String callbackData) {
        row.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
        return this;
    }

    public InlineKeyboardBuilderPhoto endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }


    public SendPhoto build() {
        SendPhoto message = new SendPhoto();

        message.setChatId(chatId);
        message.setCaption(text);
        message.setPhoto(img);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

}

