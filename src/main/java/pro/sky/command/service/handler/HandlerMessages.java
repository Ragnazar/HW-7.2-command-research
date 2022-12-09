package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.service.CheckedService;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static pro.sky.command.constants.BotMessageEnum.CALL_VOLUNTEER;
import static pro.sky.command.constants.BotMessageEnum.START;

@Service
@Slf4j
public class HandlerMessages {

    private final SendMessageService service;
    private final CheckedService checkedService;
    private final NotificationRepository notificationRepository;
    private final KeyboardMakerService keyboardMaker;

    public HandlerMessages(SendMessageService service, CheckedService checkedService, NotificationRepository notificationRepository, KeyboardMakerService keyboardMaker) {
        this.service = service;
        this.checkedService = checkedService;
        this.notificationRepository = notificationRepository;
        this.keyboardMaker = keyboardMaker;
    }

    public Object handleText(Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        int messageId = message.getMessageId();

        if (text.equals(START.getNameButton())) {
          checkedService.clearPressButton(chatId);
            return service.sendMessage(chatId, START.getMessage(), keyboardMaker.startKeyboard());
        }

        if (text.equals(CALL_VOLUNTEER.getNameButton())) {
            checkedService.addVolunteerButtonPress(chatId,true);
            return service.sendMessage(chatId, CALL_VOLUNTEER.getMessage(),null);
        }

        if (checkedService.checkVolunteerButtonPress(chatId)) {
            notificationRepository.save(new Notification(chatId, text, messageId));
            return CopyMessage.builder().messageId(messageId).fromChatId(chatId).chatId(Const.VOLUNTEER_CHAT_ID).caption(text).build();
        }
        if (chatId .equals(Const.VOLUNTEER_CHAT_ID)) {
            String textNotification = message.getReplyToMessage().getText();
            List<Notification> notifications = notificationRepository.findAllByText(textNotification);
            List<CopyMessage> messages = new ArrayList<>();
            for (Notification n : notifications) {
                notificationRepository.delete(n);
                messages.add(CopyMessage.builder().messageId(messageId).replyToMessageId(n.getMessageId()).fromChatId(chatId).chatId(n.getId()).caption(text).build());
            }
            return messages;
        }

        //  return CopyMessage.builder().messageId(messageId).fromChatId(chatId).replyToMessageId(messageId).chatId(ci).caption(text).build();
        return null;
    }
}
