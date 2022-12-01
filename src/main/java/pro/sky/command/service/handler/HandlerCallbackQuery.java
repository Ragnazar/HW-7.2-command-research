package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.command.service.ButtonModService;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;

import static pro.sky.command.constants.BotMessageEnum.*;

@Service
@Slf4j
public class HandlerCallbackQuery {
    private final SendMessageService service;
    private final ButtonModService buttonModService;
    private final KeyboardMakerService keyboardMaker;

    public HandlerCallbackQuery(SendMessageService service, ButtonModService buttonModService, KeyboardMakerService keyboardMaker) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
        this.buttonModService = buttonModService;
    }

    public EditMessageText handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callbackData) {
            case "CALL_VOLUNTEER":
                log.debug("вызваа команда /start");
                //Нужно добавить метод который будет вызывать волонтера или добавлять вопрос в базу.
               return service.sendMessageWithKeyboard(chatId, CALL_VOLUNTEER.getMessage(), messageId, null);

            case "CAT_SHELTER":
                log.debug("вызваа команда /CAT_SHELTER");

                buttonModService.setButton(chatId, CAT_SHELTER);
                return service.sendMessageWithKeyboard(chatId, CAT_SHELTER.getMessage(), messageId, keyboardMaker.shelterKeyboard());

            case "DOG_SHELTER":
                log.debug("вызваа команда /DOG_SHELTER");

                buttonModService.setButton(chatId, DOG_SHELTER);
                return service.sendMessageWithKeyboard(chatId, DOG_SHELTER.getMessage(), messageId, keyboardMaker.shelterKeyboard());

            case "TAKE_PET":
                log.debug("вызваа команда /TAKE_PET");

                return service.sendMessageWithKeyboard(chatId, TAKE_PET.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));

            case "INFO":
                log.debug("вызваа команда /INFO");

                return service.sendMessageWithKeyboard(chatId, INFO.getMessage(), messageId, keyboardMaker.infoKeyboard());

            case "PET_REPORT":
                log.debug("вызваа команда /PET_REPORT");

                return service.sendMessageWithKeyboard(chatId, PET_REPORT.getMessage(), messageId, null);

            default:
                return service.sendMessageWithKeyboard(chatId, "Извините, данная команда пока не поддерживается.", messageId, null);
        }
    }
}
