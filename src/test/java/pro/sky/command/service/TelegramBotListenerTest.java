package pro.sky.command.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.command.constants.ConstTest;

import java.util.Arrays;
import java.util.List;

import static pro.sky.command.constants.BotMessageEnum.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@OpenAPIDefinition
class TelegramBotListenerTest {
    @Autowired
    private TelegramBotListener listener;

    Update update = new Update();
    Message message = new Message();
    User user = new User();
    Chat chat =new Chat(ConstTest.CHAT_ID,"private");
    MessageEntity entity=new MessageEntity("bot_command",0,6);
    List<List<InlineKeyboardButton>> keyboardMarkup = List.of(List.of(new InlineKeyboardButton("1")));
    SendMessage sendMessage = new SendMessage();

    @BeforeEach
    public void init() {
        user.setIsBot(false);
        user.setId(ConstTest.CHAT_ID);
        user.setUserName(ConstTest.NAME);

        message.setChat(chat);
        message.setText(ConstTest.COMMAND_START);
        message.setEntities(Arrays.asList(entity));

        sendMessage.setText("Привет, " + ConstTest.NAME + " приятно познакомится!" + HELP_MESSAGE.getMessage());
        sendMessage.setChatId(ConstTest.CHAT_ID);
        sendMessage.setReplyMarkup(ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder().text(START.getNameButton()).build(),
                        (KeyboardButton.builder().text(CALL_VOLUNTEER.getNameButton()).build()))))
                .resizeKeyboard(true).oneTimeKeyboard(false).build());

    }

    @Test
    void onUpdateReceived() {
        Assertions.assertEquals(sendMessage,listener.updateReceived(update));


    }/*

    {
        "ok":true,
            "result": [
        {
            "update_id":3854323,
              "message":{
                       "message_id":1408,
                            "chat":{
                                          "id":5959782983,
                                  "first_name":"Натали",
                                       "type":"private"
                                                               },
                             "date":1672161982,
                                                  "text":"/start",
                                                "entities": [
                                                               {
                                                               "offset":0,
                                                                "length":6,
                                                                "type":"bot_command"
                                                                                   }
                                                                ]
        }
        }
        ]
    }*/


}