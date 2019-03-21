package com.silverhyuk.telegrambbot.bot;

import com.silverhyuk.telegrambbot.properties.ProjectProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class ZerosllBot extends TelegramLongPollingBot {

    Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProjectProperties projectProperties;

    public ZerosllBot(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String userLastName = Optional.ofNullable(message.getFrom().getLastName()).orElse("");
        String userFirstName = Optional.ofNullable(message.getFrom().getFirstName()).orElse("");

        log.info("{} : {}", userLastName+userFirstName, message.getText());
        //System.out.println(update.getMessage());
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
