package pro.sky.command.constants;

public enum BotMessageEnum{
    //ответы на команды с клавиатуры
    HELP_MESSAGE("  Я бот Приюта для животных. Я расскажу Вам о приюте " +
            "или помогу связаться с волонтерами.\n\n" +
            "✅Выберите хотите вы узнать о приюте кошек или собак.\n\n" +
            "Воспользуйтесь клавиатурой, чтобы начать   \uD83D\uDC47",""),
    CALL_VOLUNTEER("Ваш запрос важен для нас. Волонтер нашего центра скоро свяжется с вами","Позвать волонтера"),

    CAT_SHELTER("кошачий приют ","cat"),

   SHELTER_INFO("Вы можете почитать про наш приют","Инфо"),
TAKE_PET(" Бот поможет потенциальным усыновителям животного из приюта разобраться с бюрократическими"+
        "(оформление договора) и бытовыми (как подготовиться к жизни с животным) вопросам",
        "Взять питомца"),
    SHELTER_DATA("вы можете посмотреть график работы и как нас найти","график  работы и контакты"),
TAKE("Вы можете запросить пропуск на въезд на территорию приюта.\n\n"+
        "Не забудьте ознакомится с правилами безопасности на территории","Пропуск"),
    RULES(" Общие рекомендации о технике безопасности на территории приюта ","Правила безопасности"),
    DATA("Вы можете оставить контактные данные для связи", "Оставить данные для связи"),

    DOG_SHELTER("собачий приют ","dog"),

    RULES_CAT_SHELTER("правила кошачьего приюта","Правила"),
    RULES_DOG_SHELTER("правила собачьего приюта","Правила"),
    documents("Здесь перечень документов","Документы"),
    transportation("Правила транспортировки","Рекомендации по транспортировке"),
    settle("Здесь правила содержания","Правила содержания животного"),
    dog_handlers("Здесь список кинологов","Кинологи"),
    reasons_for_refusal("Здесь какие то причины отказа","Причины Отказа");

/*
- Узнать информацию о приюте (этап 1)
- Как взять животное из приюта (этап 2)
- Прислать отчет о питомце (этап 3)
- Позвать волонтера


- Бот может выдать правила знакомства с животным до того, как забрать её из приюта
- Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта
- Бот может  выдать список рекомендаций по транспортировке животного
- Бот может  выдать список рекомендаций по обустройству дома для щенка/котенка
- Бот может  выдать список рекомендаций по обустройству дома для взрослого животного
- Бот может  выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижения)
- Бот может выдать советы кинолога по первичному общению с собакой *(неактуально для кошачьего приюта, реализовать только для приюта для собак)*
- Бот может выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним *(неактуально для кошачьего приюта, реализовать только для приюта для собак)*
- Бот может выдать список причин отказа в заборе животного из приюта
- Бот может принять и записать контактные данные для связи (*Важно: база данных для пользователей разных приютов должна быть разной.)*
- Если бот не может ответить на вопросы клиента, то можно позвать волонтера
 */

    private final String message;
    private final String nameButton;

   BotMessageEnum(String message,String name) {
        this.message = message;
        this.nameButton=name;
    }

    public String getMessage() {
        return message;
    }

     public String getNameButton() {
        return nameButton;
    }
}