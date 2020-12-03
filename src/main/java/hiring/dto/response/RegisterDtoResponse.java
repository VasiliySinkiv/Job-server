package hiring.dto.response;

public class RegisterDtoResponse {
    private String token;

    public RegisterDtoResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
