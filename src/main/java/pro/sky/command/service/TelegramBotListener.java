package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.command.configuration.TelegramBotConfiguration;
import pro.sky.command.service.handler.HandlerCallbackQuery;
import pro.sky.command.service.handler.HandlerCommand;


@Component
@Slf4j
public class TelegramBotListener extends TelegramLongPollingBot {


    private final TelegramBotConfiguration configuration;
    private final HandlerCallbackQuery callbackQuery;
    private final HandlerCommand handlerCommand;

    public TelegramBotListener(TelegramBotConfiguration configuration, HandlerCallbackQuery callbackQuery, HandlerCommand handlerCommand) {
        this.configuration = configuration;
        this.callbackQuery = callbackQuery;
        this.handlerCommand = handlerCommand;

    }

    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    @Override
    public String getBotToken() {
        return configuration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("вызван блок для получения всех входящих сообщений");
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.hasEntities()) {
               executeMessage(handlerCommand.handleMessage(message));
            }
//вставить обработчик сообщений
        } else if (update.hasCallbackQuery()) {
            executeEditText(callbackQuery.handleCallbackQuery(update));
        }
    }

    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Возникла ошибка при отправке сообщения в телеграм");
        }
    }

    private void executeEditText(EditMessageText sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Возникла ошибка при отправке сообщения в телеграм");
        }
    }
}
