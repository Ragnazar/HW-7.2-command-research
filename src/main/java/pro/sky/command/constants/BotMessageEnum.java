package pro.sky.command.constants;

public enum BotMessageEnum {

    //ответы на команды с клавиатуры
    HELP_MESSAGE("\n  Я бот Приюта для животных. Я расскажу Вам о приюте " +
            "или помогу связаться с волонтерами.\n\n", ""),

    START(" \"✅ Для дальнейшей работы выберите  хотите вы узнать о приюте кошек или собак.\n\n"  +
            "Воспользуйтесь клавиатурой, чтобы начать  \uD83D\uDC47", "Главное меню"),

    CALL_VOLUNTEER("Ваш запрос важен для нас.\n" +
            "Напишите сообщением свой вопрос волонтеру."
          , "Позвать волонтера"),

    //первое меню
    CAT_SHELTER("кошачий приют ", "кошачий приют "),
    DOG_SHELTER("собачий приют ", "собачий приют"),


    //второе меню

    INFO("Вы можете почитать про наш приют", "Инфо"),
    //при отправке команды в чат сразу послать сообщение с краткой инфой о приюте и к нему приделать третье меню
    TAKE_PET(" Бот поможет потенциальным усыновителям животного из приюта разобраться с бюрократическими" +
            " и бытовыми вопросам. Нажмите кнопку, чтоб получить более подробную информацию.",
            "Взять питомца."),
    PET_REPORT(" Вы обязаны в течение месяца присылать информацию о том, как животное чувствует себя на новом месте.\n" +
            "В ежедневный отчет входит следующая информация:\n" +
            "\n" +
            "- *Фото животного*\n" +
            "- *Рацион животного*\n" +
            "- *Общее самочувствие и привыкание к новому месту*\n" +
            "- *Изменение в поведении: отказ от старых привычек, приобретение новых*", "Отчет о питомце"),
    //В этот пункт можно добавить кнопки послать файл тхт и послать изображения
    //которые дублировать после сообщения об успешной или провальной отправке

    //меню информации о приюте
    SHELTER_INFO("Вы можете почитать про наш приют", "Инфо"),
    SHELTER_DATA("вы можете посмотреть график работы и как нас найти", "график  работы и контакты"),
    TAKE_PASS("Напишите в чат краткое сообщение где будет содержаться краткая информация о транспортном средстве. Волонтер свяжется с вами если информация будет не полной.\n" +
            "Если вы хотите запросить звонок отошлите в чат номер телефона\n без специальных знаков. Просто 11 цифр начиная с 8\n\n" +
            "Не забудьте ознакомится с правилами безопасности на территории", "Запросить пропуск"),
    RULES_SAFETY(" Общие рекомендации о технике безопасности на территории приюта ", "Правила безопасности"),
    TAKE_DATA_FOR_CONTACT("Вы можете оставить мобильный телефон для связи. Сообщение необходимо оставить в следующем виде:\n\n" +
            "номер телефона без специальных знаков. Просто 11 цифр начиная с 8 ", "Оставить данные для связи"),

    //Меню взять питомца
    RULES_SHELTER("правила знакомства с животным до того, как забрать его из приюта", "Правила"),
    DOCUMENTS("Здесь перечень документов, необходимых для того, чтобы взять животное из приюта", "Документы"),
    TRANSPORTATION("Правила транспортировки", "Рекомендации по транспортировке"),
    SETTLE("Здесь правила содержания", "Правила содержания животного"),
    //Добавить кнопки и содержанием маленького, взрослого, с ограничениями
    LITTLE_PET("Правила содержания малышей", "Малыши"),
    BIG_PET("Правила содержания взрослых животных", "взрослые животные"),
    DISABILITY_PET("Правила содержания животных с ограничениями", "животные c ограниченными возможностями"),

    DOG_HANDLERS("Здесь список кинологов", "Кинологи"),
    //добавить кнопки первичное обращение и для потоянного наблюления
    REASONS_REFUSAL("Здесь какие то причины отказа", "Причины Отказа");


    private final String message;

    private final String nameButton;

    BotMessageEnum(String message, String name) {
        this.message = message;
        this.nameButton = name;
    }

    public String getMessage() {
        return message;
    }

    public String getNameButton() {
        return nameButton;
    }
}
