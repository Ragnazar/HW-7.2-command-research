package pro.sky.command.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.model.Owner;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.service.*;


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
    private final VolunteerService volunteerService;


    /**
     * имеет зависимость от сервисов
     */
    public HandlerMessages(SendMessageService service,
                           CheckedService checkedService,
                           NotificationRepository notificationRepository,
                           KeyboardMakerService keyboardMaker,
                           ReportService reportService,
                           VolunteerService volunteerService) {
        this.service = service;
        this.checkedService = checkedService;
        this.notificationRepository = notificationRepository;
        this.keyboardMaker = keyboardMaker;
        this.reportService = reportService;
        this.volunteerService = volunteerService;
    }

    public Object handleText(Message message) {
        log.debug("вызван блок обработки сообщений не содержащих команд бота");
        String text = message.getText();
        Long chatId = message.getChatId();
        int messageId = message.getMessageId();

        if (text.equals(START.getNameButton())) {
            checkedService.clearPressButton(chatId);
            if (checkedService.isOwnerHavePet(chatId)) {
                return service.sendMessage(chatId, "Вы можете отправить отчет о питомце или посмотреть информацию о приютах", keyboardMaker.startKeyboardForRegistered());
            }
            return service.sendMessage(chatId, START.getMessage(), keyboardMaker.startKeyboard());
        }

        if (text.equals(CALL_VOLUNTEER.getNameButton())) {
            checkedService.addVolunteerButtonPress(chatId, true);
            return service.sendMessage(chatId, CALL_VOLUNTEER.getMessage(), null);
        }
        if (text.compareToIgnoreCase("подтверждено") == 0 || text.compareToIgnoreCase("отклонено") == 0 ||
                text.compareToIgnoreCase("поздравить") == 0 || text.compareToIgnoreCase("продлить") == 0) {
            String notificationText = message.getReplyToMessage().getText();
            Notification notification = notificationRepository.findByText(notificationText);

            switch (text.toLowerCase()) {
                case "подтверждено", "отклонено" -> {
                    if (notification != null) {
                        notificationRepository.delete(notification);
                        return volunteerService.checkReport(text, notification.getId());
                    }
                    return service.sendMessage(chatId, "Возможно указанный отчет уже обработан или возникла ошибка." +
                            " Попробуйте еще раз нажать кнопку отчет.", null);
                }
                case "поздравить", "продлить" -> {
                    if (notification != null) {
                        notificationRepository.delete(notification);
                        return volunteerService.checkOwner(text, notification.getId());
                    }
                    return service.sendMessage(chatId, "Возможно указанный владелец уже обработан или возникла ошибка." +
                            " Попробуйте еще раз нажать кнопку владелец.", null);
                }
            }
        }


        if (checkedService.checkReportPress(chatId)) {
            int firstDelimiter = text.indexOf(' ');
            String dataReport = " ";
            String petId = " ";
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
            text = message.getFrom().getFirstName() + " идентификатор(номер чата) " + chatId + " спрашивает:   \n\n" + text;
            notificationRepository.save(new Notification(chatId, text, messageId));
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, text, null);
        }
        if (chatId.equals(Const.VOLUNTEER_CHAT_ID)) {
            String textNotification = message.getReplyToMessage().getText();
            Notification notification = notificationRepository.findByText(textNotification);
            if (notification != null) {
                notificationRepository.delete(notification);
                return CopyMessage.builder()
                        .messageId(messageId)
                        .replyToMessageId(notification.getMessageId())
                        .fromChatId(chatId)
                        .chatId(notification.getId())
                        .caption(text)
                        .build();
            }
        }

        return service.sendMessage(chatId, " \"подтверждено\" или \"отклонено\"Бот понимает сообщения только в определенном формате." +
                " При нажатии кнопки, вы можете посмотреть, что от вас ожидает бот. Начните с нажатия кнопки главное меню.", null);
    }
}
