package pro.sky.command.service.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.SendMessageService;
import pro.sky.command.service.VolunteerService;

@DisplayName("Unit-тесты для CommandContainer")
class HandlerCommandTest {

    private HandlerCommand handlerCommand;
    private SendMessageService sendMessageService;
    private OwnerRepository ownerRepository;
    private KeyboardMakerService keyboardMakerService;
    private VolunteerService volunteerService;

    @BeforeEach
    public void init() {
        sendMessageService = Mockito.mock(SendMessageService.class);
        keyboardMakerService = Mockito.mock(KeyboardMakerService.class);
        ownerRepository = Mockito.mock(OwnerRepository.class);
        volunteerService = Mockito.mock(VolunteerService.class);
        PetRepository petRepository = null;
        handlerCommand = new HandlerCommand(sendMessageService, keyboardMakerService, ownerRepository, volunteerService, petRepository);
    }

    @Test
    void shouldGetAllTheExistingCommands() {
        //TODO

    }


}