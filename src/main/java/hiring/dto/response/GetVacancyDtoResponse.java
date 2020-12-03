package hiring.dto.response;

import hiring.model.Vacancy;

public class GetVacancyDtoResponse {
    private Vacancy vacancy;

    public GetVacancyDtoResponse(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }
}
