package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
public class HandlerMessages {

    public void handleText (Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        long userChatId = message.getFrom().getId();

    }
}
