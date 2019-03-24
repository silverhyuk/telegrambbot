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

/**
 * @see "https://github.com/rubenlagus/TelegramBotsExample/tree/master/src/main/java/org/telegram/updateshandlers"
 */
@Component
public class ZerosllBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProjectProperties projectProperties;
    private final String MY_ID_COMMAND = "/my_id";

    public ZerosllBot(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String userLastName = Optional.ofNullable(message.getFrom().getLastName()).orElse("");
        String userFirstName = Optional.ofNullable(message.getFrom().getFirstName()).orElse("");

        log.info("{} : {}", userLastName+userFirstName, message.getText());
        System.out.println(update.getMessage());
        executeSendMessage(update);
    }

    private void executeSendMessage(Update update) {
        SendMessage sn = new SendMessage();
        sn.setChatId(update.getMessage().getChatId());
        String text = Optional.ofNullable(update.getMessage().getText()).orElse("empty");
        if(MY_ID_COMMAND.equals(text)) {
            sn.setText("내아이디 : " + Long.toString(update.getMessage().getChatId()));
        }else{
            sn.setText(text);
        }


        //sn.setReplyToMessageId(update.getMessage().getMessageId());
        try {
            super.execute(sn);
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
