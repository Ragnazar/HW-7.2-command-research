package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.command.configuration.TelegramBotConfiguration;
import pro.sky.command.constants.BotMessageEnum;

import java.util.*;

import static pro.sky.command.constants.BotMessageEnum.*;

@Component
@Slf4j
public class TelegramBotListener extends TelegramLongPollingBot {
    private final ButtonModService buttonModService;
    private final TelegramBotConfiguration configuration;

    public TelegramBotListener(ButtonModService buttonModService, TelegramBotConfiguration configuration) {
        this.buttonModService = buttonModService;
        this.configuration = configuration;
    }

    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    @Override
    public String getBotToken() {
        return configuration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("вызван блок для получения всех входящих сообщений");
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.hasEntities()) {
                handleMessage(message);
            }
            handleText(message);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }


    private void handleMessage(Message message) {
        log.debug("вызваа блок для извлечения из сообщения и обработки команды");
        long chatId = message.getChatId();
        Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
        if (commandEntity.isPresent()) {
            String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
            String text = message.getText().substring(commandEntity.get().getLength());
            switch (command) {
                case "/start":
                    log.debug("вызваа команда /start");
                    startCommand(chatId, message.getFrom().getFirstName());
                    break;
                default:
                    executeMessage(sendMessage(chatId, "Извините, данная команда пока не поддерживается."));
            }
        }
    }

    private void handleText(Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        long userChatId = message.getFrom().getId();

    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callbackData) {
            case "CALL_VOLUNTEER":
                log.debug("вызваа команда /start");
                executeMessage(sendMessage(chatId, CALL_VOLUNTEER.getMessage()));
                break;
            case "CAT_SHELTER":
                log.debug("вызваа команда /start");
                buttonModService.setButton(chatId, CAT_SHELTER);
                executeMessage(sendMessageWithKeyboard(chatId, CAT_SHELTER.getMessage(), catShelterRows()));
                break;
            case "DOG_SHELTER":
                log.debug("вызваа команда /start");
                buttonModService.setButton(chatId, DOG_SHELTER);
                executeMessage(sendMessageWithKeyboard(chatId, DOG_SHELTER.getMessage(), dogShelterRows()));
                break;
            default:
                executeMessage(sendMessage(chatId, "Извините, данная команда пока не поддерживается."));
        }
    }

    public void startCommand(Long chatId, String name) {
        log.debug("вызван приветственный блок кода после команды старт");

        String answer = "Привет, " + name + " приятно познакомится!" + HELP_MESSAGE.getMessage();

//        BotMessageEnum saved = buttonModService.getButton(chatId);
//        if (saved == CAT_SHELTER) {
//
//        } else if (saved == DOG_SHELTER) {
//
//        }

        List<List<InlineKeyboardButton>> rowsInLine = Arrays.asList(Arrays.asList(getButton(CAT_SHELTER), getButton(DOG_SHELTER)),
                Arrays.asList(getButton(CALL_VOLUNTEER)));
//        SendMessage message = sendMessage(chatId, answer);
//        message.setReplyMarkup(getStartMenuKeyboard());


        executeMessage(sendMessageWithKeyboard(chatId, answer, rowsInLine));
    }


    private SendMessage sendMessage(Long chatId, String messageToSend) {
        log.debug("вызван блок для создания исходящего сообщения");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(messageToSend);
        return sendMessage;
    }

    private SendMessage sendMessageWithKeyboard(Long chatId, String messageToSend, List<List<InlineKeyboardButton>> keyboardMarkup) {
        log.debug("вызван блок для создания исходящего сообщения с прикрепленными кнопками");
        SendMessage message = sendMessage(chatId, messageToSend);
        message.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboardMarkup).build());
        return message;
    }


    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Возникла ошибка при отправке сообщения в телеграм");
        }
    }

    private List<List<InlineKeyboardButton>> catShelterRows() {

        return Arrays.asList(Arrays.asList(getButton(BotMessageEnum.reasons_for_refusal)),
                Arrays.asList(getButton(RULES_CAT_SHELTER), getButton(documents)),
                Arrays.asList(getButton(transportation)),
                Arrays.asList(getButton(settle)));
    }

    private List<List<InlineKeyboardButton>> dogShelterRows() {

        return Arrays.asList(Arrays.asList(getButton(BotMessageEnum.reasons_for_refusal)),
                Arrays.asList(getButton(RULES_DOG_SHELTER), getButton(documents)),
                Arrays.asList(getButton(transportation)),
                Arrays.asList(getButton(dog_handlers)),
                Arrays.asList(getButton(settle)));
    }

    InlineKeyboardButton getButton(BotMessageEnum buttonName) {
        return InlineKeyboardButton.builder().text(buttonName.getNameButton()).callbackData(buttonName.name()).build();
    }

    public ReplyKeyboardMarkup getStartMenuKeyboard() {
        List<KeyboardRow> keyboard = Arrays.asList(new KeyboardRow(Arrays.asList(new KeyboardButton(CALL_VOLUNTEER.getNameButton()))));

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

}

