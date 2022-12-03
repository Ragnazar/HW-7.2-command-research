package pro.sky.command.service;

import org.springframework.stereotype.Service;
import pro.sky.command.constants.BotMessageEnum;

import java.util.HashMap;
import java.util.Map;

@Service
public class ButtonModService {
    //для запоминания какая кнопка в каком чате нажата

    private final Map<Long, BotMessageEnum> buttonMod = new HashMap<>();

    public void setButton(long chatId, BotMessageEnum button) {
        buttonMod.put(chatId, button);
    }

    public boolean checkButtonPress(long chatId, BotMessageEnum nameButton) {
        return buttonMod.get(chatId) == nameButton;
    }
}
