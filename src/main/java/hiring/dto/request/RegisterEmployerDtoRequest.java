package hiring.dto.request;

import java.util.Objects;

public class RegisterEmployerDtoRequest {
    private String lastName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;
    private String company;
    private String address;
    private String email;

    public RegisterEmployerDtoRequest(String lastName, String firstName, String middleName, String login, String password,
                                      String company, String address, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.company = company;
        this.address = address;
        this.email = email;
    }

    public RegisterEmployerDtoRequest(String lastName, String firstName, String login, String password,
                                      String company, String address, String email) {
        this(lastName, firstName, null, login, password, company, address, email);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterEmployerDtoRequest request = (RegisterEmployerDtoRequest) o;
        return Objects.equals(company, request.company) &&
                Objects.equals(address, request.address) &&
                Objects.equals(email, request.email) &&
                Objects.equals(lastName, request.lastName) &&
                Objects.equals(firstName, request.firstName) &&
                Objects.equals(middleName, request.middleName) &&
                Objects.equals(login, request.login) &&
                Objects.equals(password, request.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, address, email, lastName, firstName, middleName, login, password);
    }
}
