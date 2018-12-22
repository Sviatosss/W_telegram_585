package com.stasiv;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sviatosss on 01.12.2018.
 */

public class InlineKeyboardBuilderUpdata {

    private Long chatId;
    private int messageId;
    private String text;

    private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> row = null;

    private InlineKeyboardBuilderUpdata() {}

    public static InlineKeyboardBuilderUpdata create() {
        InlineKeyboardBuilderUpdata builder = new InlineKeyboardBuilderUpdata();
        return builder;
    }

    public static InlineKeyboardBuilderUpdata create(Long chatId, int messageId) {
        InlineKeyboardBuilderUpdata builder = new InlineKeyboardBuilderUpdata();
        builder.setChatId(chatId);
        builder.setMessageId(messageId);
        return builder;
    }

    public InlineKeyboardBuilderUpdata setText(String text) {
        this.text = text;
        return this;
    }

    public InlineKeyboardBuilderUpdata setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public InlineKeyboardBuilderUpdata row() {
        this.row = new ArrayList<>();
        return this;
    }

    public InlineKeyboardBuilderUpdata button(String text, String callbackData) {
        row.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
        return this;
    }

    public InlineKeyboardBuilderUpdata endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }


    public EditMessageText build() {
        EditMessageText message = new EditMessageText();

        message.setChatId(chatId);
        message.setText(text);
        message.setMessageId(messageId);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

}

