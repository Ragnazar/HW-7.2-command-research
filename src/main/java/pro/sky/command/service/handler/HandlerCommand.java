package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static pro.sky.command.constants.BotMessageEnum.*;

@Service
@Slf4j
public class HandlerCommand {
    private final SendMessageService service;
    private final KeyboardMakerService keyboardMaker;
    private final OwnerRepository ownerRepository;

    public HandlerCommand(SendMessageService service, KeyboardMakerService keyboardMaker, OwnerRepository ownerRepository) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
        this.ownerRepository = ownerRepository;
      }

    public Object handleMessage(Message message) {
        log.debug("вызов блок для извлечения из сообщения и обработки команды");
        long chatId = message.getChatId();
        Optional<MessageEntity> commandEntity = message.getEntities().stream()
                .filter(e -> "bot_command".equals(e.getType()) || "phone_number".equals(e.getType())).findFirst();


        if ("phone_number".equals(commandEntity.get().getType())) {
            String phone = message.getText();
            addPhoneUser(chatId, message.getFrom().getFirstName(), phone);

            List<BotApiMethod> messages = new ArrayList<>();

            messages.add(CopyMessage.builder().messageId(message.getMessageId()).fromChatId(chatId).chatId(Const.VOLUNTEER_CHAT_ID).build());
            messages.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Ожидает звонка. Эти сообщения не требуют ответа",null));
            messages.add(service.sendMessage(chatId, "Ожидайте звонка.", keyboardMaker.startKeyboard()));
            return messages;
        }
        String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

        switch (command) {
            case "/start":
                log.debug("вызваа команда /start");
                return startCommand(chatId, message.getFrom().getFirstName());
            default:
                return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.", keyboardMaker.startKeyboard());
        }
    }

    private SendMessage startCommand(Long chatId, String name) {
        log.debug("вызван приветственный блок кода после команды старт");

        String answer = "Привет, " + name + " приятно познакомится!" + HELP_MESSAGE.getMessage();
        if (ownerRepository.findById(chatId.toString()).isEmpty()){
            registerUser(chatId, name);
        }
        return SendMessage.builder().chatId(chatId).text(answer)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder().text(START.getNameButton()).build()
                                ,(KeyboardButton.builder().text(CALL_VOLUNTEER.getNameButton()).build()))))
                        .resizeKeyboard(true).oneTimeKeyboard(false).build())
                .build();
    }

    private void registerUser(Long chatId, String name) {
        Owner owner = new Owner(chatId, name);
        ownerRepository.save(owner);
    }

    private void addPhoneUser(Long chatId, String name, String phone) {
        Owner owner = ownerRepository.findById(chatId.toString()).orElse(new Owner(chatId, name));
        owner.setPhoneNumber(phone);
    }
}

