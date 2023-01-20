package pro.sky.command.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Notification;
import pro.sky.command.model.Owner;
import pro.sky.command.model.Pet;
import pro.sky.command.model.Report;
import pro.sky.command.repository.NotificationRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VolunteerService {
    private final NotificationRepository notificationRepository;
    private final ReportRepository reportRepository;
    private final PetRepository petRepository;
    private final SendMessageService service;

    public VolunteerService(NotificationRepository notificationRepository, ReportRepository repository, PetRepository petRepository, SendMessageService service) {
        this.notificationRepository = notificationRepository;
        this.reportRepository = repository;
        this.petRepository = petRepository;
        this.service = service;
    }

    public Object getReport() {
        Report report = reportRepository.findAllUncheck();
        if (report == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Нет не обработанных отчетов.", null);
        }
        new Notification(report.getReportId(), report.getReportId().toString(), 0);
        String data = report.getRecordingDate().toString();
        List<Object> send = new ArrayList<>();
        String answer = "отчет № " + report.getReportId() + " за дату  " + data + " для питомца № " + report.getPet().getNamePet();
        notificationRepository.save(new Notification(report.getReportId(), answer, 0));
        send.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, answer, null));
        send.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, BotMessageEnum.HEALTH.getNameButton() +
                " от  " + data + "   \n" + report.getStateOfHealth(), null));
        send.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, BotMessageEnum.BEHAVIOR.getNameButton() +
                " от  " + data + "   \n" + report.getBehaviorChanges(), null));
        send.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, BotMessageEnum.DIET.getNameButton() +
                " от  " + data + "   \n" + report.getDiet(), null));
        SendPhoto sendPhoto = service.createSendPhoto(Const.VOLUNTEER_CHAT_ID, report.getPathToPhoto());
        sendPhoto.setCaption(BotMessageEnum.PHOTO.getNameButton() + "  " + data);
        send.add(sendPhoto);
        send.add(service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Принять или не принять отчет можно, написав в ответ на первое сообщение," +
                " содержащее номер отчета. \"подтверждено\" или \"отклонено\" ", null));
        return send;
    }

    public Object checkReport(String text, long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Отчет с таким номером не найден попробуйте еще раз", null);
        }
        Pet pet = report.getPet();
        Owner owner = pet.getOwner();
        String answer = "Отчет питомца " + pet.getNamePet() + " от " + report.getRecordingDate();
        if (text.compareToIgnoreCase("подтверждено") == 0) {
            report.setCheckReport(true);
            pet.setCorrectReportCount(pet.getCorrectReportCount() + 1);

            return new ArrayList<>(Arrays.asList(service.sendMessage(Long.valueOf(owner.getChatId()),
                            answer + " успешно подтвержден.", null),
                    service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Отчет успешно подтвержден.", null)));
        }
        if (text.compareToIgnoreCase("отклонено") == 0) {
            report.setCheckReport(false);
            return new ArrayList<>(Arrays.asList(service.sendMessage(Long.valueOf(owner.getChatId()),
                            answer + " Отклонен. Если вы уверены в правильности заполнения отчета свяжитесь с волонтером" +
                                    " и опишите проблему. Номер отклоненного отчета сообщите волонтеру. Номер отклоненного отчета : " + reportId, null),
                    service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Отчет номер " + reportId + " отклонен.", null)));
        }
        return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Что то пошло не так. Попробуйте еще раз с нажатия кнопки отчет", null);
    }

    public Object getOwner() {
        Pet pet = petRepository.findByCorrectReportCount(Const.TEST_PERIOD).orElse(null);
        if (pet == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Нет владельцев готовых окончить испытательный период.", null);
        }
        if (pet.getOwner() == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Возникла ошибка. У питомца с номером " +
                    pet.getId() + "Пройден испытательный период, но не назначен владелец. Проверьте данные.", null);
        }
        String answer = pet.getId() + "Владелец данного питомца готов подтвердить испытательный срок.";
        new Notification( pet.getId(), answer, 0);

        return service.sendMessage(Const.VOLUNTEER_CHAT_ID, answer +
                        "Подтвердить или продлить испытательный срок можно, написав в ответ на это сообщение," +
                        " \"поздравить\" или \"продлить\". При этом испытательный срок продлится на 10 дней и хозяин питомца получит уведомление"
                , null);
    }

    public Object checkOwner(String text, long petId) {

        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID,
                    "Питомец с "+petId+" таким номером не найден попробуйте еще раз", null);
        }
        if (pet.getOwner() == null) {
            return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Возникла ошибка. У питомца с номером " +
                    pet.getId() + " не назначен владелец. Проверьте данные.", null);
        }
            if (text.compareToIgnoreCase("поздравить") == 0) {
              return new ArrayList<>(Arrays.asList(service.sendMessage(Long.valueOf(pet.getOwner().getChatId()),
                            Const.ANSWER_SUCCESS_TEST_PERIOD, null),
                    service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Владелец успешно поздравлен.", null)));
        }
        if (text.compareToIgnoreCase("продлить") == 0) {
                   return new ArrayList<>(Arrays.asList(service.sendMessage(Long.valueOf(pet.getOwner().getChatId()),
                           Const.ANSWER_FAILED_TEST_PERIOD, null),
                    service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Испытательный срок владельцеу питомца номер "+ petId+
                            " продлен на 10 дней", null)));
        }
        return service.sendMessage(Const.VOLUNTEER_CHAT_ID, "Что то пошло не так. Попробуйте еще раз с нажатия кнопки владелец", null);
    }
}