package hiring.dto.request;

public class ChangeEmployeeDtoRequest {
    private String token;
    private String lastName;
    private String firstName;
    private String middleName;
    private String password;

    public ChangeEmployeeDtoRequest(String token, String lastName, String firstName, String middleName, String password) {
        this.token = token;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.password = password;
    }

    public ChangeEmployeeDtoRequest(String token, String lastName, String firstName, String password) {
        this(token, lastName, firstName, null, password);
    }

    public String getToken() {
        return token;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }
}
