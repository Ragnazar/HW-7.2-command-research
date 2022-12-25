package pro.sky.command.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.model.Report;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class VolunteerService {
    private final NotificationRepository notificationRepository;
    private final ReportRepository repository;
    private final SendMessageService service;

    public VolunteerService(NotificationRepository notificationRepository, ReportRepository repository, SendMessageService service) {
        this.notificationRepository = notificationRepository;
        this.repository = repository;
        this.service = service;
    }

    public Object getReport(long chatId) {
        Report report = repository.findAllUncheck();
        if (report == null) {
            return service.sendMessage(chatId, "Нет не обработанных отчетов.", null);
        }
        new Notification(Const.VOLUNTEER_CHAT_ID, report.getReportId().toString(), 0);
        String data = report.getRecordingDate();
        List<Object> send = new ArrayList<>();
        send.add(service.sendMessage(chatId, "отчет № "+report.getReportId() + " за дату  " + data + " для питомца № " + report.getPet().getNamePet(), null));
        send.add(service.sendMessage(chatId, BotMessageEnum.HEALTH.getNameButton() +
                " от  " + data + "   \n" + report.getStateOfHealth(), null));
        send.add(service.sendMessage(chatId, BotMessageEnum.BEHAVIOR.getNameButton() +
                " от  " + data + "   \n" + report.getBehaviorChanges(), null));
        send.add(service.sendMessage(chatId, BotMessageEnum.DIET.getNameButton() +
                " от  " + data + "   \n" + report.getDiet(), null));
        SendPhoto sendPhoto = service.createSendPhoto(chatId, report.getPathToPhoto());
        sendPhoto.setCaption(BotMessageEnum.PHOTO.getNameButton() + "  " + data);
        send.add(sendPhoto);
        return send;
    }
}
