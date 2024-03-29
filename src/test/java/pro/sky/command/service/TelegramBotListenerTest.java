package pro.sky.command.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.command.constants.ConstTest;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static pro.sky.command.constants.BotMessageEnum.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@OpenAPIDefinition
class TelegramBotListenerTest {
    @Autowired
    private TelegramBotListener listener;
    @Autowired
    private OwnerRepository ownerRepository;


    Update update = new Update();
    Message message = new Message();
    User user = new User();
    Chat chat = new Chat(ConstTest.CHAT_ID, "private");
    MessageEntity entity = new MessageEntity("bot_command", 0, 6);
    SendMessage sendMessage = new SendMessage();

    @BeforeEach
    public void init() {
        user.setIsBot(false);
        user.setId(ConstTest.CHAT_ID);
        user.setFirstName(ConstTest.NAME);

        message.setMessageId(ConstTest.MESSAGE_ID);
        message.setChat(chat);
        message.setText(ConstTest.COMMAND_START);
        message.setFrom(user);

        sendMessage.setText("Привет, " + ConstTest.NAME + " приятно познакомится!" + HELP_MESSAGE.getMessage());
        sendMessage.setChatId(ConstTest.CHAT_ID);
        sendMessage.setReplyMarkup(ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(Arrays.asList(KeyboardButton.builder().text(START.getNameButton()).build(),
                        (KeyboardButton.builder().text(CALL_VOLUNTEER.getNameButton()).build()))))
                .resizeKeyboard(true).oneTimeKeyboard(false).build());

    }

    @Test
    void onUpdateReceivedTestCommandStart() {
        message.setEntities(Arrays.asList(entity));
        Owner owner = new Owner(ConstTest.CHAT_ID, ConstTest.NAME);
        update.setMessage(message);

        Assertions.assertEquals(sendMessage, listener.updateReceived(update));
        Assertions.assertEquals(Optional.of(owner), ownerRepository.findById(ConstTest.CHAT_ID.toString()));
    }

    @Test
    void onUpdateReceivedTestButtonMainMenu() {

        message.setText(ConstTest.BUTTON_MENU);
        Owner owner = new Owner(ConstTest.CHAT_ID, ConstTest.NAME);
        owner.setPets(new ArrayList<>());
        ownerRepository.save(owner);
        update.setMessage(message);

        sendMessage.setText(START.getMessage());
        sendMessage.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(
                Arrays.asList(Arrays.asList(
                        InlineKeyboardButton.builder().text(CAT_SHELTER.getNameButton()).callbackData(CAT_SHELTER.name()).build()
                        , InlineKeyboardButton.builder().text(DOG_SHELTER.getNameButton()).callbackData(DOG_SHELTER.name()).build()))).build());

        Assertions.assertEquals(sendMessage, listener.updateReceived(update));
    }


    /*

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