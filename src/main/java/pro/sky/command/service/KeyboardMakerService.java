package pro.sky.command.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import pro.sky.command.constants.BotMessageEnum;

import java.util.Arrays;
import java.util.List;

import static pro.sky.command.constants.BotMessageEnum.*;

@Service
public class KeyboardMakerService {
    private final ButtonModService buttonModService;

    public KeyboardMakerService(ButtonModService buttonModService) {
        this.buttonModService = buttonModService;
    }

    public List<List<InlineKeyboardButton>> startKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(CAT_SHELTER), getButton(DOG_SHELTER)),
                Arrays.asList(getButton(CALL_VOLUNTEER)));
    }

    public List<List<InlineKeyboardButton>> shelterKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(INFO)),
                Arrays.asList(getButton(TAKE_PET)),
                Arrays.asList(getButton(PET_REPORT)),
                Arrays.asList(getButton(CALL_VOLUNTEER)));

    }

    public List<List<InlineKeyboardButton>> takePetKeyboard(long chatId) {

        List<List<InlineKeyboardButton>> rows = Arrays.asList(Arrays.asList(getButton(REASONS_REFUSAL)),
                Arrays.asList(getButton(RULES_SHELTER), getButton(DOCUMENTS)),
                Arrays.asList(getButton(TRANSPORTATION)),
                Arrays.asList(getButton(SETTLE)));

        if (buttonModService.checkButtonPress(chatId, DOG_SHELTER)) {
            rows.add(Arrays.asList(getButton(DOG_HANDLERS)));
        }
        rows.add(Arrays.asList(getButton(CALL_VOLUNTEER)));
        return rows;
    }
    public List<List<InlineKeyboardButton>> infoKeyboard() {

    return  Arrays.asList(Arrays.asList(getButton(SHELTER_INFO)),
                Arrays.asList(getButton(SHELTER_DATA)),
                Arrays.asList(getButton(TAKE_PASS)),
                Arrays.asList(getButton(RULES_SAFETY)),
                Arrays.asList(getButton(TAKE_DATA_FOR_CONTACT)),
                Arrays.asList(getButton(CALL_VOLUNTEER)));
    }

    public List<List<InlineKeyboardButton>> settleKeyboard() {
    return   Arrays.asList(Arrays.asList(getButton(LITTLE_PET)),
            Arrays.asList(getButton(BIG_PET)),
            Arrays.asList(getButton(DISABILITY_PET)),
            Arrays.asList(getButton(CALL_VOLUNTEER)));
    }


    InlineKeyboardButton getButton(BotMessageEnum buttonName) {
        return InlineKeyboardButton.builder().text(buttonName.getNameButton()).callbackData(buttonName.name()).build();
    }

}
