package pro.sky.command.service;


import org.springframework.stereotype.Component;
import pro.sky.command.constants.BotMessageEnum;
import pro.sky.command.constants.Const;
import pro.sky.command.model.Owner;
import pro.sky.command.model.Pet;
import pro.sky.command.model.Report;
import pro.sky.command.repository.OwnerRepository;
import pro.sky.command.repository.PetRepository;
import pro.sky.command.repository.ReportRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс для работы с таблицей отчет. Добавляет ввделенные сообщения в таблицу и возвращает сообщение.
 *
 * @autor Шилова Наталья
 */
@Component
public class ReportService {
    private final ReportRepository reportRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    /**
     * Конструктор зависимостей
     * @see ReportRepository
     *  @see OwnerRepository
     * @see PetRepository
     */
    public ReportService(ReportRepository reportRepository, OwnerRepository ownerRepository, PetRepository petRepository) {
        this.reportRepository = reportRepository;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    /**
     * Проверяет есть ли пользователь и питомец с указанными номерами. По дате отчета добавляет в таблицу текст, присланный пользователем.
     *@param chatId идентификатор чата пользователя
     *@param data дата отчета получается в сообщениипользователя
     *@param petId идентификатор питомца
     *@param text текст сообщения отчета
     *@return возвращает строку которая будет отправлена пользователю.
     */
    public String addReport(long chatId, String data, String petId, String text) {
        String answer = "";
        LocalDate dateTime = LocalDate.parse(data, DateTimeFormatter.ofPattern(Const.PATTERN_LOCAL_DATA));
        Pet pet = petRepository.findById(Long.valueOf(petId)).orElse(null);
        Owner owner = ownerRepository.findById(String.valueOf(chatId)).orElse(null);
        if (pet == null || owner == null) {
            return "Питомец с таким идентификатором не найден. Проверьте правильно ли вы ввели идентификатор. Если все верно сообщите об ошибке волонтеру.";
        }if (!pet.getOwner().getChatId().equals(String.valueOf(chatId))){
           return "Питомец с таким номером вам не принадлежит. Если вы правильно ввели идентификатор сообщите об ошибке волонтеру.";
        }
        Report report = reportRepository.findByQuery(Long.valueOf(petId), dateTime).orElse(new Report(dateTime, pet));
        if (owner.getReportButton().equals(BotMessageEnum.DIET.name())) {
            report.setDiet(text);
            answer = BotMessageEnum.DIET.getNameButton() + " за " + data + " успешно добавлен.";
        }
        if (owner.getReportButton().equals(BotMessageEnum.HEALTH.name())) {
            report.setStateOfHealth(text);
            answer = BotMessageEnum.HEALTH.getNameButton() + " за " + data + " успешно добавлен";
        }
        if (owner.getReportButton().equals(BotMessageEnum.BEHAVIOR.name())) {
            report.setBehaviorChanges(text);
            answer = BotMessageEnum.BEHAVIOR.getNameButton() + " за " + data + " успешно добавлен. ";
        }
        if (owner.getReportButton().equals(BotMessageEnum.PHOTO.name())){
            report.setPathToPhoto(text);
            answer = BotMessageEnum.PHOTO.getNameButton() + " за " + data + " успешно добавлен. ";
        }
        owner.setReportButton(null);
        ownerRepository.save(owner);
        reportRepository.save(report);
        String checkReport=checkReport(report);
        if (!checkReport.isEmpty()) {
            answer = answer + " Не забудьте добавить " + checkReport;
        }
        pet.setDateLastCorrectReport(dateTime);
        petRepository.save(pet);
        return answer;
    }

    /**
     * Проверяет какие поля в таблице отчет заполнены. Возвращает строку которая, будет отправлена пользователю.
     *@param report Принимает объект отчет
     *@return возвращает строку которая содержит какие поля в отчете еще не заполнены.
     */
    private String checkReport(Report report) {
        String answer = "";
        if (report.getDiet() == null) {
            answer = "рацион ";
        }
        if (report.getBehaviorChanges() == null) {
            answer = answer + " изменения в поедении и привычках питомца ";
        }
        if (report.getStateOfHealth() == null) {
            answer = answer + "состояние здоровья питомца ";
        }
        if (report.getPathToPhoto() == null) {
            answer = answer + "фотографию питомца ";
        }
        return answer;
    }

    public List<Pet> checkReportData() {
        LocalDate today =LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern(Const.PATTERN_LOCAL_DATA));
        return petRepository.findAllByDateLastCorrectReport(today.minusDays(3));
    }

}
