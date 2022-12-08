package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public HandlerCallbackQuery(SendMessageService service, ButtonModService buttonModService,
                                KeyboardMakerService keyboardMaker) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
        this.buttonModService = buttonModService;
    }

    public Object handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callbackData) {
            case "CALL_VOLUNTEER":
                log.debug("вызваа команда /CALL_VOLUNTEER");
                buttonModService.addButtonPress(chatId, true);
                return service.sendMessage(chatId, "В ответе на это сообщение напишите свой вопрос волонтеру", null);

            case "CAT_SHELTER":

                log.debug("вызваа команда /CAT_SHELTER");
                if (buttonModService.addShelterPress(chatId, CAT_SHELTER)) {
                    return service.sendMessageWithReplaceKeyboard(chatId, CAT_SHELTER.getMessage(), messageId, keyboardMaker.shelterKeyboard());
                }
                return service.sendMessage(chatId, "Пользователя нет в базе. Введите команду /start", null);

            case "DOG_SHELTER":

                if (buttonModService.addShelterPress(chatId, DOG_SHELTER)) {
                    log.debug("вызваа команда /DOG_SHELTER");
                    return service.sendMessageWithReplaceKeyboard(chatId, DOG_SHELTER.getMessage(), messageId, keyboardMaker.shelterKeyboard());
                }
                return service.sendMessage(chatId, "Пользователя нет в базе. Введите команду /start", null);
            case "INFO":
                log.debug("вызваа команда /INFO");
                return service.sendMessageWithReplaceKeyboard(chatId, INFO.getMessage(), messageId, keyboardMaker.infoKeyboard());

            case "TAKE_PET":
                log.debug("вызваа команда /TAKE_PET");

                return service.sendMessageWithReplaceKeyboard(chatId, TAKE_PET.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "PET_REPORT":
                log.debug("вызваа команда /PET_REPORT");
                return service.sendPhoto(chatId, PET_REPORT, " xnj nj ", keyboardMaker.shelterKeyboard());
            // return service.sendMessageWithKeyboard(chatId, PET_REPORT.getMessage(), messageId, null);

            default:
                return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.", keyboardMaker.startKeyboard());
        }
    }
}
