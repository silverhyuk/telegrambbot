package com.silverhyuk.telegrambbot.bot;

import com.silverhyuk.telegrambbot.properties.ProjectProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ZerosllBot extends TelegramLongPollingBot {

    private final ProjectProperties projectProperties;

    public ZerosllBot(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage());
        SendMessage sn = new SendMessage();
        sn.setChatId(update.getMessage().getChatId());
        sn.setText(update.getMessage().getText());
        //sn.setReplyToMessageId(update.getMessage().getMessageId());
        try {
            execute(sn);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return projectProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return projectProperties.getToken();
    }
}
