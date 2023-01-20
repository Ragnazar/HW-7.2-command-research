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
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;
import pro.sky.command.service.VolunteerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static pro.sky.command.constants.BotMessageEnum.*;

/**
 * Класс для обработки специальных камманд.
 *
 * @autor Шилова Наталья
 */
@Service
@Slf4j
public class HandlerCommand {
    private final SendMessageService service;
    private final KeyboardMakerService keyboardMaker;
    private final OwnerRepository ownerRepository;
    private final VolunteerService volunteerService;
    private final PetRepository petRepository;


    /**
     * Конструктор зависимостей
     *
     * @see SendMessageService
     * @see KeyboardMakerService
     * @see OwnerRepository
     */
    public HandlerCommand(SendMessageService service, KeyboardMakerService keyboardMaker, OwnerRepository ownerRepository, VolunteerService volunteerService, PetRepository petRepository) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
        this.ownerRepository = ownerRepository;
        this.volunteerService = volunteerService;
        this.petRepository = petRepository;
    }

    /**
     * Обрабатывает поступающие команды.
     * реагирует на команду /start и номер телефона в определенном формате
     *
     * @param message сообщение из чата пользователя
     * @return возвращает объект который может быть отправлен в телеграм с помощью метода execute.
     */
    public Object handleMessage(Message message) {
        log.debug("вызов блок для извлечения из сообщения и обработки команды");
        long chatId = message.getChatId();
        Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType()) || "phone_number".equals(e.getType())).findFirst();


        if ("phone_number".equals(commandEntity.get().getType())) {
            String phone = message.getText();
            addPhoneUser(chatId, message.getFrom().getFirstName(), phone);

            List<BotApiMethod> messages = new ArrayList<>();

            messages.add(CopyMessage.builder().messageId(message.getMessageId()).fromChatId(chatId).chatId(Const.VOLUNTEER_CHAT_ID).build());
            messages.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Ожидает звонка. Эти сообщения не требуют ответа", null));
            messages.add(service.sendMessage(chatId, "Ожидайте звонка.", keyboardMaker.startKeyboard()));
            return messages;
        }
        String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

        if (chatId == Const.VOLUNTEER_CHAT_ID) {
            switch (command) {
                case "/start":
                    log.debug("вызваа команда /start");
                    return startCommandVolunteer();
                case "/report":
                    return volunteerService.getReport();
                case "/owner":
                    return volunteerService.getOwner();

                default:
                    return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.", null);
            }
        }

        switch (command) {
            case "/start":
                log.debug("вызваа команда /start");
                return startCommand(chatId, message.getFrom().getFirstName());
            default:
                return service.sendMessage(chatId, "Извините, данная команда пока не поддерживается.", keyboardMaker.startKeyboard());
        }
    }

    /**
     * создает ответ пользователю после вызова команды /start.
     * проверяет есть ли пользователь в базе и в зависимотси от этого подставляет разную панель кнопок.
     *
     * @param chatId идентификатор чата пользователя
     * @param name   Имя пользователя
     * @return возвращает строку которая будет отправлена пользователю.
     */
    private SendMessage startCommand(Long chatId, String name) {
        log.debug("вызван приветственный блок кода после команды старт");

        String answer = "Привет, " + name + " приятно познакомится!" + HELP_MESSAGE.getMessage();
        if (ownerRepository.findById(chatId.toString()).isEmpty()) {
            registerUser(chatId, name);
        } else if (!petRepository.findAllByOwner(chatId.toString()).isEmpty()) {
            return service.sendMessage(chatId, " Привет! Чем я смогу помочь", keyboardMaker.startKeyboardForRegistered());
        }
        return SendMessage.builder().chatId(chatId).text(answer)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder().text(START.getNameButton()).build(),
                                (KeyboardButton.builder().text(CALL_VOLUNTEER.getNameButton()).build()))))
                        .resizeKeyboard(true).oneTimeKeyboard(false).build()).build();
    }

    /**
     * создает ответ в чате волонтеров после вызова команды /start.
     *
     * @return возвращает строку которая будет отправлена.
     */
    private SendMessage startCommandVolunteer() {
        log.debug("вызван приветственный блок кода после команды старт в чате волонтеров");

        String answer = "Привет, здесь вы сможете работать с отчетами пользователей и отвечать на вопросы пользователя";

        return SendMessage.builder().chatId(Const.VOLUNTEER_CHAT_ID).text(answer)
                .replyMarkup(ReplyKeyboardMarkup.builder().keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder().text("/report").build(), (KeyboardButton.builder().text("/owner").build())))).resizeKeyboard(true).oneTimeKeyboard(false).build()).build();
    }

    /**
     * Добавляет нового пользователя в базу
     *
     * @param chatId идентификатор чата пользователя
     * @param name   Имя пользователя
     */
    private void registerUser(Long chatId, String name) {
        log.debug("вызван блок кода для регистрации пользователя");
        Owner owner = new Owner(chatId, name);
        ownerRepository.save(owner);
    }

    /**
     * Добавляет пользователю номер телефона после того как пользователь ввел свой телефонный номер в формате "11 цифр начиная с 8 без разделителей".
     *
     * @param chatId идентификатор чата пользователя
     * @param name   Имя пользователя
     * @param phone  номер телефона пользовавтеля
     */
    private void addPhoneUser(Long chatId, String name, String phone) {
        log.debug("вызван блок кода для добавления пользователю номера телефона");
        Owner owner = ownerRepository.findById(chatId.toString()).orElse(new Owner(chatId, name));
        owner.setPhoneNumber(phone);
    }
}

