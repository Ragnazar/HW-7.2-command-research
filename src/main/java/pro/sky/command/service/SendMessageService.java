package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import pro.sky.command.constants.BotMessageEnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@Slf4j
public class SendMessageService {
    @Value("staticFiles/images/")
    private String path;
    private final CheckedService checkedService;

    public SendMessageService(CheckedService checkedService) {
        this.checkedService = checkedService;
    }

    public SendMessage sendMessage(Long chatId, String messageToSend, List<List<InlineKeyboardButton>> keyboardMarkup) {
        log.debug("вызван блок для создания исходящего сообщения");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(messageToSend);
        if (keyboardMarkup != null) {
            sendMessage.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMarkup).build());
        }
        return sendMessage;
    }

    public EditMessageText sendMessageWithReplaceKeyboard(Long chatId, String messageToSend, int messageID, List<List<InlineKeyboardButton>> keyboardMarkup) {

        log.debug("вызван блок для создания исходящего сообщения с прикрепленными кнопками");
        EditMessageText message = EditMessageText.builder().chatId(chatId.toString())
                .text(messageToSend).build();
        if (messageID != 0) {
            message.setMessageId(messageID);
        }
        if (keyboardMarkup != null) {
            message.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMarkup).build());
        }

        return message;
    }

    public Object sendPhoto(Long chatId, BotMessageEnum name, String messageToSend, List<List<InlineKeyboardButton>> keyboardMarkup) {
        log.debug("вызван блок для создания исходящего сообщения image");
        String nameShelter = checkedService.checkShelterPress(chatId);
        if (nameShelter.equals("")) {
            return sendMessage(chatId, "Вы не выбрали приют по которому хотите получить информацию. Нажмите главное меню и выберите приют", null);
        }
        String pathToSend = "classpath:" +path + nameShelter + "_" + name + ".jpg";
        SendPhoto sendPhoto = createSendPhoto(chatId,pathToSend);
        if (sendPhoto == null) {
            return sendMessage(chatId, "Возможно файл с информацией не найден. Нажмите главное меню и сообшите пожалуйста волонтеру об ошибке.", null);
        }
        if (messageToSend != null) {
            sendPhoto.setCaption(messageToSend);
        }
        if (keyboardMarkup != null) {
            sendPhoto.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMarkup).build());
        }

        return sendPhoto;
    }


    public SendPhoto createSendPhoto(Long chatId, String path) {
        SendPhoto sendPhoto = new SendPhoto();
        try {
            File image = ResourceUtils.getFile( path);
            InputFile inputFile = new InputFile(image);
            sendPhoto.setPhoto(inputFile);
            sendPhoto.setChatId(String.valueOf(chatId));
        } catch (FileNotFoundException e) {
            log.error("Возникла ошибка файл не найден. путь " +  path);
            return null;
        }
        return sendPhoto;
    }
}
