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
    private final ButtonModService buttonModService;

    public KeyboardMakerService(ButtonModService buttonModService) {
        this.buttonModService = buttonModService;
    }

    public List<List<InlineKeyboardButton>> startKeyboard() {

        InlineKeyboardButton cat = InlineKeyboardButton.builder().text(CAT_SHELTER.getNameButton()).callbackData(CAT_SHELTER.name()).switchInlineQueryCurrentChat("-859148741").build();
        return Arrays.asList(Arrays.asList(cat, getButton(DOG_SHELTER)),
                Arrays.asList(getButton(CALL_VOLUNTEER)));
    }

    public List<List<InlineKeyboardButton>> startKeyboardForRegistered() {
        return Arrays.asList(Arrays.asList(getButton(PET_REPORT)),
                Arrays.asList(getButton(CALL_VOLUNTEER)),
                Arrays.asList(getButton(START)));
    }

    public List<List<InlineKeyboardButton>> shelterKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(INFO)),
                Arrays.asList(getButton(TAKE_PET)),
                Arrays.asList(getButton(PET_REPORT)),
                Arrays.asList(getButton(CALL_VOLUNTEER)),
                Arrays.asList(getButton(START)));

    }

    public List<List<InlineKeyboardButton>> takePetKeyboard(long chatId) {

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(Arrays.asList(getButton(REASONS_REFUSAL)));
        rows.add(Arrays.asList(getButton(RULES_SHELTER), getButton(DOCUMENTS)));
        rows.add(Arrays.asList(getButton(TRANSPORTATION)));
        rows.add(Arrays.asList(getButton(SETTLE)));

      if (buttonModService.checkShelterPress(chatId, DOG_SHELTER)) {
           rows.add(Arrays.asList(getButton(DOG_HANDLERS)));
       }
        rows.add(Arrays.asList(getButton(CALL_VOLUNTEER)));
        rows.add(Arrays.asList(getButton(START)));
        return rows;
    }

    public List<List<InlineKeyboardButton>> infoKeyboard() {

        return Arrays.asList(Arrays.asList(getButton(SHELTER_INFO)),
                Arrays.asList(getButton(SHELTER_DATA)),
                Arrays.asList(getButton(TAKE_PASS)),
                Arrays.asList(getButton(RULES_SAFETY)),
                Arrays.asList(getButton(TAKE_DATA_FOR_CONTACT)),
                Arrays.asList(getButton(CALL_VOLUNTEER)),
                Arrays.asList(getButton(START)));
    }

    public List<List<InlineKeyboardButton>> settleKeyboard() {
        return Arrays.asList(Arrays.asList(getButton(LITTLE_PET)),
                Arrays.asList(getButton(BIG_PET)),
                Arrays.asList(getButton(DISABILITY_PET)),
                Arrays.asList(getButton(CALL_VOLUNTEER)),
                Arrays.asList(getButton(START)));
    }

    InlineKeyboardButton getButton(BotMessageEnum buttonName) {
        return InlineKeyboardButton.builder().text(buttonName.getNameButton()).callbackData(buttonName.name()).build();
    }

}
