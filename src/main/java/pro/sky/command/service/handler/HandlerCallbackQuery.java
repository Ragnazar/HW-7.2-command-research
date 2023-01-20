package pro.sky.command.service.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.service.CheckedService;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;

import java.util.List;

import static pro.sky.command.constants.BotMessageEnum.*;

/**
 * Класс для обработки комманд после нажатия кнопок.
 * формирует ответные сообщения
 *
 * @autor Шилова Наталья
 */
@Service
@Slf4j
public class HandlerCallbackQuery {
    private final SendMessageService service;
    private final CheckedService checkedService;
    private final KeyboardMakerService keyboardMaker;

    /**
     * Конструктор зависимостей
     *
     * @see SendMessageService
     * @see CheckedService
     * @see KeyboardMakerService
     */
    public HandlerCallbackQuery(SendMessageService service, CheckedService checkedService, KeyboardMakerService keyboardMaker) {
        this.service = service;
        this.checkedService = checkedService;
        this.keyboardMaker = keyboardMaker;
    }

    /**
     * Обрабатывает команды поступающие от нажатия кнопок.
     * реагирует кнопки из класса
     *
     * @param update сообщение из чата пользователя
     * @return возвращает объект который может быть отправлен в телеграм с помощью метода execute.
     * @see BotMessageEnum
     */
    public Object handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callbackData) {
            case "CAT_SHELTER":

                log.debug("вызваа команда /CAT_SHELTER");
                if (checkedService.addShelterPress(chatId, CAT_SHELTER)) {
                    return service.sendMessageWithReplaceKeyboard(chatId, CAT_SHELTER.getMessage(), messageId, keyboardMaker.shelterKeyboard());
                }
                return service.sendMessage(chatId, "Пользователя нет в базе. Введите команду /start", null);

            case "DOG_SHELTER":

                if (checkedService.addShelterPress(chatId, DOG_SHELTER)) {
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
                return service.sendMessageWithReplaceKeyboard(chatId, PET_REPORT.getMessage(), messageId, keyboardMaker.reportKeyboard());
            //  //меню информации о приюте
            case "SHELTER_DATA":
                log.debug("вызваа команда /SHELTER_DATA");
                return service.sendPhoto(chatId, SHELTER_DATA, SHELTER_DATA.getMessage(), keyboardMaker.infoKeyboard());
            case "RULES_SAFETY":
                log.debug("вызваа команда /RULES_SAFETY");
                return service.sendPhoto(chatId, RULES_SAFETY, RULES_SAFETY.getMessage(), keyboardMaker.infoKeyboard());
            case "SHELTER_INFO":
                log.debug("вызваа команда /SHELTER_INFO");
                return service.sendPhoto(chatId, SHELTER_INFO, SHELTER_INFO.getMessage(), keyboardMaker.infoKeyboard());
            case "TAKE_PASS":
                log.debug("вызваа команда /TAKE_PASS");
                checkedService.addVolunteerButtonPress(chatId, true);
                return service.sendMessage(chatId, TAKE_PASS.getMessage(), keyboardMaker.infoKeyboard());
            case "TAKE_DATA_FOR_CONTACT":
                log.debug("вызваа команда /TAKE_DATA_FOR_CONTACT");
                checkedService.addVolunteerButtonPress(chatId, true);
                return service.sendMessage(chatId, TAKE_DATA_FOR_CONTACT.getMessage(), keyboardMaker.infoKeyboard());
            //Меню взять питомца
            case "RULES_SHELTER":
                log.debug("вызваа команда /RULES_SHELTER");
                //здесь вставить метод для отправки файла
                return service.sendMessageWithReplaceKeyboard(chatId, RULES_SHELTER.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "DOCUMENTS":
                log.debug("вызваа команда /DOCUMENTS");
                //здесь вставить метод для отправки файла
                return service.sendMessageWithReplaceKeyboard(chatId, DOCUMENTS.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "TRANSPORTATION":
                log.debug("вызваа команда /TRANSPORTATION");
                //здесь вставить метод для отправки файла
                return service.sendMessageWithReplaceKeyboard(chatId, TRANSPORTATION.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "DOG_HANDLERS":
                log.debug("вызваа команда /DOG_HANDLERS");
                //здесь вставить метод для отправки файла
                return service.sendMessageWithReplaceKeyboard(chatId, DOG_HANDLERS.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "REASONS_REFUSAL":
                log.debug("вызваа команда /REASONS_REFUSAL");
                //здесь вставить метод для отправки файла
                return service.sendMessageWithReplaceKeyboard(chatId, REASONS_REFUSAL.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "SETTLE":
                log.debug("вызваа команда /SETTLE");
                return service.sendMessageWithReplaceKeyboard(chatId, SETTLE.getMessage(), messageId, keyboardMaker.settleKeyboard());
            case "LITTLE_PET":
                log.debug("вызваа команда /LITTLE_PET");
                return service.sendMessageWithReplaceKeyboard(chatId, LITTLE_PET.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "BIG_PET":
                log.debug("вызваа команда /BIG_PET");
                return service.sendMessageWithReplaceKeyboard(chatId, BIG_PET.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));
            case "DISABILITY_PET":
                log.debug("вызваа команда /DISABILITY_PET");
                return service.sendMessageWithReplaceKeyboard(chatId, DISABILITY_PET.getMessage(), messageId, keyboardMaker.takePetKeyboard(chatId));

            case "PHOTO":
                log.debug("вызваа команда /PHOTO");
                if (checkedService.addReportPress(chatId, PHOTO)) {
                    return service.sendMessage(chatId, PHOTO.getMessage(), keyboardMaker.reportKeyboard());
                }
            case "DIET":
                log.debug("вызваа команда /DIET");
                if (checkedService.addReportPress(chatId, DIET)) {
                    return service.sendMessage(chatId, DIET.getMessage(), keyboardMaker.reportKeyboard());
                }
            case "HEALTH":
                log.debug("вызваа команда /HEALTH");
                if (checkedService.addReportPress(chatId, HEALTH)) {
                    return service.sendMessage(chatId, HEALTH.getMessage(), keyboardMaker.reportKeyboard());
                }
            case "BEHAVIOR":
                log.debug("вызваа команда /BEHAVIOR");
                if (checkedService.addReportPress(chatId, BEHAVIOR)) {
                    return service.sendMessage(chatId, BEHAVIOR.getMessage(), keyboardMaker.reportKeyboard());
                }

                return service.sendMessage(chatId,"Пользователь с таким номером не найден. Обратитесь к волантеру.", keyboardMaker.reportKeyboard());
            case "TEST_PERIOD":
                log.debug("вызваа команда /TEST_PERIOD");
               List<String> count = checkedService.getReportCount(chatId);
                if (count==null){
                    return service.sendMessage(chatId, " Ознакомьтесь с информацией о тестовом периоде.\n   "+
                            TEST_PERIOD.getMessage()+"\n оставшийся тестовый период не удалось получить." +
                            " Если вы зарегистрированы и взяли питомца обратитесь к волонтерам и опишите проблему. ", keyboardMaker.reportKeyboard());
                }
                StringBuilder answer =new StringBuilder("\n Вам осталось прислать " );
                for (String s:count) {
                    answer.append(s);
                }answer.append(" Верно заполненных и подтвержденных отчетов. Если вы обнаружили не точность сообщите волонтеру.");

                return service.sendMessage(chatId, TEST_PERIOD.getMessage()+answer , keyboardMaker.reportKeyboard());
            default:
                return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.", keyboardMaker.startKeyboard());
        }
    }
}
