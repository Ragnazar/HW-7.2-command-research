package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.service.ButtonModService;
import pro.sky.command.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HandlerMessages {

    private final SendMessageService service;
    private final ButtonModService modService;
    private final NotificationRepository notificationRepository;

    public HandlerMessages(SendMessageService service, ButtonModService modService, NotificationRepository notificationRepository) {
        this.service = service;
        this.modService = modService;
        this.notificationRepository = notificationRepository;
    }

    public Object handleText(Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        int messageId = message.getMessageId();
        long ci = Const.VOLUNTEER_CHAT_ID;

        if (modService.checkButtonPress(chatId)) {
            notificationRepository.save(new Notification(chatId, text, messageId));
            return CopyMessage.builder().messageId(messageId).fromChatId(chatId).chatId(ci).caption(text).build();
        }
        if (chatId == ci) {
           String textNotification = message.getReplyToMessage().getText();
          List<Notification> notifications= notificationRepository.findAllByText(textNotification);
          List<CopyMessage> messages=new ArrayList<>();
            for (Notification n:notifications       ) {
                notificationRepository.delete(n);
            messages.add( CopyMessage.builder().messageId(messageId).fromChatId(chatId).chatId(n.getId()).caption(text).build());
            }
            return messages;
        }

        return CopyMessage.builder().messageId(messageId).fromChatId(chatId).replyToMessageId(messageId).chatId(ci).caption(text).build();
    }
}
