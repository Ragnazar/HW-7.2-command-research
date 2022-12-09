package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.OwnerRepository;

@Service
@Slf4j
public class ButtonModService {
    //для запоминания какая кнопка в каком чате нажата

    private final OwnerRepository ownerRepository;

    public ButtonModService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public boolean addShelterPress(long chatId, BotMessageEnum nameButton) {
        boolean check = false;
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {
            check = true;
            owner.setShelterButton(nameButton.name());
            ownerRepository.save(owner);
        }
        return check;
    }
    public boolean checkShelterPress(long chatId, BotMessageEnum nameButton) {
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {
            return owner.getShelterButton().equals(nameButton.name());
        }
        return false;
    }
    public boolean checkButtonPress(long chatId) {
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {
            return owner.isVolunteerChat();
        }
        return false;
    }

    public boolean addButtonPress(long chatId, boolean b) {
        boolean check = false;
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {
            check = b;
            owner.setVolunteerChat(true);
            ownerRepository.save(owner);
        }
        return check;
    }
}
