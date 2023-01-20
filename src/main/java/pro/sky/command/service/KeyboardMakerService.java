package pro.sky.command.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import pro.sky.command.constants.BotMessageEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pro.sky.command.constants.BotMessageEnum.*;

@Service
public class KeyboardMakerService {
    private final CheckedService checkedService;

    public KeyboardMakerService(CheckedService checkedService) {
        this.checkedService = checkedService;
    }

    public List<List<InlineKeyboardButton>> startKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(CAT_SHELTER), getButton(DOG_SHELTER)));
    }

    public List<List<InlineKeyboardButton>> startKeyboardForRegistered() {
        return Arrays.asList(Arrays.asList(getButton(PET_REPORT)));
    }

    public List<List<InlineKeyboardButton>> shelterKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(INFO)),
                Arrays.asList(getButton(TAKE_PET)),
                Arrays.asList(getButton(PET_REPORT)));

    }

    public List<List<InlineKeyboardButton>> takePetKeyboard(long chatId) {

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(Arrays.asList(getButton(REASONS_REFUSAL)));
        rows.add(Arrays.asList(getButton(RULES_SHELTER), getButton(DOCUMENTS)));
        rows.add(Arrays.asList(getButton(TRANSPORTATION)));
        rows.add(Arrays.asList(getButton(SETTLE)));

        if (checkedService.checkShelterPress(chatId).equals(DOG_SHELTER.name())) {
            rows.add(Arrays.asList(getButton(DOG_HANDLERS)));
        }
        return rows;
    }

    public List<List<InlineKeyboardButton>> infoKeyboard() {

        return Arrays.asList(Arrays.asList(getButton(SHELTER_INFO)),
                Arrays.asList(getButton(SHELTER_DATA)),
                Arrays.asList(getButton(TAKE_PASS)),
                Arrays.asList(getButton(RULES_SAFETY)),
                Arrays.asList(getButton(TAKE_DATA_FOR_CONTACT)));
    }

    public List<List<InlineKeyboardButton>> settleKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(LITTLE_PET)),
                Arrays.asList(getButton(BIG_PET)),
                Arrays.asList(getButton(DISABILITY_PET)));
    }
    public List<List<InlineKeyboardButton>> reportKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(PHOTO),getButton(DIET)),
                         Arrays.asList(getButton(HEALTH),getButton(BEHAVIOR)),
                Arrays.asList(getButton(TEST_PERIOD)));
    }

    InlineKeyboardButton getButton(BotMessageEnum buttonName) {
        return InlineKeyboardButton.builder().text(buttonName.getNameButton()).callbackData(buttonName.name()).build();
    }

}
