/**
 * Abstract class for testing {@link Command}s.
 */
package pro.sky.command.service.handler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.command.service.CheckedService;
import pro.sky.command.service.SendMessageService;
import pro.sky.command.service.TelegramBotInitializer;

abstract class AbstractCommandTest {

    protected TelegramBotInitializer telegramBot = Mockito.mock(TelegramBotInitializer.class);
    protected CheckedService checkedService = Mockito.mock(CheckedService .class);
    protected SendMessageService sendBotMessageService = new SendMessageService(checkedService);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract HandlerCommand getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().equals(update);

        //then
        Mockito.verify(telegramBot).equals(sendMessage);
    }
}