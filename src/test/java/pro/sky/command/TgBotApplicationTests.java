package pro.sky.command;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.command.service.TelegramBotInitializer;
import pro.sky.command.service.TelegramBotListener;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@OpenAPIDefinition
public class TgBotApplicationTests {

    @Autowired
    private TelegramBotListener listener;
    @Autowired
    private TelegramBotInitializer initializer;

    @Test
    void contextLoads() {
        Assertions.assertThat(initializer).isNotNull();
        Assertions.assertThat(listener).isNotNull();
    }

    @Test
    public void testOnUpdateReceived() {
        TelegramBotListener bot = Mockito.mock(TelegramBotListener.class);
        Mockito.doCallRealMethod().when(bot).onUpdatesReceived(any());
        Update update1 = new Update();
        update1.setUpdateId(1);
        Update update2 = new Update();
        update2.setUpdateId(2);
        bot.onUpdatesReceived(asList(update1, update2));
        Mockito.verify(bot).onUpdateReceived(update1);
        Mockito.verify(bot).onUpdateReceived(update2);


    }
}