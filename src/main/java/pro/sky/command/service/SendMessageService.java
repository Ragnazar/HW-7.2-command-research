package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
@Slf4j
public class SendMessageService {

    public SendMessage sendMessage(Long chatId, String messageToSend) {
        log.debug("вызван блок для создания исходящего сообщения");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(messageToSend);
        return sendMessage;
    }

    public EditMessageText sendMessageWithKeyboard(Long chatId, String messageToSend, int messageID, List<List<InlineKeyboardButton>> keyboardMarkup) {

        log.debug("вызван блок для создания исходящего сообщения с прикрепленными кнопками");
        EditMessageText message = EditMessageText.builder().chatId(chatId.toString())
                .text(messageToSend).build();
        if (messageID != 0) {
            message.setMessageId(messageID);
        }
        if (keyboardMarkup!=null) {
            message.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMarkup).build());
        }

        return message;
    }
}
