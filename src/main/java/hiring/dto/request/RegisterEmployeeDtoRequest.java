package hiring.dto.request;

import java.util.Objects;

public class RegisterEmployeeDtoRequest {
    private String lastName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;

    public RegisterEmployeeDtoRequest(String lastName, String firstName, String middleName, String login, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
    }

    public RegisterEmployeeDtoRequest(String lastName, String firstName, String login, String password) {
        this(lastName, firstName, null, login, password);
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterEmployeeDtoRequest request = (RegisterEmployeeDtoRequest) o;
        return Objects.equals(lastName, request.lastName) &&
                Objects.equals(firstName, request.firstName) &&
                Objects.equals(middleName, request.middleName) &&
                Objects.equals(login, request.login) &&
                Objects.equals(password, request.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName, login, password);
    }
}
