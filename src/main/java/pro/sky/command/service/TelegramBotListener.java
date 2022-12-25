package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.command.configuration.TelegramBotConfiguration;
import pro.sky.command.constants.Const;
import pro.sky.command.exception.ExecuteException;
import pro.sky.command.service.handler.HandlerCallbackQuery;
import pro.sky.command.service.handler.HandlerCommand;
import pro.sky.command.service.handler.HandlerMessages;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectories;


@Component
@Slf4j
@Transactional
public class TelegramBotListener extends TelegramLongPollingBot {
    private final TelegramBotConfiguration configuration;
    private final HandlerCallbackQuery callbackQuery;
    private final HandlerCommand handlerCommand;
    private final HandlerMessages handlerMessages;
    private final ReportService reportService;
    private final KeyboardMakerService keyboardMaker;

    public TelegramBotListener(TelegramBotConfiguration configuration, HandlerCallbackQuery callbackQuery, HandlerCommand handlerCommand, HandlerMessages handlerMessages, ReportService reportService, KeyboardMakerService keyboardMaker) {
        this.configuration = configuration;
        this.callbackQuery = callbackQuery;
        this.handlerCommand = handlerCommand;
        this.handlerMessages = handlerMessages;
        this.reportService = reportService;
        this.keyboardMaker = keyboardMaker;
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
            } else if (message.hasPhoto()) {
                String text = message.getCaption();
                long chatId = message.getChatId();

                int firstDelimiter = text.indexOf(' ');
                String dataReport = null;
                if (firstDelimiter > 0) {
                    dataReport = text.substring(0, firstDelimiter);
                    text = text.substring(firstDelimiter + 1);
                }

                if (dataReport.matches(Const.PATTERN_DATA) & text.matches(Const.PATTERN_PET_ID)) {

                    int i = 0;
                    for (PhotoSize photoSize : update.getMessage().getPhoto()) {
                        i++;
                        GetFile getFile = new GetFile(photoSize.getFileId());
                        Path filePath = Path.of(configuration.getReportPhotoPath(), "отчет" + dataReport + ".jpg");
                        try {
                            File file = execute(getFile);
                            byte[] fileToByte = Files.readAllBytes(downloadFile(file).toPath());
                            while (i == 2) {
                                i++;
                                createDirectories(filePath.getParent());
                                Files.deleteIfExists(filePath);
                                Files.write(filePath, fileToByte);
                                executeMessage(SendMessage.builder().chatId(chatId).text(reportService.addReport(chatId, dataReport, text, filePath.toString()))
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMaker.reportKeyboard()).build()).build());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
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
                    if (m instanceof SendPhoto) {
                        execute((SendPhoto) m);
                    } else if (m instanceof BotApiMethod<?>) {
                        execute((BotApiMethod<?>) m);
                    }
                }
            } else {
                throw new ExecuteException("Что то пошло не так при отправке сообщения. Возможно передан объект не поддерживаемый методом");
            }
        } catch (ExecuteException | TelegramApiException e) {
            log.error("Возникла ошибка при отправке сообщения в телеграм" + e.getMessage());
        }
    }
}
