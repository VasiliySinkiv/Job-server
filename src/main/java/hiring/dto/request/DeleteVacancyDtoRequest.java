package hiring.dto.request;

import hiring.model.Vacancy;

public class DeleteVacancyDtoRequest {
    private String token;
    private Vacancy vacancy;

    public DeleteVacancyDtoRequest(String token, Vacancy vacancy) {
        this.token = token;
        this.vacancy = vacancy;
    }

    public String getToken() {
        return token;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }
}
