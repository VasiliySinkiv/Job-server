package hiring.dto.request;

public class GetVacanciesByEmployeeDtoRequest {
    private String token;
    private int selectionType;

    public GetVacanciesByEmployeeDtoRequest(String token, int selectionType) {
        this.token = token;
        this.selectionType = selectionType;
    }

    public String getToken() {
        return token;
    }

    public int getSelectionType() {
        return selectionType;
    }
}
