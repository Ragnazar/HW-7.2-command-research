package pro.sky.command.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@DisplayName("Unit-тесты для SendBotService")
class SendMessageServiceTest {

    private SendMessageService sendMessageService;
    private TelegramBotListener telegramBotListener;
    private CheckedService checkedService;


    @BeforeEach
    public void init() {
        checkedService = Mockito.mock(CheckedService.class);
        telegramBotListener = Mockito.mock(TelegramBotListener.class);
        sendMessageService = new SendMessageService(checkedService);
    }

    @Test
    void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 11111L;
        String message = "test_message";
        List<List<InlineKeyboardButton>> keyboardMarkup =List.of(List.of(new InlineKeyboardButton("1")));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendMessageService.sendMessage(chatId, message,keyboardMarkup);

        //then
        Mockito.verify(telegramBotListener).execute(sendMessage);
    }
//TODO
    @Test
    void sendMessageWithReplaceKeyboard() {
    }

    @Test
    void sendPhoto() {
    }
}