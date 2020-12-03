package hiring.dto.request;

public class GetVacancyDtoRequest {
    private String token;
    private int indexVacancy;

    public GetVacancyDtoRequest(String token, int indexVacancy) {
        this.token = token;
        this.indexVacancy = indexVacancy;
    }

    public String getToken() {
        return token;
    }

    public int getIndexVacancy() {
        return indexVacancy;
    }
}
