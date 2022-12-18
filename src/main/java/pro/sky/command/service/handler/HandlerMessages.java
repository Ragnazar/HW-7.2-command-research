package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.service.CheckedService;
import pro.sky.command.service.KeyboardMakerService;
import pro.sky.command.service.ReportService;
import pro.sky.command.service.SendMessageService;

import static pro.sky.command.constants.BotMessageEnum.*;

/**
 * Класс для обработки полученных текстовых сообщений
 *
 * @autor Наталья Шилова
 */
@Service
@Slf4j
public class HandlerMessages {

    private final SendMessageService service;
    private final CheckedService checkedService;
    private final NotificationRepository notificationRepository;
    private final KeyboardMakerService keyboardMaker;
    private final ReportService reportService;


    /**
     * имеет зависимость от сервисов
     */
    public HandlerMessages(SendMessageService service, CheckedService checkedService, NotificationRepository notificationRepository, KeyboardMakerService keyboardMaker, ReportService reportService) {
        this.service = service;
        this.checkedService = checkedService;
        this.notificationRepository = notificationRepository;
        this.keyboardMaker = keyboardMaker;
        this.reportService = reportService;
    }

    public Object handleText(Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        int messageId = message.getMessageId();

        if (text.equals(START.getNameButton())) {
            checkedService.clearPressButton(chatId);
            return service.sendMessage(chatId, START.getMessage(), keyboardMaker.startKeyboard());
        }

        if (text.equals(CALL_VOLUNTEER.getNameButton())) {
            checkedService.addVolunteerButtonPress(chatId, true);
            return service.sendMessage(chatId, CALL_VOLUNTEER.getMessage(), null);
        }

        if (checkedService.checkReportPress(chatId)) {
            int firstDelimiter = text.indexOf(' ');
            String dataReport = null;
            String petId = null;
            if (firstDelimiter > 0) {
                dataReport = text.substring(0, firstDelimiter);
                text = text.substring(firstDelimiter + 1);
            }
            int secondDelimiter = text.indexOf(' ');
            if (secondDelimiter > 0) {
                petId = text.substring(0, secondDelimiter);
                text = text.substring(secondDelimiter + 1);
            }
            if (dataReport.matches(Const.PATTERN_DATA) & petId.matches(Const.PATTERN_PET_ID)) {
                return service.sendMessage(chatId, reportService.addReport(chatId, dataReport, petId, text), keyboardMaker.reportKeyboard());
            }
        }


        if (checkedService.checkVolunteerButtonPress(chatId)) {
            text = message.getFrom().getFirstName() + "  " + chatId + " спрашивает:   " + text;
            notificationRepository.save(new Notification(chatId, text, messageId));
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, text, null);
        }
        if (chatId.equals(Const.VOLUNTEER_CHAT_ID)) {
            String textNotification = message.getReplyToMessage().getText();
            Notification notification = notificationRepository.findByText(textNotification);
            notificationRepository.delete(notification);
            return CopyMessage.builder()
                    .messageId(messageId)
                    .replyToMessageId(notification.getMessageId())
                    .fromChatId(chatId)
                    .chatId(notification.getId())
                    .caption(text)
                    .build();
        }

        return service.sendMessage(chatId, "Бот понимает сообщения только в определенном формате." +
                " При нажатии кнопки, вы можете посмотреть, что от вас ожидает бот. Начните с нажатия кнопки главное меню.", null);
    }
}
