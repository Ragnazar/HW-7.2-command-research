package pro.sky.command.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.OwnerRepository;

@Service
@Slf4j
public class CheckedService {
    //для запоминания какая кнопка в каком чате нажата

    private final OwnerRepository ownerRepository;

    public CheckedService(OwnerRepository ownerRepository) {
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

    public String checkShelterPress(long chatId) {
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {
            return owner.getShelterButton();
        }
        return "";
    }

    public boolean checkVolunteerButtonPress(long chatId) {
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (owner != null) {

            return owner.isVolunteerChat();
        }
        return false;
    }

    public void addVolunteerButtonPress(long chatId, boolean b) {
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(new Owner(chatId, "No Name"));
        owner.setVolunteerChat(b);
        ownerRepository.save(owner);
    }

    public void clearPressButton(long chatId) {
        if (ownerRepository.findById(String.valueOf(chatId)).isPresent()) {
            Owner owner = ownerRepository.findById(String.valueOf(chatId)).get();
            owner.setVolunteerChat(false);
            owner.setShelterButton(null);
            ownerRepository.save(owner);
        }
    }
}
