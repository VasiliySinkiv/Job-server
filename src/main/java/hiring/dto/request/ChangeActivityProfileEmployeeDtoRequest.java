package hiring.dto.request;

public class ChangeActivityProfileEmployeeDtoRequest {
    private String token;
    private boolean activity;

    public ChangeActivityProfileEmployeeDtoRequest(String token, boolean activity) {
        this.token = token;
        this.activity = activity;
    }

    public String getToken() {
        return token;
    }

    public boolean isActivity() {
        return activity;
    }
}
