package hiring.dto.request;

public class LogoutDtoRequest {
    String token;

    public LogoutDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
