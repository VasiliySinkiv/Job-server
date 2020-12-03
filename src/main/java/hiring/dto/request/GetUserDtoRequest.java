package hiring.dto.request;

public class GetUserDtoRequest {
    private String token;

    public GetUserDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
