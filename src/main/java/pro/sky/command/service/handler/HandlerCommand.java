package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;

import java.util.Optional;

import static pro.sky.command.constants.BotMessageEnum.HELP_MESSAGE;

@Service
@Slf4j
public class HandlerCommand {
    private final SendMessageService service;
    private final KeyboardMakerService keyboardMaker;

    public HandlerCommand(SendMessageService service, KeyboardMakerService keyboardMaker) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
    }

    public SendMessage handleMessage(Message message) {
        log.debug("вызов блок для извлечения из сообщения и обработки команды");
        long chatId = message.getChatId();
        Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();

        String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());


        switch (command) {
            case "/start":
                log.debug("вызваа команда /start");
                return startCommand(chatId, message.getFrom().getFirstName());
            default:
                return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.");
        }
    }


    public SendMessage startCommand(Long chatId, String name) {
        log.debug("вызван приветственный блок кода после команды старт");

        String answer = "Привет, " + name + " приятно познакомится!" + HELP_MESSAGE.getMessage();
        return service.sendMessageWithKeyboard(chatId, answer, keyboardMaker.startKeyboard());
    }
}
