package hiring.exception;

public enum ServerErrorCode {
    WRONG_TOKEN("Неверный токен"),
    LOGIN_ALREADY_USED("Этот логин уже используется"),
    WRONG_COMPANY("Не указано название компании"),
    WRONG_ADDRESS("Не указан адрес"),
    WRONG_EMAIL("Не указан email адрес"),
    WRONG_INVALID_EMAIL("Указан некорректный email"),
    WRONG_FIRSTNAME("Не указано имя"),
    WRONG_LASTNAME("Не указана фамилия"),
    WRONG_LOGIN_EMPTY("Не указан логин"),
    WRONG_LOGIN_LENGTH("Длинна логина не может быть меньше 5 символов"),
    WRONG_LOGIN_OR_PASSWORD("Неправильно введен логин или пароль"),
    WRONG_PASSWORD("Не указан пароль"),
    WRONG_PASSWORD_LENGTH("Длинна пароля не может быть меньше 6 символов"),
    WRONG_SKILL("Не указано имя"),
    WRONG_SKILL_LEVEL("Уровень владения не может быть меньше 1 и больше 5"),
    SKILL_NOT_FOUND("Нет такого навыка"),
    WRONG_VACANCY_NAME("Не указано название вакансии"),
    WRONG_VACANCY_SALARY("Зарплата не может быть меньше 0"),
    WRONG_REQUIREMENT("Список требований не может быть пустым"),
    REQUIREMENT_NOT_FOUND("Нет такого требования"),
    WRONG_VACANCY("Такой вакансии не существует"),
    WRONG_SELECTION_TYPE("Неправильно указан тип выборки"),
    WRONG_TYPE_VACANCIES("Неправильно указан тип вакансий");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
