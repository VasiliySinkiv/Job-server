package hiring.dto.request;

import hiring.model.Vacancy;

public class GetEmployeesByVacancyDtoRequest {
    private String token;
    private Vacancy vacancy;
    private int selectionType;

    public GetEmployeesByVacancyDtoRequest(String token, Vacancy vacancy, int selectionType) {
        this.token = token;
        this.vacancy = vacancy;
        this.selectionType = selectionType;
    }

    public String getToken() {
        return token;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public int getSelectionType() {
        return selectionType;
    }
}
