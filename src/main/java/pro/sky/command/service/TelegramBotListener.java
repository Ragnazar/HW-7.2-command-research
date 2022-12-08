package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.command.configuration.TelegramBotConfiguration;
import pro.sky.command.exception.ExecuteException;
import pro.sky.command.service.handler.HandlerCallbackQuery;
import pro.sky.command.service.handler.HandlerCommand;
import pro.sky.command.service.handler.HandlerMessages;

import java.util.List;


@Component
@Slf4j
public class TelegramBotListener extends TelegramLongPollingBot {
    private final TelegramBotConfiguration configuration;
    private final HandlerCallbackQuery callbackQuery;
    private final HandlerCommand handlerCommand;
    private final HandlerMessages handlerMessages;

    public TelegramBotListener(TelegramBotConfiguration configuration, HandlerCallbackQuery callbackQuery, HandlerCommand handlerCommand, HandlerMessages handlerMessages) {
        this.configuration = configuration;
        this.callbackQuery = callbackQuery;
        this.handlerCommand = handlerCommand;
        this.handlerMessages = handlerMessages;
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
            } else if (message.hasText()) {
                executeMessage(handlerMessages.handleText(message));
            }
        } else if (update.hasCallbackQuery()) {
            executeMessage(callbackQuery.handleCallbackQuery(update));
        }
    }

    private void executeMessage(Object o) {
        try {
            if (o instanceof BotApiMethod<?>) {
                execute((BotApiMethod<?>) o);
            } else if (o instanceof SendPhoto) {
                execute((SendPhoto) o);
            } else if (o instanceof SendDocument) {
                execute((SendDocument) o);
            } else if (o instanceof List<?>) {
                for (Object m : (List<?>) o) {
                    execute((BotApiMethod<?>) m);
                }
            } else {
                throw new ExecuteException("Что то пошло не так при отправке сообщения. Возможно передан объект не поддерживаемый методом");
            }
        } catch (ExecuteException | TelegramApiException e) {
            log.error("Возникла ошибка при отправке сообщения в телеграм" + e.getMessage());
        }
    }
}
