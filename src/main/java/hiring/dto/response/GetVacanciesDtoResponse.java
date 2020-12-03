package hiring.dto.response;

import hiring.model.Vacancy;

import java.util.List;

public class GetVacanciesDtoResponse {
    private List<Vacancy> vacancies;

    public GetVacanciesDtoResponse(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }
}
