package hiring.dto.request;

public class GetAllVacanciesDtoRequest {
    private String token;
    private int typeVacancies;

    public GetAllVacanciesDtoRequest(String token, int typeVacancies) {
        this.token = token;
        this.typeVacancies = typeVacancies;
    }

    public String getToken() {
        return token;
    }

    public int getTypeVacancies() {
        return typeVacancies;
    }
}
