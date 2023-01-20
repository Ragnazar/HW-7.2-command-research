package pro.sky.command.constants;
/**
 * Класс констант.
 * @autor Шилова Наталья
 */
public class Const {
    public static final Long VOLUNTEER_CHAT_ID=-859148741L;
    public static final String PATTERN_LOCAL_DATA = "yyyy-MM-dd";
    public static final String PATTERN_DATA = "([0-9\\-]{10})";
    public static final String PATTERN_PET_ID = "\\d+";

    public static final String ANSWER_UNCHECK_REPORT = "Последние 2 дня вы не присылали отчет о питомце. " +
            "Или ваш отчет был плохо заполнен и не принят волдонтерами центра. " +
            "Отнеситесь ответственно к этой задаче или волонтеры сами придут к вам, проверить состояние питомца!";
    public static final String ANSWER_SUCCESS_TEST_PERIOD="Поздравляем Вы успешно прошли испытательный период. Обратитесь к волонтерам центра для снятия питомца с учета." +
            "Если вы все оформили правильно и получаете это сообщение обратитесь к волонтерам центра с описанием проблеммы. ";
    public static final String ANSWER_FAILED_TEST_PERIOD="Сожалеем но вы не прошли испытательный период." +
            " Вы будете получать это сообщение, пока не обратитесь к волонтерам приюта и не вернете животное. " +
            "Если вы все оформили правильно и получаете это сообщение обратитесь к волонтерам центра с описанием проблеммы. ";
    public static final int TEST_PERIOD =15;
}
